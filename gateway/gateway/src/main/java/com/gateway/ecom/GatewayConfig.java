package com.gateway.ecom;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    /*
    * 1. Here we implementing the circuitBreaker logic if that particular service is down
    *     then we sending fallback response which is written in fallbackController class for each service
    *      different response
    * 2. Once service is up , we used rewrite paths, because from gateway we can access different name
    *    but it has to communicate with particular service as per the eureka names
    * 3. so we use rewrite path and it will redirect to particular service it fetches the details from eureka
    * */

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("product",r->r
                        .path("/ecom/products/**")
                        .filters(f->f
                                .circuitBreaker(config->config
                                    .setName("ecomProductBreaker")
                                    .setFallbackUri("forward:/fallback/products"))
                                .rewritePath("/ecom/products(?<segment>/?.*)",
                                        "/api/products${segment}" )
                        )
                        .uri("lb://PRODUCT")
                )
                .route("user-service",r->r
                        .path("/ecom/users/**")
                        .filters(f->f
                                .rewritePath("/ecom/users(?<segment>/?.*)",
                                "/api/users${segment}")
                                .circuitBreaker(config-> config
                                        .setName("ecomUsersBreaker")
                                        .setFallbackUri("forward:/fallback/users")
                                )
                        )
                        .uri("lb://USER-SERVICE")
                )
                .route("order-service",r->r
                        .path("/ecom/orders/**","/ecom/cart/**")
                        .filters(f->f
                                .rewritePath("/ecom/(?<segment>.*)",
                                "/api/${segment}")
                                .circuitBreaker(config->config
                                        .setName("ecomOrderBreaker")
                                        .setFallbackUri("forward:/fallback/orders")
                                )
                        )
                        .uri("lb://ORDER-SERVICE")
                )
                .route("eureka-server",r->r
                        .path("/eureka/main")
                        .filters(f->f.rewritePath("/eureka/main","/"))
                        .uri("http://localhost:8761")
                )
                .route("eureka-server-static",r->r
                        .path("/eureka/**")
                        .uri("http://localhost:8761")
                )
                .build();
    }
}
