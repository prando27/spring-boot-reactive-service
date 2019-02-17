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
                .GET("/personspringdata/{id}", personHandler::findPersonById)
                .GET("/personspringdata", personHandler::listPerson)
                .POST("/personspringdata", personHandler::createPerson)
                .PUT("/personspringdata/{id}", personHandler::updatePerson)
                .DELETE("/personspringdata/{id}", personHandler::removePerson)

                .GET("/personreactorpool", personHandler::listPerson2)
                .GET("/personreactorpool/{id}", personHandler::findPersonById2)
                .build();
    }
}
