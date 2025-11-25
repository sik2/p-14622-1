package com.rag.domain.question.service;


import com.rag.domain.answer.repository.AnswerRepository;
import com.rag.domain.question.entity.Question;
import com.rag.domain.question.repository.QuestionDocumentRepository;
import com.rag.domain.question.repository.QuestionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}