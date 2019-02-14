package com.prando.springbootreactiveservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
public class ExampleHandler {

    public Mono<ServerResponse> getExample(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just("Hello World"), String.class);
    }

    public Mono<ServerResponse> postBodyExample(ServerRequest request) {
        return request.bodyToMono(Person.class)
                .doOnNext(person -> log.info(person.toString()))
                .flatMap(person -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(person), Person.class));
    }
}
