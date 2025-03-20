package org.example.springcloudapigatewayservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


//@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    private Environment env;

    //@Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/first-service/**")
                        .filters(f ->
                            f.addRequestHeader("first-request", "first-value")
                            .addResponseHeader("first-response", "first-value"))
                            .uri("http://localhost:8001"))
                .route(r -> r.path("/second-service/**")
                        .uri("http://localhost:8002"))
                .build();
    }
}
