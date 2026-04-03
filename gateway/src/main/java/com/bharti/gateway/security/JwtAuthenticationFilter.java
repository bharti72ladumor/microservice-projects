package com.bharti.gateway.security;

import com.bharti.gateway.model.CustomUser;
import com.bharti.gateway.model.UserRepository;
import com.bharti.gateway.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.security.Key;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter, Ordered {
    private final String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var request = exchange.getRequest();
        var headers = request.getHeaders();
        System.out.println("filter called........." + request.getHeaders());

        if (authMissing(request)) {
            return chain.filter(exchange);
        }

        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);
        String userName = jwtUtils.extractUsername(token);

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUser user = userDetailsService.loadUserByUsername(userName);
            if (Boolean.TRUE.equals(jwtUtils.validateToken(token, user.getUsername()))) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                        null, user.getAuthorities());
                return chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
//                return Mono.deferContextual(context -> {
//                    SecurityContext securityContext = new SecurityContext() {
//                        @Override
//                        public Authentication getAuthentication() {
//                            return authentication;
//                        }
//
//                        @Override
//                        public void setAuthentication(Authentication authentication) {
//                            // not used in this example
//                        }
//                    };
//                    return chain.filter(exchange)
//                            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
//                });
            }
        }
        return chain.filter(exchange);
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
    }
}
