package org.example.services;

import org.example.dto.FilterDto;
import org.example.dto.UserDto;
import org.example.models.Route;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IRouteService {

    Mono<Object> createRoute(Route route);

    Mono<Route> updateRoute(String id, Route route);

    Flux<Route> getAllRoutes();

    Mono<Route> getRouteById(String id);

    Mono<Route> registerUserRoute(UserDto userDto, String id);

    Flux<Route> getRoutesByFilter(FilterDto filterDto);
}
