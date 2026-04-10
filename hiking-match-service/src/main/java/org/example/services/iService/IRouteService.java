package org.example.services.iService;

import org.example.dto.FilterRouteDto;
import org.example.dto.RouteDto;
import org.example.security.custom.CustomUser;

import java.util.List;

public interface IRouteService {

    List<RouteDto> getRoutes();

    List<RouteDto> filterRoute(FilterRouteDto filterDto);

    RouteDto registerUserRoute(CustomUser user, String routeId);

    void sendNotificationNewRoute(RouteDto routeDto);

}
