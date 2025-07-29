package org.fadak.selp.selpbackend.application.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ElasticClient {

    @Value("${elasticsearch.url}")
    private String elasticUrl;

    @Value("${elasticsearch.auth}")
    private String elasticAuth;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode send(Map<String, Object> requestBody) {
        try {
            String body = objectMapper.writeValueAsString(requestBody);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(elasticUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " +
                            Base64.getEncoder().encodeToString(elasticAuth.getBytes()))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readTree(response.body()).path("hits").path("hits");

        } catch (Exception e) {
            e.printStackTrace();
            return objectMapper.createArrayNode(); // 빈 배열
        }
    }

    public Map<String, Object> parseSingleHit(JsonNode hits, Long excludeId) {
        for (JsonNode hit : hits) {
            JsonNode source = hit.get("_source");
            String pid = source.path("product_id").asText();
            if (excludeId == null || !pid.equals(excludeId.toString())) {
                return objectMapper.convertValue(source, new TypeReference<>() {
                });
            }
        }
        return null;
    }

    public List<Map<String, Object>> parseMultipleHits(JsonNode hits) {
        Set<String> seen = new HashSet<>();
        List<Map<String, Object>> results = new ArrayList<>();

        for (JsonNode hit : hits) {
            JsonNode source = hit.get("_source");
            String pid = source.path("product_id").asText();
            if (seen.add(pid)) {
                results.add(objectMapper.convertValue(source, new TypeReference<>() {
                }));
            }
        }
        return results;
    }
}
