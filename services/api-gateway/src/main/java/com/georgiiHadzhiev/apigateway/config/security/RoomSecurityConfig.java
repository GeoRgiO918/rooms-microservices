package com.georgiiHadzhiev.apigateway.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class RoomSecurityConfig {


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtAuthenticationConverter jwtConverter) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.GET, "/rooms/**").hasRole("user")
                        .pathMatchers(HttpMethod.POST, "/rooms").hasRole("admin")
                        .pathMatchers(HttpMethod.PUT, "/rooms").hasRole("admin")
                        .pathMatchers(HttpMethod.DELETE, "/rooms").hasRole("admin")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->  oauth2
                        .jwt(jwtSpec -> jwtSpec
                                .jwtAuthenticationConverter(new ReactiveJwtAuthenticationConverterAdapter(jwtConverter))
                        ));

        return http.build();
    }
}
