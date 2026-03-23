package org.example.hikingroutesservice.services.impl;

import org.example.hikingroutesservice.dto.FilterDto;
import org.example.hikingroutesservice.dto.InjuryDto;
import org.example.hikingroutesservice.dto.RouterDto;
import org.example.hikingroutesservice.dto.UserDto;
import org.example.hikingroutesservice.dto.enums.ActionEnum;
import org.example.hikingroutesservice.dto.enums.InjurySeverity;
import org.example.hikingroutesservice.exception.exceptions.DuplicateRouteException;
import org.example.hikingroutesservice.exception.exceptions.InjuryException;
import org.example.hikingroutesservice.exception.exceptions.RouteNotFoundException;
import org.example.hikingroutesservice.exception.exceptions.UserAlreadyRegisteredException;
import org.example.hikingroutesservice.models.User;
import org.example.hikingroutesservice.models.enums.Difficulty;
import org.example.hikingroutesservice.rabbitmq.RouterPublisher;
import org.example.hikingroutesservice.repository.RouteRepository;
import org.example.hikingroutesservice.models.Route;
import org.example.hikingroutesservice.services.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteServiceImpl implements IRouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    private RouterPublisher routerPublisher;

    @Override
    public Mono<Object> createRoute(Route route) {
        return routeRepository.findRouteByName(route.getName())
                .flatMap(routeExisting -> Mono.error(new DuplicateRouteException("Route already exists")))
                .switchIfEmpty(routeRepository.save(route))
                .flatMap(routeCreated -> {
                    RouterDto routerDto = RouterDto
                            .builder()
                            .name(route.getName())
                            .action(ActionEnum.CREATE)
                            .description(route.getDescription())
                            .limit(route.getLimit())
                            .difficulty(route.getDifficulty())
                            .tags(route.getTags())
                            .temperatureRange(route.getTemperatureRange())
                            .build();

                    return routerPublisher.publishNewRoute(routerDto).thenReturn(routeCreated);
                });

    }

    @Override
    public Mono<Route> updateRoute(String id, Route route) {
        return routeRepository.findById(id)
                .switchIfEmpty(Mono.error(new RouteNotFoundException("Route not found")))
                .flatMap(existingRoute -> {
            existingRoute.setName(route.getName());
            existingRoute.setDescription(route.getDescription());
            existingRoute.setDifficulty(route.getDifficulty());
            existingRoute.setTags(route.getTags());
            existingRoute.setLimit(route.getLimit());
            existingRoute.setTemperatureRange(route.getTemperatureRange());
            return routeRepository.save(existingRoute);
        });
    }

    @Override
    public Flux<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    @Override
    public Mono<Route> getRouteById(String id) {
        return routeRepository.findById(id).switchIfEmpty(Mono.error(new RouteNotFoundException("Route not found")));
    }

    @Override
    public Mono<Route> registerUserRoute(UserDto userDto, String id) {

        return routeRepository.findById(id)
                .switchIfEmpty(Mono.error(new RouteNotFoundException("Route not found")))
                .map(route -> {
                    this.validateRouteUser(route, userDto);
                    return route;
                })
                .flatMap(route -> {

                    if (route.routeHasUser(userDto.getId())) {
                        return Mono.error(new UserAlreadyRegisteredException("User already registered in the route"));
                    }
                    User user = User.fromDtoToUser(userDto);
                    route.addUser(user);
                    return routeRepository.save(route);
                });

    }

    private void validateRouteUser(Route route, UserDto userDto) {

       switch (route.getDifficulty()){
           case EASY -> {
           }
           case MEDIUM -> {
                if (userDto.getIsInjury()){
                    InjuryDto injuryDto = userDto.getInjuries().stream().filter(dto -> InjurySeverity.GRAVE.equals(dto.getSeverity())).findFirst().orElse(null);

                    if (injuryDto != null){
                        throw new InjuryException("If the user has a serious injury, it could be a risk to their safety.");
                    }
                }

            }
           case HARD -> {
               if (userDto.getIsInjury()){
                   throw new InjuryException("If the user has an injury, it could be a risk to their safety.");
               }
           }
        };
    }

    @Override
    public Flux<Route> getRoutesByFilter(FilterDto filterDto) {

        List<Criteria> criterias = new ArrayList<>();

        if (filterDto.getName() != null) {
            criterias.add(Criteria.where("name").regex(filterDto.getName(), "i"));
        }

        if (filterDto.getDifficulty() != null) {
            criterias.add(Criteria.where("difficulty").is(filterDto.getDifficulty()));
        }

        if (filterDto.getTags() != null ) {
            criterias.add(Criteria.where("tags").in(filterDto.getTags()));
        }

        if (filterDto.getTemperatureRange() != null) {
            Long tempRangeMin = filterDto.getTemperatureRange().get(0);
            Long tempRangeMax = filterDto.getTemperatureRange().get(1);

            criterias.add(new Criteria().andOperator(
                    Criteria.where("temperatureRange.1").gte(tempRangeMin),
                    Criteria.where("temperatureRange.0").lte(tempRangeMax)
            ));
        }

        Criteria finalCriteria = new Criteria();
        if (!criterias.isEmpty()) {
            finalCriteria = new Criteria().andOperator(criterias);
        }

        Query query = new Query(finalCriteria);

        return reactiveMongoTemplate.find(query, Route.class);
    }
}
