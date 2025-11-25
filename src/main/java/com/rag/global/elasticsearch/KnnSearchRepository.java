package com.rag.global.elasticsearch;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;

public interface KnnSearchRepository<T> {
    SearchPage<T> knnSearch(
            float[] queryVector,
            String field,
            int k,
            int numCandidates,
            Pageable pageable,
            Class<T> kClass
    );
}
