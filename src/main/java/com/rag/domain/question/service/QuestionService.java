package com.rag.domain.question.service;

import com.rag.domain.answer.entity.Answer;
import com.rag.domain.answer.service.AnswerService;
import com.rag.domain.question.document.QuestionDocument;
import com.rag.domain.question.dto.FAQ;
import com.rag.domain.question.entity.Question;
import com.rag.domain.question.repository.QuestionDocumentRepository;
import com.rag.domain.question.repository.QuestionRepository;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionDocumentRepository questionDocumentRepository;
    private final EmbeddingModel embeddingModel;
    private final AnswerService answerService;

    public QuestionService(
            QuestionRepository questionRepository,
            QuestionDocumentRepository questionDocumentRepository,
            EmbeddingModel embeddingModel,
            AnswerService answerService) {
        this.questionRepository = questionRepository;
        this.questionDocumentRepository = questionDocumentRepository;
        this.embeddingModel = embeddingModel;
        this.answerService = answerService;
    }

    public Question create(String content) {
        Question question = questionRepository.save(new Question(content));

        float[] embedding = embeddingModel.embed(content);
        QuestionDocument document = new QuestionDocument(embedding, question.getId());
        questionDocumentRepository.save(document);

        return question;
    }

    @Tool(description = "search questions and answers by text")
    public List<FAQ> search(String query) {
        List<Long> ids = questionDocumentRepository.knnSearch(
                        embeddingModel.embed(query),
                        "embedding",
                        10,
                        100,
                        PageRequest.of(0, 10),
                        QuestionDocument.class
                ).stream()
                .map(searchHit -> searchHit.getContent().getId())
                .collect(Collectors.toList());

        List<Question> questions = questionRepository.findAllById(ids);
        List<Answer> answers = answerService.findByQuestionIdIn(ids);

        return questions.stream()
                .map(q -> new FAQ(
                        q.getId(),
                        q.getContent(),
                        answers.stream()
                                .filter(a -> a.getQuestion().getId().equals(q.getId()))
                                .map(Answer::getContent)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}