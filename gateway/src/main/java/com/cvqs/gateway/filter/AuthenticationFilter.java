package com.cvqs.gateway.filter;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private static final Logger LOGGER= LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            var request = exchange.getRequest();
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    LOGGER.error("missing authorization header");

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
                        LOGGER.error("Invalid access...!");

                        return responsee.setComplete();
                    }

                } catch (Exception e) {
                    LOGGER.error("Failed to make request to the server");
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