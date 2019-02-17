package com.prando.springbootreactiveservice.component.personspringdata;

import com.prando.springbootreactiveservice.model.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

interface PersonRepository extends ReactiveCrudRepository<Person, Integer> {
}
