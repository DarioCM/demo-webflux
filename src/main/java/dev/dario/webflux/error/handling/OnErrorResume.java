package dev.dario.webflux.error.handling;

import reactor.core.publisher.Mono;

interface OnErrorResume {

  public static void main(String[] args) {
    Mono<String> mono = Mono.just("Hello")
        .map(value -> {
          if (value.equals("Hello")) {
            throw new RuntimeException("Something went wrong");
          }
          return value;
        })
        .onErrorResume(e -> Mono.just("Recovered Value"));

    mono.subscribe(System.out::println);
  }

}
