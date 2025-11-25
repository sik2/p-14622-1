package com.rag.global.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final ChatClient chatClient;

    public AIService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String ask(String question) {
        var response = chatClient.prompt().user(question).call();
        return response.content() != null ? response.content() : "No response from AI";
    }
}