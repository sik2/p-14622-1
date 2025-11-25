package com.rag.global.config;
import com.rag.domain.question.service.QuestionService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Bean
    public ToolCallbackProvider faqTools(QuestionService questionService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(questionService)
                .build();
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ToolCallbackProvider toolCallbackProvider) {

        return chatClientBuilder
                .defaultTools(toolCallbackProvider)
                .build();
    }
}