package br.dev.ferreiras.spring_ai_llm.services;

import br.dev.ferreiras.spring_ai_llm.dto.PromptRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatService {

  private final  ChatClientFactory chatClientFactory;

  public ChatService(ChatClientFactory chatClientFactory) {
    this.chatClientFactory = chatClientFactory;
  }

  public ChatResponse getLLMModelResponse(PromptRequest promptRequest, String model) throws IOException {

    ChatClient chatClient = chatClientFactory.getChatClient(model);

    return chatClient.prompt()
        .system(s -> s.text(promptRequest.systemPrompt()))
        .user(u -> u.text(promptRequest.userPrompt()))
        .call()
        .chatResponse();

  }
}
