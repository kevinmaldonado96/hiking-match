package org.example.hikingmatchservice.security;

import jakarta.servlet.http.HttpServletResponse;
import org.example.hikingmatchservice.repository.IUserRepository;
import org.example.hikingmatchservice.security.filter.JwtAuthenticationFilter;
import org.example.hikingmatchservice.security.filter.JwtValidationFilter;
import org.example.hikingmatchservice.security.utils.JwtTokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private AuthenticationConfiguration authenticationConfiguration;
    private JwtTokenUtils jwtTokenUtils;
    private IUserRepository userRepository;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtTokenUtils jwtTokenUtils, IUserRepository userRepository) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/ping").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST,"/login").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenUtils))
                .addFilter(new JwtValidationFilter(authenticationManager(), jwtTokenUtils, userRepository))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, excep) ->
                                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, excep.getMessage())
                        )
                );

        return http.build();
    }
}
