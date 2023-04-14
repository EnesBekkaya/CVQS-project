package com.cvqs.gateway.filter;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * GatewayFilter sınıfını miras alan ve bir yapılandırma sınıfını parametre olarak alan
 *  AbstractGatewayFilterFactory alt sınıfıdır. Bu sınıf, bir isteğin kimlik doğrulamasını yapar
 * ve yapılandırılmış olan güvenli olmayan endpoint'lerden gelen istekleri filtreler. Bu sınıf ayrıca
 * bir RouteValidator ve bir RestTemplate bağımlılığı enjekte eder. Doğrulama için bir
 * JWT token'ı ve kullanıcı rolü gerekir ve doğrulama isteği bir harici servis aracılığıyla gönderilir.
 * İsteğin kimlik doğrulama mekanizmasına uymadığı durumda yanıt verir ve yanıtı döndürür.
 *
 * @author Enes Bekkaya
 * @since  25.03.2023
 */
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
                    LOGGER.warn("missing authorization header");

                    var responseMissing = exchange.getResponse();
                    responseMissing.setStatusCode(HttpStatus.UNAUTHORIZED);
                    responseMissing.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    String errorResponse = " Missing authorization header...!";
                    var bufferFactory = responseMissing.bufferFactory();
                    var dataBuffer = bufferFactory.wrap(errorResponse.getBytes(StandardCharsets.UTF_8));
                    return responseMissing.writeWith(Mono.just(dataBuffer));
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    ResponseEntity<Boolean> response = restTemplate.getForEntity("http://host.docker.internal:9092/auth/validateToken?token="+authHeader+"&role="+config.getRole(), Boolean.class);
                    LOGGER.info("Auth API'ye doğrulama isteği gönderildi.");
                    Boolean isTokenValid= response.getBody();

                    if (!isTokenValid){
                        var responseUnauthorized = exchange.getResponse();
                        responseUnauthorized.setStatusCode(HttpStatus.UNAUTHORIZED);
                        responseUnauthorized.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                        String errorResponse = " Error: Invalid access...! ";
                        var bufferFactory = responseUnauthorized.bufferFactory();
                        var dataBuffer = bufferFactory.wrap(errorResponse.getBytes(StandardCharsets.UTF_8));
                        return responseUnauthorized.writeWith(Mono.just(dataBuffer));
                    }

                } catch (Exception e) {
                    LOGGER.warn("Failed to make request to the server");
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
        return Arrays.asList("role");
    }
}