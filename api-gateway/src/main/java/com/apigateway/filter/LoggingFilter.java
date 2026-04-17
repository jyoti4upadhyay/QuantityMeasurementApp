package com.apigateway.filter;


import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class LoggingFilter {

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {
            System.out.println("Incoming Request: " +
                    exchange.getRequest().getMethod() + " " +
                    exchange.getRequest().getURI());
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() ->
                            System.out.println("Response Status: " +
                                    exchange.getResponse().getStatusCode())));
        };
    }
}