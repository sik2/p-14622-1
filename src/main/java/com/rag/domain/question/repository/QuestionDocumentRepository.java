package com.rag.domain.question.repository;

import com.rag.domain.question.document.QuestionDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface QuestionDocumentRepository extends ElasticsearchRepository<QuestionDocument, Long> {

}
