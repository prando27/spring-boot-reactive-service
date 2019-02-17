package com.prando.springbootreactiveservice.http.handler;

import com.prando.springbootreactiveservice.component.person.PersonComponent;
import com.prando.springbootreactiveservice.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
public class PersonHandler {

    private final PersonComponent personComponent;

    public PersonHandler(PersonComponent personComponent) {
        this.personComponent = personComponent;
    }

    public Mono<ServerResponse> findPersonById(ServerRequest request) {
        return personComponent.findById(Integer.valueOf(request.pathVariable("id")))
                .flatMap(person -> ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(Mono.just(person), Person.class))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> listPerson(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(personComponent.findAll(), Person.class);
    }

    public Mono<ServerResponse> createPerson(ServerRequest request) {
        return request.bodyToMono(Person.class)
                .flatMap(person -> ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(personComponent.create(person), Person.class));
    }

    public Mono<ServerResponse> updatePerson(ServerRequest request) {
        Mono<Integer> idMono = Mono.just(request.pathVariable("id")).map(Integer::valueOf);
        Mono<Person> personMono = request.bodyToMono(Person.class);

        return idMono
                .zipWith(personMono)
                .flatMap(idAndPersonTuple -> personComponent.update(idAndPersonTuple.getT1(), idAndPersonTuple.getT2()))
                .flatMap(person -> ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(Mono.just(person), Person.class))
                .doOnError(throwable -> log.error("", throwable))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> removePerson(ServerRequest request) {
        Mono<Integer> idMono = Mono.just(request.pathVariable("id")).map(Integer::valueOf);

        return idMono.flatMap(personComponent::deleteById)
                .flatMap(person -> noContent().build());
    }
}
