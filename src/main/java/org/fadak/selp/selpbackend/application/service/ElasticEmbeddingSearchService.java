package org.fadak.selp.selpbackend.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.cert.X509Certificate;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ElasticEmbeddingSearchService {

    @Value("${elasticsearch.url}")
    private String elasticUrl;

    @Value("${elasticsearch.auth}")
    private String elasticAuth;

    private final OpenAiEmbeddingModel openAiEmbeddingModel;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Map<String, Object>> searchByUserInput(String prompt, int topK, String category, int budget) {
        try {
            // 1. 임베딩 생성
            float[] embeddingVector = getEmbeddingVector(prompt);

            List<Float> queryVectorList = new ArrayList<>();
            for (float v : embeddingVector) queryVectorList.add(v);

            // 2. KNN 쿼리 구성
            Map<String, Object> knnQuery = new HashMap<>();
            knnQuery.put("field", "embedding");
            knnQuery.put("k", topK);
            knnQuery.put("num_candidates", 300);
            knnQuery.put("query_vector", queryVectorList);

            // 카테고리 + 가격 필터
            List<Map<String, Object>> mustFilters = new ArrayList<>();
            if (category != null && !category.isBlank()) {
                mustFilters.add(Map.of("term", Map.of("category", category.toUpperCase())));
            }
            if (budget > 0) {
                mustFilters.add(Map.of("range", Map.of("price", Map.of("lte", budget))));
            }
            if (!mustFilters.isEmpty()) {
                knnQuery.put("filter", Map.of("bool", Map.of("must", mustFilters)));
            }

            Map<String, Object> requestBodyMap = Map.of(
                    "size", topK,
                    "knn", knnQuery
            );

            String requestBody = objectMapper.writeValueAsString(requestBodyMap);

            // 3. HttpClient 생성 (SSL 무시)
            HttpClient httpClient = HttpClient.newBuilder()
                    .sslContext(getUnsafeSslContext())
                    .build();

            // 4. HTTP 요청 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(elasticUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(elasticAuth.getBytes()))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode hitsNode = objectMapper.readTree(response.body()).path("hits").path("hits");
            List<Map<String, Object>> resultList = new ArrayList<>();
            Set<String> seenProductIds = new HashSet<>();

            for (JsonNode hit : hitsNode) {
                JsonNode source = hit.get("_source");
                String productId = source.path("product_id").asText();
                if (!seenProductIds.contains(productId)) {
                    seenProductIds.add(productId);
                    resultList.add(objectMapper.convertValue(source, new TypeReference<>() {
                    }));
                }
            }

            return resultList;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Map<String, Object> recommendSimilarProduct(String prompt, int topK, String category, int price, Long excludeProductId) {

        try {
            float[] embeddingVector = getEmbeddingVector(prompt);

            List<Float> queryVectorList = new ArrayList<>();
            for (float v : embeddingVector) queryVectorList.add(v);

            Map<String, Object> knnQuery = new HashMap<>();
            knnQuery.put("field", "embedding");
            knnQuery.put("k", topK);
            knnQuery.put("num_candidates", 300);
            knnQuery.put("query_vector", queryVectorList);

            // 필터: 카테고리, 가격, 제외할 product_id
            List<Map<String, Object>> mustFilters = new ArrayList<>();

            if (category != null && !category.isBlank()) {
                mustFilters.add(Map.of("term", Map.of("category", category.toUpperCase())));
            }

            if (price > 0) {
                int range = (int) (price * 0.2); // 20% 범위 계산
                mustFilters.add(Map.of("range", Map.of(
                        "price", Map.of(
                                "gte", price - range,
                                "lte", price + range
                        )
                )));
            }

            // 제외할 기존 productId
            mustFilters.add(Map.of("bool", Map.of("must_not",
                    List.of(Map.of("term", Map.of("product_id", excludeProductId.toString())))
            )));

            knnQuery.put("filter", Map.of("bool", Map.of("must", mustFilters)));

            Map<String, Object> requestBodyMap = Map.of(
                    "size", topK,
                    "knn", knnQuery
            );

            String requestBody = objectMapper.writeValueAsString(requestBodyMap);

            HttpClient httpClient = HttpClient.newBuilder()
                    .sslContext(getUnsafeSslContext())
                    .build();
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(elasticUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(elasticAuth.getBytes()))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode hitsNode = objectMapper.readTree(response.body()).path("hits").path("hits");

            for (JsonNode hit : hitsNode) {
                JsonNode source = hit.get("_source");
                String productId = source.path("product_id").asText();
                if (!productId.equals(String.valueOf(excludeProductId))) {
                    return objectMapper.convertValue(source, new TypeReference<>() {
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private float[] getEmbeddingVector(String text) {
        EmbeddingRequest embeddingRequest = new EmbeddingRequest(
                List.of(text),
                OpenAiEmbeddingOptions.builder().build()
        );
        EmbeddingResponse response = openAiEmbeddingModel.call(embeddingRequest);
        return response.getResults().getFirst().getOutput();
    }

    private SSLContext getUnsafeSslContext() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslContext;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create SSL context", e);
        }
    }
}