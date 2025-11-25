package com.rag.domain.question.service;

import com.rag.domain.question.entity.Question;
import com.rag.domain.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question create(String content) {
        Question question = new Question(content);
        return questionRepository.save(question);
    }
}
