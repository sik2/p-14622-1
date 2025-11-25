package com.rag.domain.question.repository;

import com.rag.domain.question.entity.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class QuestionRepositoryTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void 엔티티_생성_테스트() {
        Question question = questionRepository.save(new Question("질문 내용"));
        assertNotNull(question);
    }
}
