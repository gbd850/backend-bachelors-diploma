package edu.pollub.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

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
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Authorization not found");
                }
                final String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token not found");
                }
                final String jwt = authHeader.substring(7);

                MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

                bodyValues.add("token", jwt);

                Boolean isTokenValid = webClientBuilder.build().post()
                        .uri("http://account-service/api/user/validateToken")
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(BodyInserters.fromFormData(bodyValues))
                        .retrieve()
                        .bodyToMono(Boolean.class)
                        .block();
                if (Boolean.FALSE.equals(isTokenValid)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
                }
            }
            return chain.filter(exchange);
        });
    }
}
