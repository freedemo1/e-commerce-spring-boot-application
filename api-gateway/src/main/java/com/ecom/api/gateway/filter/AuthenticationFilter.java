package com.ecom.api.gateway.filter;

import com.ecom.api.gateway.auth.config.JwtProperties;
import com.ecom.api.gateway.auth.config.TokenClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@ConfigurationProperties(prefix = "jwt")
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/catalogue/public") || path.startsWith("/actuator") || path.equals("/health");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        final String token = authHeader.substring(7);
        try {
            ObjectMapper mapper = new ObjectMapper();
            TokenClass tokenObj = mapper.readValue(token, TokenClass.class);

            Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecret().getBytes())
                .parseClaimsJws(tokenObj.getToken())
                .getBody();
            request.setAttribute("claims", claims);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}