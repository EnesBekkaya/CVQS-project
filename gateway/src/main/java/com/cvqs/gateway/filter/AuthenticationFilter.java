package com.cvqs.gateway.filter;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>  {

    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private RestTemplate restTemplate;


    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            var request = exchange.getRequest();
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    ResponseEntity<Boolean> response = restTemplate.getForEntity("http://localhost:9092/auth/validateToken?token="+authHeader+"&role="+config.getRole(), Boolean.class);
                    Boolean isTokenValid= response.getBody();

                    if (!isTokenValid){
                        var responsee = exchange.getResponse();
                        responsee.setStatusCode(HttpStatus.UNAUTHORIZED);
                        System.out.println("Invalid access...!");

                        return responsee.setComplete();
                    }

                } catch (Exception e) {
                    System.out.println("Request failed...!");
                    throw new RuntimeException("Failed to make request to the server");
                }
            }
            return chain.filter(exchange);
        });
    }

    @Data
    public static class Config {
        private String role;
    }
    @Override
    public List<String> shortcutFieldOrder() {
        // we need this to use shortcuts in the application.yml
        return Arrays.asList("role");
    }
}