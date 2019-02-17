# spring-boot-reactive-service
Spring Boot Reactive Service Example

Spring WebFlux (with Functional Endpoints), Redis Cache, R2DBC, Spring Data R2DBC, Postgres

1 - REST using functional endpoints - OK

2 - Cache - OK (In memory cache) - OK (https://stackoverflow.com/questions/48156424/spring-webflux-and-cacheable-proper-way-of-caching-result-of-mono-flux-type)
2.1 - Redis with Lettuce - OK

3 - Persistence using R2DBC and Postgres - OK
3.1 - Spring Data R2dbc - OK
3.1.1 - Problem with mult-module Spring Data repositories - https://github.com/spring-projects/spring-data-examples/tree/master/multi-store
3.2 - Reactor Pool - 
3.2.1 - https://github.com/reactor/reactor/issues/651
3.2.2 - https://github.com/rdegnan/reactor-pool
3.2.3 - https://github.com/davidmoten/rxjava2-jdbc

4 - GlobalExceptionHandler - (https://www.baeldung.com/spring-webflux-errors) 
