package com.rag.domain.answer.entity;

import com.rag.domain.question.entity.Question;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    // 생성자
    public Answer(String content, Question question) {
        this.content = content;
        this.question = question;
    }
}