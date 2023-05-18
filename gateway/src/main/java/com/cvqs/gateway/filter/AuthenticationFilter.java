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
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * An AbstractGatewayFilterFactory subclass that inherits from the GatewayFilter class and takes a configuration
 * class as a parameter. This class performs authentication of a request and filters requests coming from
 * configured insecure endpoints. Additionally, this class injects a RouteValidator and a RestTemplate dependency.
 * Authentication requires a JWT token and a user role, and the authentication request is sent through an external service.
 * If the request does not comply with the authentication mechanism, it responds and returns the response.
 *
 * @author Enes Bekkaya
 * @since 25.03.2023
 */
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private RestTemplate restTemplate;


    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    String errorResponse = "Missing authorization header!";
                    return Mono.defer(() -> {
                        ServerHttpResponse response = exchange.getResponse();
                        LOGGER.warn(errorResponse);
                        return response.writeWith(Mono.just(response.bufferFactory().wrap(errorResponse.getBytes(StandardCharsets.UTF_8))));
                    });
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    ResponseEntity<String> response = restTemplate.getForEntity("http://host.docker.internal:9092/auth/validateToken?token=" + authHeader + "&role=" + config.getRole(), String.class);
                    LOGGER.info("Authentication request sent to the Auth API.");
                    String responseBody = response.getBody();
                    if (responseBody.equals("notValid")) {
                        return Mono.defer(() -> {
                            ServerHttpResponse responseNotValid  = exchange.getResponse();
                            LOGGER.warn("Invalid access!");
                            return responseNotValid .writeWith(Mono.just(responseNotValid .bufferFactory().wrap("Invalid access!".getBytes(StandardCharsets.UTF_8))));
                        });
                    }else if(responseBody.equals("unauthorized")){
                        return Mono.defer(() -> {
                            ServerHttpResponse responseUnauthorized  = exchange.getResponse();
                            LOGGER.warn("Unauthorized access!");
                            responseUnauthorized.setStatusCode(HttpStatus.UNAUTHORIZED);
                            return responseUnauthorized .writeWith(Mono.just(responseUnauthorized .bufferFactory().wrap("Unauthorized access!".getBytes(StandardCharsets.UTF_8))));
                        });
                    }

                } catch (Exception e) {
                    LOGGER.error("Failed to make request to the server!");
                    return Mono.defer(() -> {
                        ServerHttpResponse responseFailded  = exchange.getResponse();
                        return responseFailded .writeWith(Mono.just(responseFailded .bufferFactory().wrap("Failed to make request to the server!".getBytes(StandardCharsets.UTF_8))));
                    });
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
        return Arrays.asList("role");
    }
}