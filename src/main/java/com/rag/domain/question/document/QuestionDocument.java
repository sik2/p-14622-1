package com.rag.domain.question.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "questions")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDocument {

    @Field(type = FieldType.Dense_Vector, dims = 384)
    private float[] embedding;

    @Id
    @Field
    private Long id;
}