package edu.pollub.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> WHITELIST_URLS = List.of(
            "/api/user/register",
            "/api/user/login",
            "/api/user/validateToken"
    );

    public Predicate<ServerHttpRequest> isSecured =
            serverHttpRequest -> WHITELIST_URLS.stream()
                    .noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));
}
