package com.bharti.gateway.security.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Log4j2
public class JwtAuthEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String errorResponse = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseMap = mapper.createObjectNode();
            responseMap.put("error", ex.getMessage());
            responseMap.put("message",  "Unauthorized access !!");
            responseMap.put("status", HttpStatus.UNAUTHORIZED.value());
            responseMap.put("timestamp", new Date().getTime());
            errorResponse = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("JwtAuthEntryPoint :: " + errorResponse);
        byte[] bytes = errorResponse.getBytes(StandardCharsets.UTF_8);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
    }
}
