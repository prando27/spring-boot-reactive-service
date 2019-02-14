package com.prando.springbootreactiveservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.concurrent.TimeUnit;

/**
 * 1 - REST using functional endpoints - Ok
 * 2 - Cache - OK (In memory cache) - Ok
 *  2.1 - https://stackoverflow.com/questions/48156424/spring-webflux-and-cacheable-proper-way-of-caching-result-of-mono-flux-type
 *  2.2 - Redis with Lettuce -
 * 3 - Persistence using R2DBC and Postgres
 *  3.1 -
 */
@SpringBootApplication
@EnableCaching
public class SpringBootFullReactiveServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFullReactiveServiceApplication.class, args);
    }

    @Autowired
    private PersonRepository2 personRepository2;

    @Override
    public void run(String... args) throws Exception {
        personRepository2.insert(Person.builder().name("Sonova").build());

        Thread.sleep(TimeUnit.SECONDS.toMillis(5));

        personRepository2.select();
    }
}

