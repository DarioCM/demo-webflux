package dev.dario.webflux.error.handling;

import reactor.core.publisher.Mono;

interface OnErrorReturn {

  public static void main(String[] args) {
    Mono<String> mono = Mono.just("Hello")
        .map(value -> {
          if (value.equals("Hello")) {
            throw new RuntimeException("Something went wrong");
          }
          return value;
        })
        .onErrorReturn("Fallback value");

    mono.subscribe(System.out::println);

  }

}
