package org.example.hikingmatchservice.services.iService;

import org.example.hikingmatchservice.dto.FilterRouteDto;
import org.example.hikingmatchservice.dto.RouteDto;
import org.example.hikingmatchservice.entities.User;
import org.example.hikingmatchservice.security.custom.CustomUser;

import java.util.List;

public interface IRouteService {

    List<RouteDto> getRoutes();

    List<RouteDto> filterRoute(FilterRouteDto filterDto);

    RouteDto registerUserRoute(CustomUser user, String routeId);
}
