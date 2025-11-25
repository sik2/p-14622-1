package com.rag.global.ai;

import com.rag.domain.answer.repository.AnswerRepository;
import com.rag.domain.answer.service.AnswerService;
import com.rag.domain.question.repository.QuestionDocumentRepository;
import com.rag.domain.question.repository.QuestionRepository;
import com.rag.domain.question.service.QuestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AIServiceTests {

    @Autowired
    private AIService aiService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionDocumentRepository questionDocumentRepository;

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
    public void AI_답변_생성_테스트() {
        // 테스트 데이터 생성
        var q1 = questionService.create("where is pizza store");
        answerService.create(q1, "The pizza store is in Seoul.");

        var q2 = questionService.create("when is pizza store open");
        answerService.create(q2, "The pizza store is open from 10am to 10pm.");

        var q3 = questionService.create("how to order pizza");
        answerService.create(q3, "You can order pizza online or by phone.");

        var q4 = questionService.create("how much is pizza");
        answerService.create(q4, "The price of pizza varies depending on the size and toppings.");

        // AI 답변 요청
        String response = aiService.ask("where is pizza store open");

        // 검증
        System.out.println("AI Response: " + response);
        assertNotNull(response);
        assertTrue(response.toLowerCase().contains("seoul"),
                "응답에 'Seoul'이 포함되어야 합니다. 실제 응답: " + response);
    }
}
