package br.dev.ferreiras.spring_ai_llm.controller;

import br.dev.ferreiras.spring_ai_llm.contracts.ControllerChat;
import br.dev.ferreiras.spring_ai_llm.dto.PromptRequest;
import br.dev.ferreiras.spring_ai_llm.services.ChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class ChatController implements ControllerChat {

  private final ChatService chatServices;

  @Value("classpath:/prompts/spring-prompt.st")
  private Resource sbPromptTemplate; // default prompt

  private static final List<String> SUPPORTED_MODELS = List.of("openai", "anthropic", "gemini");

  public ChatController(ChatService chatServices) {
    this.chatServices = chatServices;
  }


  /**
   * Endpoint to generate LLM response based on user input and model.
   *
   * @param model         LLM model to use (e.g., "openai", "anthropic").
   * @param promptRequest Optional: User and system prompts
   * @return Generated response or an error message.
   * @throws IOException on error
   */

  public ResponseEntity<String> fetchAnswer(@RequestParam(value = "model", defaultValue = "openai") String model,
                                                 @RequestBody(required = false) PromptRequest promptRequest) throws IOException {
    if (!SUPPORTED_MODELS.contains(model.toLowerCase())) {
      return ResponseEntity.badRequest()
          .body("Invalid modelType. Supported models are: " + String.join(", ", SUPPORTED_MODELS));
    }

    if (promptRequest == null) {
      promptRequest = new PromptRequest("add jpa functionality",
          sbPromptTemplate.getContentAsString(Charset.defaultCharset()));
    }

    try {
      String response = chatServices.getLLMModelResponse(promptRequest, model).getResult().getOutput().toString();
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error generating response from model: " + e.getMessage());
    }
  }
}
