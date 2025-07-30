package org.fadak.selp.selpbackend.application.infrastructure;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ElasticQueryBuilder {

    public Map<String, Object> buildKnnQuery(float[] embedding, int topK, String category, int price, Long excludeProductId, boolean applyExclude) {

        List<Float> vector = new ArrayList<>();
        for (float v : embedding) {
            vector.add(v);
        }

        Map<String, Object> knn = new HashMap<>();
        knn.put("field", "embedding");
        knn.put("k", topK);
        knn.put("num_candidates", 3000);
        knn.put("query_vector", vector);

        List<Map<String, Object>> must = new ArrayList<>();

        if (category != null && !category.isBlank()) {
            must.add(Map.of("term", Map.of("category", category.toUpperCase())));
        }

        if (price > 0) {
            int maxPrice = (int) (price * 1.3);
            must.add(Map.of("range", Map.of("price", Map.of("lte", maxPrice))));
        }

        if (applyExclude && excludeProductId != null) {
            must.add(Map.of("bool", Map.of("must_not",
                    List.of(Map.of("term", Map.of("product_id", excludeProductId.toString())))
            )));
        }

        if (!must.isEmpty()) {
            knn.put("filter", Map.of("bool", Map.of("must", must)));
        }

        return knn;
    }
}
