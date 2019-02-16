package com.prando.springbootreactiveservice.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    @Value("${r2dbc.host}")
    private String host;

    @Value("${r2dbc.port}")
    private Integer port;

    @Value("${r2dbc.username}")
    private String username;

    @Value("${r2dbc.password}")
    private String password;

    @Value("${r2dbc.database}")
    private String database;

    @Value("${r2dbc.schema}")
    private String schema;

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host(host)
                .port(port)
                .username(username)
                .password(password)
                .database(database)
                .schema(schema)
                .build()
        );
    }
}
