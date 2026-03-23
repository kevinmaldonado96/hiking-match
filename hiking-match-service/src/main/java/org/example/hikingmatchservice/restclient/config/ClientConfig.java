package org.example.hikingmatchservice.restclient.config;

import org.example.hikingmatchservice.restclient.factory.ClientFactory;
import org.example.hikingmatchservice.restclient.interfaces.RouteClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public RouteClient routeClient(ClientFactory clientFactory) {
        //return clientFactory.createClient(RouteClient.class, "http://host.docker.internal:8080/api");
        return clientFactory.createClient(RouteClient.class, "http://localhost:8080/api");
    }
}
