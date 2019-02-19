package com.prando.springbootreactiveservice.component.person;

import com.prando.springbootreactiveservice.config.reactor.pool.NonBlockingPool;
import com.prando.springbootreactiveservice.config.reactor.pool.Pool;
import com.prando.springbootreactiveservice.model.Person;
import io.r2dbc.postgresql.PostgresqlConnection;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;
import java.util.concurrent.Executors;

@Component
class PersonRepository {

    private final Pool<PostgresqlConnection> connectionPool;

    PersonRepository(PostgresqlConnectionFactory connectionFactory) {
        this.connectionPool = NonBlockingPool.factory(connectionFactory.create())
                .disposer(connection -> connection.close().subscribe())
                .maxSize(5)
                .scheduler(Schedulers.fromExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())))
                .build();
    }

    Mono<Person> findById(Integer id) {
        return connectionPool.member()
                .flatMap(member -> Mono.from(member
                        .value()
                        .createStatement("SELECT * FROM person WHERE id = $1")
                        .bind(0, id)
                        .execute())
                        .doFinally(signalType -> member.checkin())
                        .flatMap(result -> Mono.from(result.map((row, rowMetadata) ->
                                Person.builder()
                                        .id(row.get("id", Integer.class))
                                        .name(row.get("name", String.class))
                                        .build()))))
                ;
    }

    Flux<Person> findAll() {
        return connectionPool.member()
                .flatMapMany(member -> member
                        .value()
                        .createStatement("SELECT * FROM person")
                        .execute()
                        .doFinally(signalType -> member.checkin())
                        .flatMap(result -> result.map((row, rowMetadata) -> Person.builder().id(row.get("id", Integer.class)).name(row.get("name", String.class)).build()))).onBackpressureBuffer();
    }

    Mono<Person> save(Person person) {
        return Mono.just(person)
                .filter(p -> Objects.isNull(p.getId()))
                .flatMap(p -> connectionPool.member().flatMap(member ->
                        Mono.from(member.value()
                                .createStatement("INSERT INTO person (name) VALUES ($1)").returnGeneratedValues("id").bind(0, person.getName()).execute())
                                .doFinally(signalType -> member.checkin())
                                .flatMap(result -> Mono.from(result.map((row, rowMetadata) -> row.get("id", Integer.class))))
                                .flatMap(this::findById)
                ))
                .switchIfEmpty(connectionPool.member().flatMap(member -> Mono.from(member
                        .value()
                        .createStatement("UPDATE person SET name = $1 WHERE id = $2")
                        .bind(0, person.getName())
                        .bind(1, person.getId())
                        .execute())
                        .doFinally(signalType -> member.checkin())
                        .flatMap(result -> this.findById(person.getId()))
                ));
    }

    Mono<Void> deleteById(Integer id) {
        return connectionPool.member()
                .flatMap(member -> Mono.from(member
                        .value()
                        .createStatement("DELETE FROM person WHERE id = $1")
                        .bind(0, id)
                        .execute())
                        .doFinally(signalType -> member.checkin())
                        .then());
    }
}
