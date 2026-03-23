package org.example.hikingroutesservice.services;

import org.example.hikingroutesservice.dto.FilterDto;
import org.example.hikingroutesservice.dto.UserDto;
import org.example.hikingroutesservice.models.Route;
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
