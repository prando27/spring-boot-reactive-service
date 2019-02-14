package com.prando.springbootreactiveservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> helloWorldRouterFunction(ExampleHandler exampleHandler,
                                                                   CacheHandler cacheHandler,
                                                                   R2DbcHandler r2DbcHandler) {
        return RouterFunctions.route()
                .GET("/get-example", exampleHandler::getExample)
                .POST("/post-body-example", exampleHandler::postBodyExample)
                .GET("/cache-example", cacheHandler::cacheExample)

                .GET("/person", r2DbcHandler::listPerson)
                .POST("/person", r2DbcHandler::createPerson)
                .build();
    }
}
