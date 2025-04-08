package ru.improve.abs.gateway.service.configuration;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.improve.abs.gateway.service.util.TokenUtil;


@Component
public class TokenExchangeFilter implements GlobalFilter {

    private final WebClient authWebClient;

    public TokenExchangeFilter(WebClient.Builder webClientBuilder) {
        this.authWebClient = webClientBuilder.baseUrl("http://localhost:8071").build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String extractedToken = TokenUtil.extractToken(exchange.getRequest().getHeaders());

        WebClient.ResponseSpec responseSpec = authWebClient.post()
                .uri("token_exchange")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + extractedToken)
                .retrieve();

        var body = responseSpec.bodyToMono(String.class);
        System.out.println(body);

        return chain.filter(exchange);

        /*return authWebClient.post()
                .uri("/internal/token/convert")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + externalToken)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(internalToken -> {
                    ServerHttpRequest mutatedRequest = exchange.getRequest()
                            .mutate()
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + internalToken)
                            .build();
                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                });*/
    }
}
