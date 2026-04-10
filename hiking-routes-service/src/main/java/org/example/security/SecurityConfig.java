package org.example.security;

import org.example.security.filter.JwtWebfluxValidationFilter;
import org.example.security.utils.JwtTokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtTokenUtils jwtTokenUtils;

    public SecurityConfig(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Bean
    public JwtWebfluxValidationFilter jwtWebfluxValidationFilter() {
        return new JwtWebfluxValidationFilter(jwtTokenUtils);
    }

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange.anyExchange().permitAll())
                .addFilterAt(jwtWebfluxValidationFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }
}
