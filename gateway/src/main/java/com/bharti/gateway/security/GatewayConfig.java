package com.bharti.gateway.security;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class GatewayConfig implements WebFluxConfigurer {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("student-service", r -> r.path("/student/**")
//                        .filters(f -> {
//                                    return  f.filter(jwtAuthenticationFilter); // Apply JwtAuthenticationFilter
//                        })
                        .uri("lb://student-service"))
                .route("school-service", r -> r.path("/school/**")
                        .uri("lb://school-service"))
                .build();
    }
}
