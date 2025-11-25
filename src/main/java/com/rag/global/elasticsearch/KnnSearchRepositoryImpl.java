package com.rag.global.elasticsearch;

import co.elastic.clients.elasticsearch._types.KnnSearch;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;

import java.util.Arrays;

public class KnnSearchRepositoryImpl<T> implements KnnSearchRepository<T> {

    private final ElasticsearchTemplate elasticsearchTemplate;

    public KnnSearchRepositoryImpl(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public SearchPage<T> knnSearch(
            float[] queryVector,
            String field,
            int k,
            int numCandidates,
            Pageable pageable,
            Class<T> kClass) {

        // KnnSearch 생성
        KnnSearch search = KnnSearch.of(builder -> builder
                .queryVector(toList(queryVector))
                .field(field)
                .k(k)
                .numCandidates(numCandidates)
        );

        // NativeQuery 생성 - withKnnQuery 사용
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.knn(knn -> knn
                        .queryVector(toList(queryVector))
                        .field(field)
                        .k(k)
                        .numCandidates(numCandidates)
                ))
                .withPageable(pageable)
                .build();

        // 검색 실행
        SearchHits<T> searchHits = elasticsearchTemplate.search(query, kClass);

        // SearchPage로 변환
        return SearchHitSupport.searchPageFor(searchHits, pageable);
    }

    // float[] to List<Float> 변환 헬퍼 메서드
    private java.util.List<Float> toList(float[] array) {
        Float[] boxed = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            boxed[i] = array[i];
        }
        return Arrays.asList(boxed);
    }
}
