package ru.improve.abs.exchange.service.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.improve.abs.exchange.service.service.TokenExchangeService;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final TokenExchangeService tokenExchangeService;

    private final WebClient webClient = WebClient.create();

    @RequestMapping("/**")
    public Mono<ResponseEntity<byte[]>> proxy(HttpServletRequest request,
                                              @RequestHeader("Authorization") String authHeader
    ) {
        String externalToken = authHeader.replace("Bearer ", "");
        return webClient.get()
                .uri("http://localhost:8081/exchange/" + externalToken)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(newToken -> forwardRequest(request, newToken));
    }

    private Mono<ResponseEntity<byte[]>> forwardRequest(HttpServletRequest originalRequest, String newToken) {
        String targetUri = "http://localhost:8082" + originalRequest.getRequestURI();

        return webClient.method(HttpMethod.valueOf(originalRequest.getMethod()))
                .uri(targetUri)
                .headers(headers -> {
                    headers.setBearerAuth(newToken);
                    Collections.list(originalRequest.getHeaderNames())
                            .forEach(name -> {
                                if (!name.equalsIgnoreCase("Authorization")) {
                                    headers.addAll(name, Collections.list(originalRequest.getHeaders(name)));
                                }
                            });
                })
                .body(BodyInserters.fromDataBuffers(DataBufferUtils.readInputStream(
                        originalRequest::getInputStream, new DefaultDataBufferFactory(), 4096)))
                .exchangeToMono(response -> response.toEntity(byte[].class));
    }
}

