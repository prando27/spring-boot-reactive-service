package com.prando.springbootreactiveservice.component;

import com.prando.springbootreactiveservice.model.Person;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.cache.CacheManager;
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
    private final CacheManager cacheManager;

    public PersonService(PersonRepository personRepository,
                         CacheManager cacheManager) {
        this.personRepository = personRepository;
        this.cacheManager = cacheManager;
    }

    public Mono<Person> findPersonById(Integer id) {
        return CacheMono.lookup(findPersonReader(), id)
                .onCacheMissResume(personRepository.findById(id))
                .andWriteWith(findPersonWriter());
    }

    private Function<Integer, Mono<Signal<? extends Person>>> findPersonReader() {
        return key -> Mono.justOrEmpty(cacheManager.getCache("person").get(key, Person.class)).map(Signal::next);
    }

    private BiFunction<Integer, Signal<? extends Person>, Mono<Void>> findPersonWriter() {
        return (key, value) -> Mono.fromRunnable(() -> Optional.ofNullable(value.get()).ifPresent(val -> cacheManager.getCache("person").put(key, val)));
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
                .flatMap(personRepository::save)
                .doOnNext(updatedPerson -> cacheManager.getCache("person").put(id, updatedPerson))
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
                .then(Mono.fromRunnable(() -> cacheManager.getCache("person").evict(id)));
    }
}
