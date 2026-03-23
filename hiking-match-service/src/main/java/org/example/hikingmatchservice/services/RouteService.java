package org.example.hikingmatchservice.services;

import org.example.hikingmatchservice.dto.FilterRouteDto;
import org.example.hikingmatchservice.dto.RouteDto;
import org.example.hikingmatchservice.dto.UserDto;
import org.example.hikingmatchservice.entities.User;
import org.example.hikingmatchservice.repository.IUserRepository;
import org.example.hikingmatchservice.restclient.interfaces.RouteClient;
import org.example.hikingmatchservice.security.custom.CustomUser;
import org.example.hikingmatchservice.services.iService.IRouteService;
import org.springframework.stereotype.Service;
import org.springframework.util.RouteMatcher;

import java.util.List;

@Service
public class RouteService implements IRouteService {

    private final RouteClient routeClient;
    private final IUserRepository userRepository;

    public RouteService(RouteClient routerClient, IUserRepository userRepository) {
        this.routeClient = routerClient;
        this.userRepository = userRepository;
    }

    public List<RouteDto> getRoutes() {
        return this.routeClient.getAllRoutes().collectList().block();
    }

    public List<RouteDto> filterRoute(FilterRouteDto filterDto) {
        return this.routeClient.getRouterByFilter(filterDto);
    }

    public RouteDto registerUserRoute(CustomUser customUser, String routeId) {
        User user = userRepository.getReferenceById(customUser.getId());

        UserDto userDto = UserDto.fromUser(user);
        return this.routeClient.registerUserRoute(userDto, routeId).block();
    }
}
