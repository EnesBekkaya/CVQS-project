package com.cvqs.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * This class is used to determine whether an incoming server request is secure or not.
 * @author Enes Bekkaya
 * @since  25.03.2023
 */
@Component
public class RouteValidator {
    /**
     * List of open API endpoints.
     */
    public static final List<String> openApiEndpoints = List.of(
            "/users/login",
            "/users/save",
            "/roles/save",
            "/eureka"
    );
    /**
     * A predicate used to determine whether a given server request is secure or not.
     * @param request the server request to be checked
     * @return a boolean value indicating whether the server request is secure or not
     */
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
