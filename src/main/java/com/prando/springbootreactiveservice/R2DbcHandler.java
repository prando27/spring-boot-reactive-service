package com.prando.springbootreactiveservice;

import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class R2DbcHandler {

    private final PostgresqlConnectionFactory connectionFactory;

    public R2DbcHandler(PostgresqlConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Mono<ServerResponse> listPerson(ServerRequest request) {
        return Mono.empty();
    }

    public Mono<ServerResponse> createPerson(ServerRequest request) {
        return Mono.empty();
    }
}
