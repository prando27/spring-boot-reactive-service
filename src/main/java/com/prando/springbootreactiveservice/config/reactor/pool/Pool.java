package com.prando.springbootreactiveservice.config.reactor.pool;

import reactor.core.publisher.Mono;

public interface Pool<T> extends AutoCloseable {

    Mono<Member<T>> member();

}