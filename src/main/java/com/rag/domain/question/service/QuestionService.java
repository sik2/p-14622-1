package com.rag.domain.question.service;

import com.rag.domain.question.document.QuestionDocument;
import com.rag.domain.question.entity.Question;
import com.rag.domain.question.repository.QuestionDocumentRepository;
import com.rag.domain.question.repository.QuestionRepository;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionDocumentRepository questionDocumentRepository;
    private final EmbeddingModel embeddingModel;

    public QuestionService(
            QuestionRepository questionRepository,
            QuestionDocumentRepository questionDocumentRepository,
            EmbeddingModel embeddingModel) {
        this.questionRepository = questionRepository;
        this.questionDocumentRepository = questionDocumentRepository;
        this.embeddingModel = embeddingModel;
    }

    public Question create(String content) {
        // Question 저장
        Question question = questionRepository.save(new Question(content));

        // 임베딩 생성 및 QuestionDocument 저장
        float[] embedding = embeddingModel.embed(content);
        QuestionDocument document = new QuestionDocument(embedding, question.getId());
        questionDocumentRepository.save(document);

        return question;
    }
}