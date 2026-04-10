package org.example.repository;

import org.example.models.Route;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RouteRepository extends ReactiveMongoRepository<Route, String> {
    Mono<Route> findRouteByName(String name);
}
