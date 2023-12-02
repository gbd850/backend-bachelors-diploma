package edu.pollub.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    public static class Config {

    }

    @Autowired
    private RouteValidator validator;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization not found");
                }
                final String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token not found");
                }
                final String jwt = authHeader.substring(7);

                String body = "{\n" +
                        "\"token\":\""+jwt +
                        "\"\n}";

                return webClientBuilder.build().post()
                        .uri("http://account-service/api/user/validateToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(body))
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, response ->
                            Mono.error(new WebClientResponseException(HttpStatus.UNAUTHORIZED, "Invalid token", null, null, null, null))
                        )
                        .bodyToMono(Boolean.class)
                        .flatMap(s -> chain.filter(exchange));
            }
            return chain.filter(exchange);
        });
    }
}
