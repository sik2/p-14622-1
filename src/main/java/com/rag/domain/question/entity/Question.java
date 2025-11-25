package com.rag.domain.question.entity;

import com.rag.domain.answer.entity.Answer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    // 생성자
    public Question(String content) {
        this.content = content;
    }
}