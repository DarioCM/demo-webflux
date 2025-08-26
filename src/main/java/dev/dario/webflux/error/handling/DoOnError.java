package dev.dario.webflux.error.handling;

import reactor.core.publisher.Mono;

interface DoOnError {

  static void main(String[] args) {
    Mono<String> mono = Mono.just("Hello")
        .map(value -> {
          if (value.equals("Hello")) {
            throw new RuntimeException("Something went wrong");
          }
          return value;
        })
        .doOnError(e -> System.out.println("Error occurred: " + e.getMessage()));

    mono.subscribe(
        value -> System.out.println("Success: " + value),
        error -> System.out.println("Handled error")
    );
  }

}
