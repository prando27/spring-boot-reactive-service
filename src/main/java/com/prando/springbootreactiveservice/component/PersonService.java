package com.prando.springbootreactiveservice.component;

import com.prando.springbootreactiveservice.model.Person;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.cache.CacheMono;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final ReactiveRedisTemplate<String, Person> reactiveRedisTemplate;

    public PersonService(PersonRepository personRepository,
                         ReactiveRedisTemplate<String, Person> reactiveRedisTemplate) {
        this.personRepository = personRepository;
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    public Mono<Person> findPersonById(Integer id) {
        return CacheMono.lookup(findPersonReader(), id)
                .onCacheMissResume(personRepository.findById(id))
                .andWriteWith(findPersonWriter());
    }

    private Function<Integer, Mono<Signal<? extends Person>>> findPersonReader() {
        return key -> reactiveRedisTemplate.opsForValue().get(key).map(Signal::next);
    }

    private BiFunction<Integer, Signal<? extends Person>, Mono<Void>> findPersonWriter() {
        return (key, value) -> reactiveRedisTemplate.opsForValue().set(key.toString(), value.get()).then();
    }

    public Flux<Person> listPerson() {
        return personRepository.findAll();
    }

    public Mono<Person> createPerson(Person person) {
        return personRepository.save(person);
    }

    public Mono<Person> updatePerson(Integer id, Person person) {
        return personRepository.findById(id)
                .map(personFound -> this.patch(personFound, person))
                .flatMap(personToUpdate -> personRepository.save(personToUpdate)
                        .flatMap(personUpdated -> reactiveRedisTemplate.opsForValue().set(id.toString(), personUpdated)
                                .then(Mono.just(personUpdated))
                                .onErrorReturn(personUpdated)))
                .switchIfEmpty(Mono.empty());
    }

    private Person patch(Person personStored, Person personToUpdateFrom) {
        Person.Builder personBuilder = personStored.toBuilder();
        Optional.ofNullable(personToUpdateFrom.getName()).filter(StringUtils::isNotBlank)
                .ifPresent(personBuilder::name);
        return personBuilder.build();
    }

    public Mono<Void> removePerson(Integer id) {
        return personRepository.deleteById(id)
                .then(reactiveRedisTemplate.opsForValue().delete(id.toString()))
                .then();
    }
}
