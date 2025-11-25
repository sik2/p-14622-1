package com.rag.domain.answer.service;

import com.rag.domain.answer.entity.Answer;
import com.rag.domain.answer.repository.AnswerRepository;
import com.rag.domain.question.entity.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer create(Question question, String content) {
        Answer answer = new Answer(content, question);
        return answerRepository.save(answer);
    }

    public List<Answer> findByQuestionIdIn(List<Long> ids) {
        return answerRepository.findByQuestionIdIn(ids);
    }
}