package org.example.restclient.interfaces;

import org.example.dto.FilterRouteDto;
import org.example.dto.RouteDto;
import org.example.dto.UserDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RouteClient {

    @PutExchange("/routes/user/{routeId}")
    Mono<RouteDto> registerUserRoute(@RequestBody UserDto userDto, @PathVariable("routeId") String routeId);

    @GetExchange("/routes")
    Flux<RouteDto> getAllRoutes();

    @PostExchange("/routes/filter")
    List<RouteDto> getRouterByFilter(@RequestBody FilterRouteDto filterDto);



}
