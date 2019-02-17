package com.prando.springbootreactiveservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1 - REST using functional endpoints - OK
 * 2 - Cache - OK (In memory cache) - OK
 * https://stackoverflow.com/questions/48156424/spring-webflux-and-cacheable-proper-way-of-caching-result-of-mono-flux-type
 * 2.1 - Redis with Lettuce -
 * 3 - Persistence using R2DBC and Postgres - OK
 * 3.1 - Spring Data R2dbc - OK
 * 3.1.1 - Problem with mult-module Spring Data repositories - https://github.com/spring-projects/spring-data-examples/tree/master/multi-store
 * 4 - GlobalExceptionHandler -
 * 4.1 - https://www.baeldung.com/spring-webflux-errors
 */
@SpringBootApplication
public class SpringBootReactiveServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactiveServiceApplication.class, args);
    }
}

