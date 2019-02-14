package com.prando.springbootreactiveservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 1 - REST using functional endpoints - Ok
 * 2 - Cache - OK (In memory cache) - Ok
 * 3 - Persistence using R2DBC and Postgres - Almost there...
 */
@SpringBootApplication
@EnableCaching
public class SpringBootFullReactiveServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFullReactiveServiceApplication.class, args);
    }
}

