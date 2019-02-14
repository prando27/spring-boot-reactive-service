package com.prando.springbootreactiveservice;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PersonRepository2 {

    private final ConnectionFactory connectionFactory;

    public PersonRepository2(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void select() {
        // https://stackoverflow.com/questions/42037552/how-to-properly-manage-closable-resources-in-reactor
        Mono.using(
                this::connectionMono,

                connectionMono -> connectionMono()
                        .flatMapMany(conn -> conn.createStatement("SELECT * FROM PERSON").execute())
                        .flatMap(r -> r.map((row, rowMetadata) -> Person.builder()
                                .id(row.get("id", Integer.class))
                                .name(row.get("name", String.class))
                                .build()))
                        .doOnNext(System.out::println).then(),



                connectionMono -> connectionMono.map(Connection::close)
        ).subscribe();
    }

    public void insert(Person person) {
        Mono.using(
                this::connectionMono,
                connectionMono -> connectionMono()
                        .flatMapMany(conn -> conn.createStatement("INSERT INTO PERSON (NAME) VALUES ($1)")
                                .bind("$1", person.getName())
                                .add()
                                .execute())
                        .then(),
                connectionMono -> connectionMono.map(Connection::close)
        ).subscribe();
    }

    private Mono<Connection> connectionMono() {
        return Mono.from(connectionFactory.create());
    }
}
