package br.dev.ferreiras.spring_ai_intro.controllers;

import br.dev.ferreiras.spring_ai_intro.model.Answer;
import br.dev.ferreiras.spring_ai_intro.model.CapitalRequest;
import br.dev.ferreiras.spring_ai_intro.model.CapitalResponse;
import br.dev.ferreiras.spring_ai_intro.model.Question;
import br.dev.ferreiras.spring_ai_intro.services.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

  private final OpenAIService openAIService;

  public QuestionController(OpenAIService openAIService) {
    this.openAIService = openAIService;
  }

  @PostMapping("/ask")
  public Answer askQuestion(@RequestBody Question question) {
    return openAIService.getAnswer(question);
  }

  @PostMapping("/capital")
  public Answer getCapital(@RequestBody CapitalRequest capitalRequest) {
    return this.openAIService.getCapital(capitalRequest);
  }

  @PostMapping("/capitalJ")
  public CapitalResponse getCapitalJ(@RequestBody CapitalRequest capitalRequest){
    return this.openAIService.getCapitalJson(capitalRequest);
  }
}

