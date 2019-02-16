package com.prando.springbootreactiveservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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

//    @Bean
//    public RedisClient redisClient() {
//        ClientResources clientResources = DefaultClientResources.builder()
//                .dnsResolver(new DirContextDnsResolver())
//                .reconnectDelay(Delay.constant(Duration.ofSeconds(reconnectDelay)))
//                .build();
//
//        return RedisClient.create(clientResources);
//    }
//
//    @Bean
//    public StatefulRedisConnection<String, String> statefulRedisConnection(RedisClient redisClient) {
//        RedisURI redisURI = RedisURI.create(host, port);
//
//        StatefulRedisConnection<String, String> statefulRedisConnection = redisClient.connect(redisURI);
//        statefulRedisConnection.setTimeout(Duration.ofSeconds(commandTimeout));
//
//        return statefulRedisConnection;
//    }
//
//    @Bean
//    public RedisReactiveCommands<String, String> redisCommands(StatefulRedisConnection<String, String> statefulRedisConnection) {
//        return statefulRedisConnection.reactive();
//    }


//    @Bean
//    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
//        ClientResources clientResources = DefaultClientResources.builder()
//                .dnsResolver(new DirContextDnsResolver())
//                .reconnectDelay(Delay.constant(Duration.ofSeconds(reconnectDelay)))
//                .build();
//
//        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
//                .clientResources(clientResources)
//                .commandTimeout(Duration.ofSeconds(commandTimeout))
//                .build();
//
//        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port), lettuceClientConfiguration);
//    }
//
//    @Bean
//    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
//
//        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder =
//                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
//
//        RedisSerializationContext<String, Object> redisSerializationContext = builder
//                .value(new GenericJackson2JsonRedisSerializer("_type")).build();
//
//        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, redisSerializationContext);
//    }

}
