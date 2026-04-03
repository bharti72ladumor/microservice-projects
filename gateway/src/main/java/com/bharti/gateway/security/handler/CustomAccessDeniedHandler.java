package com.bharti.gateway.security.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String errorResponse = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseMap = mapper.createObjectNode();
            responseMap.put("error",denied.getMessage());
            responseMap.put("message", "Access Denied !!");
            responseMap.put("status", HttpStatus.FORBIDDEN.value());
            responseMap.put("timestamp", new Date().getTime());
            errorResponse = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("CustomAccessDeniedHandler :: " + errorResponse);
        byte[] bytes = errorResponse.getBytes(StandardCharsets.UTF_8);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
    }
}
