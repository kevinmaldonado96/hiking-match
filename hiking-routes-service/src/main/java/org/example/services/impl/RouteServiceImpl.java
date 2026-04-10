package org.example.services.impl;

import org.example.dto.FilterDto;
import org.example.dto.InjuryDto;
import org.example.dto.RouterDto;
import org.example.dto.UserDto;
import org.example.dto.enums.ActionEnum;
import org.example.dto.enums.InjurySeverity;
import org.example.exception.exceptions.*;
import org.example.models.User;
import org.example.rabbitmq.RouterPublisher;
import org.example.repository.RouteRepository;
import org.example.models.Route;
import org.example.services.IRouteService;
import org.example.services.criteria.RouteCriteria;
import org.example.services.criteria.impl.DifficultyCriteria;
import org.example.services.criteria.impl.NameCriteria;
import org.example.services.criteria.impl.TagsCriteria;
import org.example.services.criteria.impl.TemperatureCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

                    if (route.getParticipants() >= route.getLimit()){
                        return Mono.error(new ExceedingLimitException("The limit of participants in the walk has been exceeded"));
                    }

                    if (route.routeHasUser(userDto.getId())) {
                        return Mono.error(new UserAlreadyRegisteredException("User already registered in the route"));
                    }
                    User user = User.fromDtoToUser(userDto);
                    route.addUser(user);
                    route.setParticipants(route.getUsers().size());

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

            List<Criteria> criterias = getRouteCriterias().stream()
                    .map(c -> c.build(filterDto))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            Criteria finalCriteria = criterias.isEmpty() ? new Criteria() : new Criteria().andOperator(criterias);
            Query query = new Query(finalCriteria);

            return reactiveMongoTemplate.find(query, Route.class);
        }


    public List<RouteCriteria> getRouteCriterias() {
        return List.of(
                new NameCriteria(),
                new DifficultyCriteria(),
                new TagsCriteria(),
                new TemperatureCriteria()
        );
    }
}
