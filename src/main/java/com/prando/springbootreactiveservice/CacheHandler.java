package com.prando.springbootreactiveservice;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.cache.CacheMono;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class CacheHandler {

    private final CacheManager cacheManager;
    private final Random random = new Random();

    public CacheHandler(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public Mono<ServerResponse> cacheExample(ServerRequest request) {
        return Mono.justOrEmpty(request.queryParam("id"))
                .flatMap(key -> CacheMono.lookup(reader(), key)
                        .onCacheMissResume(Mono.just(random.nextInt(1000)))
                        .andWriteWith(writer()))
                .flatMap(value -> ok().body(Mono.just(value), Integer.class));
    }

    private Function<String, Mono<Signal<? extends Integer>>> reader() {
        return key -> Mono.justOrEmpty(cacheManager.getCache("numbers").get(key, Integer.class)).map(Signal::next);
    }

    private BiFunction<String, Signal<? extends Integer>, Mono<Void>> writer() {
        return (key, value) -> Mono.fromRunnable(() -> Optional.ofNullable(value.get()).ifPresent(val -> cacheManager.getCache("numbers").put(key, val)));
    }
}
