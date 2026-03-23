package org.example.hikingroutesservice.interceptors;

import org.example.hikingroutesservice.services.ILogHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class AuditoryHttpInterceptor implements WebFilter {

    @Autowired
    private ILogHttpService logHttpService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String serverHost = exchange.getRequest().getURI().getHost();
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();
        String ip = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();

        String log = "Entrada de la petición " + ip + ":" + serverHost + ":" + method;

        logHttpService.saveLogHttp(serverHost, path, method, ip, log);

        return chain.filter(exchange);
    }
}
