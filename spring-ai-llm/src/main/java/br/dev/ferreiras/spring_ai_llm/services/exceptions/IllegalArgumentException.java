package br.dev.ferreiras.spring_ai_llm.services.exceptions;

public class IllegalArgumentException extends RuntimeException{
  public IllegalArgumentException(String message) {
    super(message);
  }
}
