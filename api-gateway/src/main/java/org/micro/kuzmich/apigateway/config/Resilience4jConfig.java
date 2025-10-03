package org.micro.kuzmich.apigateway.config;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import io.github.resilience4j.common.timelimiter.configuration.TimeLimiterConfigCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public CircuitBreakerConfigCustomizer userServiceCustomizer() {
        return CircuitBreakerConfigCustomizer
                .of("userServiceCB", builder -> builder
                        .slidingWindowSize(100)
                        .failureRateThreshold(50)
                        .waitDurationInOpenState(Duration.ofSeconds(60))
                        .permittedNumberOfCallsInHalfOpenState(10)
                        .automaticTransitionFromOpenToHalfOpenEnabled(false));
    }

    @Bean
    public CircuitBreakerConfigCustomizer notificationServiceCustomizer() {
        return CircuitBreakerConfigCustomizer
                .of("notificationServiceCB", builder -> builder
                        .slidingWindowSize(100)
                        .failureRateThreshold(50)
                        .waitDurationInOpenState(Duration.ofSeconds(60))
                        .permittedNumberOfCallsInHalfOpenState(10)
                        .automaticTransitionFromOpenToHalfOpenEnabled(false));
    }

}
