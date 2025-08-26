package dev.dario.webflux.error.handling;

import reactor.core.publisher.Mono;

interface OnErrorMap {

  static void main(String[] args) {
    Mono<String> mono = Mono.just("Hello")
        .map(value -> {
          if ("Hello".equals(value)) {
            throw new RuntimeException("Something went wrong");
          }
          return value;
        })
        .onErrorMap(e -> new CustomException("Custom error: " + e.getMessage()));

    mono.subscribe(
        v -> System.out.println("Success: " + v),
        e -> System.out.println("Error: " + e.getMessage())
    );
  }

  class CustomException extends RuntimeException {

    public CustomException(String message) {
      super(message);
    }
  }
}