package com.prando.springbootreactiveservice.config;

import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import io.lettuce.core.resource.Delay;
import io.lettuce.core.resource.DirContextDnsResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${redis.reconnect-delay}")
    private int reconnectDelay;
    @Value("${redis.command-timeout}")
    private int commandTimeout;

    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        ClientResources clientResources = DefaultClientResources.builder()
                .dnsResolver(new DirContextDnsResolver())
                .reconnectDelay(Delay.constant(Duration.ofSeconds(reconnectDelay)))
                .build();

        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
                .clientResources(clientResources)
                .commandTimeout(Duration.ofSeconds(commandTimeout))
                .build();

        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port), lettuceClientConfiguration);
    }

    @Bean
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {

        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Object> redisSerializationContext = builder
                .value(new GenericJackson2JsonRedisSerializer("_type")).build();

        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, redisSerializationContext);
    }

}
