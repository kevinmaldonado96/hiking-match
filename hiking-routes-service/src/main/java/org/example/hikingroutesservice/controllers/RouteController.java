package org.example.hikingroutesservice.controllers;

import jakarta.validation.Valid;
import org.example.hikingroutesservice.dto.FilterDto;
import org.example.hikingroutesservice.dto.UserDto;
import org.example.hikingroutesservice.models.Route;
import org.example.hikingroutesservice.services.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/routes")
public class RouteController {

    @Autowired
    private IRouteService routeService;

    @PostMapping
    public Mono<ResponseEntity<?>> createRoute(@Valid @RequestBody Mono<Route> monoRoute) {
        return monoRoute.flatMap(routeService::createRoute)
                .map(route -> ResponseEntity.status(HttpStatus.CREATED).body(route));
    }

    @PutMapping("/{id}")
    public Mono<Route> updateRoute(@PathVariable("id") String id, @RequestBody Route route) {
        return routeService.updateRoute(id, route);
    }

    @GetMapping
    public Flux<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public Mono<Route> getRoute(@PathVariable("id") String id) {

        return routeService.getRouteById(id);
    }

    @PostMapping("/filter")
    public Flux<Route> getRouterByFilter(@RequestBody FilterDto filterDto) {
        return routeService.getRoutesByFilter(filterDto);
    }

    @PutMapping("/user/{routeId}")
    public Mono<ResponseEntity<?>> registerUser(@RequestBody Mono<UserDto> monoUser, @PathVariable("routeId") String routeId) {
        return monoUser.flatMap(userDto -> routeService.registerUserRoute(userDto, routeId))
                .map(route -> ResponseEntity.status(HttpStatus.CREATED).body(route));
    }
}
