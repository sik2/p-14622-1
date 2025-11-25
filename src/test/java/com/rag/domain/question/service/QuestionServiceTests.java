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

import static org.junit.jupiter.api.Assertions.*;

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

    // 각 테스트 전후로 데이터 초기화
    @BeforeEach
    @AfterEach
    public void cleanUp() {
        answerRepository.deleteAll();
        questionDocumentRepository.deleteAll();
        questionRepository.deleteAll();
    }

    @Test
    public void 서비스_테스트() {
        // Question 생성 시 QuestionDocument도 함께 생성되는지 확인
        Question question = questionService.create("서비스 테스트 질문 내용");
        assertNotNull(question);
        assertNotEquals(0, questionRepository.count());
        assertNotEquals(0, questionDocumentRepository.count());
    }

    @Test
    public void KnnSearch_테스트() {
        // 테스트 데이터 생성
        String[] questionTexts = {"where is pizza store", "when open the pizza store", "how much pizza"};
        Question[] questions = new Question[questionTexts.length];

        for (int i = 0; i < questionTexts.length; i++) {
            questions[i] = questionService.create(questionTexts[i]);
        }

        // KNN 검색: 임베딩 유사도로 가장 유사한 질문 찾기
        var searchResult = questionDocumentRepository.knnSearch(
                embeddingModel.embed("when open the pizza store"),
                "embedding",
                1,
                10,
                PageRequest.of(0, 10),
                QuestionDocument.class
        );

        // 검색 결과 검증 (임베딩 모델이 questions[1]을 가장 유사하다고 판단해야 함)
        var firstResult = searchResult.iterator().next();
        assertEquals(questions[1].getId(), firstResult.getContent().getId());

        // search 메서드 테스트: FAQ 형태로 반환되는지 확인
        var faqs = questionService.search("when open the pizza store");
        assertTrue(faqs.get(0).getQuestion().contains("pizza store"));
    }
}