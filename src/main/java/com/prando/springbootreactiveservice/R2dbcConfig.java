package com.prando.springbootreactiveservice;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableR2dbcRepositories
//public class R2dbcConfig extends AbstractR2dbcConfiguration {
public class R2dbcConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("172.17.0.2")
                .port(5432)
                .username("r2dbc")
                .password("r2dbc")
                .database("postgres")
                .schema("example_schema")
                .build()
        );
    }
}
