package org.example.hikingroutesservice.repository;

import jakarta.validation.constraints.NotNull;
import org.example.hikingroutesservice.models.Route;
import org.springframework.boot.devtools.filewatch.SnapshotStateRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RouteRepository extends ReactiveMongoRepository<Route, String> {
    Mono<Route> findRouteByName(String name);
}
