package org.example.restclient.config;

import org.example.restclient.factory.ClientFactory;
import org.example.restclient.interfaces.RouteClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public RouteClient routeClient(ClientFactory clientFactory, ClientProperties props) {
        return clientFactory.createClient(RouteClient.class, props.getUrl("routes"));
    }
}
