package com.rag.domain.question.service;

import com.rag.domain.answer.repository.AnswerRepository;
import com.rag.domain.question.document.QuestionDocument;
import com.rag.domain.question.entity.Question;
import com.rag.domain.question.repository.QuestionDocumentRepository;
import com.rag.domain.question.repository.QuestionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class QuestionServiceTests {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionDocumentRepository questionDocumentRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private EmbeddingModel embeddingModel;

    @BeforeEach
    public void setUp() {
        cleanUp();
    }

    @AfterEach
    public void tearDown() {
        cleanUp();
    }

    private void cleanUp() {
        answerRepository.deleteAll();
        questionDocumentRepository.deleteAll();
        questionRepository.deleteAll();
    }

    @Test
    public void 서비스_테스트() {
        Question question = questionService.create("서비스 테스트 질문 내용");

        assertNotNull(question);
        assertNotEquals(0, questionRepository.count());
        assertNotEquals(0, questionDocumentRepository.count());
    }

    @Test
    public void KnnSearch_테스트() {
        // 테스트 데이터 생성
        String[] questions = {"where is pizza store", "when open the pizza store", "how much pizza"};
        Question[] savedQuestions = new Question[questions.length];

        for (int i = 0; i < questions.length; i++) {
            savedQuestions[i] = questionService.create(questions[i]);
        }

        // KNN 검색 실행
        float[] queryVector = embeddingModel.embed("when open the pizza store");
        var searchResult = questionDocumentRepository.knnSearch(
                queryVector,
                "embedding",
                1,
                10,
                PageRequest.of(0, 10),
                QuestionDocument.class
        );

        // 검증: 두 번째 질문(index 1)이 가장 유사해야 함
        QuestionDocument firstResult = searchResult.getContent().get(0).getContent();
        assertEquals(savedQuestions[1].getId(), firstResult.getId());
    }
}