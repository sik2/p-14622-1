package com.rag.domain.question.repository;

import com.rag.domain.question.document.QuestionDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class QuestionDocumentRepositoryTests {

    @Autowired
    private QuestionDocumentRepository questionDocumentRepository;

    @Test
    public void 문서_생성_테스트() {
        assertDoesNotThrow(() -> {
            float[] embedding = new float[384];
            for (int i = 0; i < 384; i++) {
                embedding[i] = 0.1f;
            }
            questionDocumentRepository.save(new QuestionDocument(embedding, 1L));
        });
    }
}
