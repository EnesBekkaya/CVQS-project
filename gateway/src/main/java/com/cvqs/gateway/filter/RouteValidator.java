package com.cvqs.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * Bu sınıf, gelen bir sunucu isteğinin güvenli olup olmadığını belirlemek için kullanılır.
 * @author Enes Bekkaya
 * @since  25.03.2023
 */
@Component
public class RouteValidator {
    /**
     * Açık API uç noktalarının listesi.
     */
    public static final List<String> openApiEndpoints = List.of(
            "/auth/authenticate",
            "/users/save",
            "/terminals/**",
            "/eureka"
    );
    /**
     * Verilen bir sunucu isteğinin güvenli olup olmadığını belirlemek için kullanılan bir predikattır.
     * @param request kontrol edilecek sunucu isteği
     * @return sunucu isteğinin güvenli olup olmadığını belirten boolean değer
     */
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
