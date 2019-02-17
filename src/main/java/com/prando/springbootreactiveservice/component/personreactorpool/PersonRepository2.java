package com.prando.springbootreactiveservice.component.personreactorpool;

import com.prando.springbootreactiveservice.config.reactor.pool.NonBlockingPool;
import com.prando.springbootreactiveservice.config.reactor.pool.Pool;
import com.prando.springbootreactiveservice.model.Person;
import io.r2dbc.postgresql.PostgresqlConnection;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PersonRepository2 {

    private final Pool<PostgresqlConnection> connectionPool;

    public PersonRepository2(PostgresqlConnectionFactory connectionFactory) {
        this.connectionPool = NonBlockingPool.factory(connectionFactory.create())
                .disposer(connection -> connection.close().subscribe())
                .maxSize(5)
                .build();
    }

    public Flux<Person> findAll() {
        return connectionPool.member()
                .flatMapMany(member -> member
                        .value()
                        .createStatement("SELECT * FROM person")
                        .execute()
                        .doFinally(signalType -> member.checkin())
                        .flatMap(result -> result.map((row, rowMetadata) -> Person.builder().id(row.get("id", Integer.class)).name(row.get("name", String.class)).build()))).onBackpressureBuffer();
    }

    public Mono<Person> findById(Integer id) {
        return connectionPool.member()
                .flatMap(member -> Mono.from(member
                        .value()
                        .createStatement("SELECT * FROM person WHERE id = $1")
                        .bind(0, id)
                        .execute())
                        .doFinally(signalType -> member.checkin())
                        .flatMap(result -> Mono.from(result.map((row, rowMetadata) -> Person.builder().id(row.get("id", Integer.class)).name(row.get("name", String.class)).build()))));
    }

}
