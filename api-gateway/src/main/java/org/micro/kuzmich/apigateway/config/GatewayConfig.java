package org.micro.kuzmich.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // === User Service ===
                .route("user-service", r -> r.path("/api/users/**")
                        .filters(f -> f.circuitBreaker(c -> c
                                .setName("userServiceCB")
                                .setFallbackUri("forward:/fallback/users")
                        ))
                        .uri("lb://db-hometask-kuzmich"))

                // === Notification Service ===
                .route("notification-service", r -> r.path("/api/mail/**")
                        .filters(f -> f.circuitBreaker(c -> c
                                .setName("notificationServiceCB")
                                .setFallbackUri("forward:/fallback/mail")
                        ))
                        .uri("lb://notification-service"))

                .build();
    }

}