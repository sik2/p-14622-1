package com.rag.domain.answer.service;

import com.rag.domain.answer.entity.Answer;
import com.rag.domain.question.entity.Question;
import com.rag.domain.question.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AnswerServiceTests {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Test
    public void 서비스_테스트() {
        Question question = questionService.create("서비스 테스트 질문 내용");
        Answer answer = answerService.create(question, "서비스 테스트 답변 내용");

        assertEquals("서비스 테스트 답변 내용", answer.getContent());
        assertEquals(question.getId(), answer.getQuestion().getId());
    }
}
