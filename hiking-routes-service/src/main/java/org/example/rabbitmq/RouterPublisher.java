package org.example.rabbitmq;

import org.example.dto.RouterDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RouterPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.new.topic.name}")
    private String routeNewTopicExchangeName;

    @Value("${rabbitmq.new.routing.key}")
    private String routeNewRoutingKey;

    public Mono<Void> publishNewRoute(RouterDto route) {
       return Mono.fromRunnable(() -> {
           try {
                       rabbitTemplate.convertAndSend(routeNewTopicExchangeName, routeNewRoutingKey, route);
                   } catch (Exception e) {
                       throw new RuntimeException(e);
                   }
               }
        );
    }
}
