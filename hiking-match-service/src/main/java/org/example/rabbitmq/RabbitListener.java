package org.example.rabbitmq;

import org.example.dto.RouteDto;
import org.example.restclient.interfaces.RouteClient;
import org.example.services.iService.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitListener {

    @Autowired
    private IRouteService routeService;
    @Autowired
    private RouteClient routeClient;

    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "${rabbitmq.new.queue.name}")
    public void recibirMensajes(RouteDto routeDto) {
        System.out.println("Recibiendo mensajes "+routeDto.getName());
        routeService.sendNotificationNewRoute(routeDto);

    }


}
