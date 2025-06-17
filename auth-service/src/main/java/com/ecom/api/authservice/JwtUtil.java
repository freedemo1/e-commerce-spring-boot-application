package com.ecom.api.authservice;

import com.ecom.api.authservice.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(String username) {
        String secret = jwtProperties.getSecret();
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        List<String> roles = List.of("ROLE_ADMIN");
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuer("auth-service")
                .claim("roles", roles) // ✅ Add roles claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("Generated Token:");
        System.out.println(token);


        Key key1 = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));


        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key1)
                .build()
                .parseClaimsJws(token)
                .getBody();

        System.out.println("Subject: " + claims.getSubject());
        return token;
    }
}