package com.prando.springbootreactiveservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 1 - REST using functional endpoints - Ok
 * 2 - Cache - OK (In memory cache) - Ok
 *  2.1 - https://stackoverflow.com/questions/48156424/spring-webflux-and-cacheable-proper-way-of-caching-result-of-mono-flux-type
 * 3 - Persistence using R2DBC and Postgres - Almost there...
 */
@SpringBootApplication
@EnableCaching
public class SpringBootFullReactiveServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFullReactiveServiceApplication.class, args);
    }
}

