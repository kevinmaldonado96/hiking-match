package org.example.controllers;

import org.example.dto.FilterRouteDto;
import org.example.security.custom.CustomUser;
import org.example.services.iService.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
    private IRouteService routeService;

    @GetMapping("/all")
    public ResponseEntity<?> getRoutes() {
        return ResponseEntity.ok(routeService.getRoutes());
    }

    @PostMapping("/search")
    public ResponseEntity<?> filterRoutes(@RequestBody FilterRouteDto filterRouteDto) {
        return ResponseEntity.ok(routeService.filterRoute(filterRouteDto));
    }

    @PutMapping("/{routeId}/register")
    public ResponseEntity<?> registerUserRoute(@PathVariable("routeId") String routeId, Authentication authentication) {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        return ResponseEntity.ok(routeService.registerUserRoute(user, routeId));
    }

}
