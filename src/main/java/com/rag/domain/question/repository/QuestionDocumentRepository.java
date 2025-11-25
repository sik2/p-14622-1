package com.rag.domain.question.repository;

import com.rag.domain.question.document.QuestionDocument;
import com.rag.global.elasticsearch.KnnSearchRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface QuestionDocumentRepository extends ElasticsearchRepository<QuestionDocument, Long>, KnnSearchRepository<QuestionDocument> {

}
