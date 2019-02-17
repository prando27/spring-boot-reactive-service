package com.prando.springbootreactiveservice.component.person;

import com.prando.springbootreactiveservice.model.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

interface PersonRepository extends ReactiveCrudRepository<Person, Integer> {
}
