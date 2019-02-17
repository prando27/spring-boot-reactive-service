package com.prando.springbootreactiveservice.config;

import com.prando.springbootreactiveservice.http.handler.PersonHandler;
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
    public RouterFunction<ServerResponse> helloWorldRouterFunction(PersonHandler personHandler) {
        return RouterFunctions.route()
                .GET("/person/{id}", personHandler::findPersonById)
                .GET("/person", personHandler::listPerson)
                .POST("/person", personHandler::createPerson)
                .PUT("/person/{id}", personHandler::updatePerson)
                .DELETE("/person/{id}", personHandler::removePerson)
                .build();
    }
}
