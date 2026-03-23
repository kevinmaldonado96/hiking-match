package org.example.hikingmatchservice.restclient.config;

import io.netty.channel.ChannelOption;
import org.example.hikingmatchservice.exceptions.HttpClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;


@Configuration
public class WebRestClientConfig {

    @Bean
    public WebClient.Builder webRestClientBuilder() {

        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(10))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filter(errorHandlingFilter());
    }

    private ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().isError()) {
                return clientResponse.bodyToMono(String.class)
                        .defaultIfEmpty("No response error")
                        .flatMap(body -> Mono.error(
                                new HttpClientException(
                                        clientResponse.statusCode().value(),
                                        body
                                )
                        ));
            }
            return Mono.just(clientResponse);
        });
    }

}
