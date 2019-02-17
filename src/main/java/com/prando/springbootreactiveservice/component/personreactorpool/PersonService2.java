package com.prando.springbootreactiveservice.component.personreactorpool;

import com.prando.springbootreactiveservice.model.Person;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PersonService2 {

    private final PersonRepository2 personRepository2;

    public PersonService2(PersonRepository2 personRepository2) {
        this.personRepository2 = personRepository2;
    }

    public Flux<Person> findAll() {
        return personRepository2.findAll();
    }

    public Mono<Person> findById(Integer id) {
        return personRepository2.findById(id);
    }
}
