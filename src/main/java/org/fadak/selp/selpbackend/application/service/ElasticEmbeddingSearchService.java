package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.infrastructure.ElasticClient;
import org.fadak.selp.selpbackend.application.infrastructure.ElasticQueryBuilder;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ElasticEmbeddingSearchService {

    private final OpenAiEmbeddingModel embeddingModel;
    private final ElasticQueryBuilder queryBuilder;
    private final ElasticClient elasticClient;

    public Map<String, Object> searchMostRelevant(String prompt, String category, int budget) {
        float[] embedding = getEmbedding(prompt);
        var knn = queryBuilder.buildKnnQuery(embedding, 1, category, budget, null, false);
        var hits = elasticClient.send(Map.of("size", 1, "knn", knn));
        return elasticClient.parseSingleHit(hits, null);
    }

    public List<Map<String, Object>> searchByUserInput(String prompt, int topK, String category, int budget) {
        float[] embedding = getEmbedding(prompt);
        var knn = queryBuilder.buildKnnQuery(embedding, topK, category, budget, null, false);
        var hits = elasticClient.send(Map.of("size", topK, "knn", knn));
        return elasticClient.parseMultipleHits(hits);
    }

    public Map<String, Object> recommendSimilarProduct(String prompt, String category, int price, Long excludeId) {
        float[] embedding = getEmbedding(prompt);
        var knn = queryBuilder.buildKnnQuery(embedding, 1, category, price, excludeId, true);
        var hits = elasticClient.send(Map.of("size", 1, "knn", knn));
        return elasticClient.parseSingleHit(hits, excludeId);
    }

    private float[] getEmbedding(String text) {
        EmbeddingRequest request = new EmbeddingRequest(
                List.of(text),
                OpenAiEmbeddingOptions.builder()
                        .model("text-embedding-3-large")
                        .build()
        );
        return embeddingModel.call(request).getResults().getFirst().getOutput();
    }
}
