package org.example.restclient.config;

import io.netty.channel.ChannelOption;
import org.example.exceptions.HttpClientException;
import org.example.restclient.utils.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;


@Configuration
public class WebRestClientConfig {

    private final TokenProvider tokenProvider;

    public WebRestClientConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public WebClient.Builder webRestClientBuilder() {

        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(10))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filter(addTokenRequestFilter())
                .filter(errorHandlingFilter());
    }

    private ExchangeFilterFunction addTokenRequestFilter() {
        return (request, next) -> {
            String token = tokenProvider.getToken();

            if(token != null) {
                var newRequest = ClientRequest.from(request).headers(header -> header.setBearerAuth(token)).build();
                return next.exchange(newRequest);
            }
            return next.exchange(request);
        };
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
