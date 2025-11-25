package com.rag.domain.answer.repository;

import com.rag.domain.answer.entity.Answer;
import com.rag.domain.question.entity.Question;
import com.rag.domain.question.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AnswerRepositoryTests {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void 엔티티_생성_테스트() {
        Question question = questionRepository.save(new Question("질문 내용"));
        Answer answer = answerRepository.save(new Answer("답변 내용", question));
        assertNotNull(answer);
    }
}