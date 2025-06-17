/*
package com.ecom.api.gateway.filter;


import com.ecom.api.gateway.auth.config.JwtProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
@Order(-1)

public class JwtAuthenticationGlobalFilter implements GlobalFilter, Ordered {

    private final JwtProperties jwtProperties;

    public JwtAuthenticationGlobalFilter(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // Allow public endpoints
        if (path.startsWith("/api/catalogue/public")
                || path.startsWith("/actuator")
                || path.equals("/health")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty(); //stop the chain
            //return exchange.getResponse().setComplete();
        }

        try {
            String token = authHeader.substring(7);
            ObjectMapper mapper = new ObjectMapper();
            TokenClass tokenObj = mapper.readValue(token, TokenClass.class);

            Key key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(tokenObj.getToken())
                    .getBody();

            // Optional: Pass username in header to downstream services
            exchange.getRequest().mutate()
                    .header("X-User", claims.getSubject())
                    .build();

            return chain.filter(exchange);
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty();//stop the chain
            // return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1; // Ensure this runs early
    }
}
*/
