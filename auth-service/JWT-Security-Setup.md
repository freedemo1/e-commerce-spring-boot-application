
# ✅ Final JWT Security Setup – Spring Boot Microservices

### 🔐 Authenticated via `auth-service`  
### 🔒 Secured via `cart-service`  
> 🗓️ *Final working configuration – as of June 15, 2025*

---

## 🧩 Overview

This architecture uses:
- **Stateless JWT authentication**
- **HS256 algorithm with shared secret**
- **Embedded roles in token (`roles: [ "ROLE_ADMIN" ]`)**
- **Spring Security + custom filter integration**

---

## 📌 `auth-service` – JWT Issuer

### ➕ Token Structure
```json
{
  "sub": "admin",
  "iss": "auth-service",
  "roles": ["ROLE_ADMIN"],
  "iat": 1750021574,
  "exp": 1750025174
}
```

### 🔐 Generation Logic
```java
String token = Jwts.builder()
    .setSubject("admin")
    .setIssuer("auth-service")
    .claim("roles", List.of("ROLE_ADMIN"))
    .setIssuedAt(new Date())
    .setExpiration(new Date(System.currentTimeMillis() + 3600000))
    .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
    .compact();
```

### ✅ Output
- Token is valid on [jwt.io](https://jwt.io)
- Signature verified using shared `jwt.secret`

---

## 📦 `cart-service` – Secured Microservice

### 🔧 `application.yml`
```yaml
jwt:
  secret: your-very-secret-key-here
```

### 🛡️ `JwtAuthenticationFilter`
```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/actuator") || path.equals("/health") || path.contains("/public")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            String token = authHeader.substring(7);
            Key key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);

            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            request.setAttribute("claims", claims);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
```

---

### 🔐 `SecurityConfig`
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/actuator/**", "/health").permitAll()
            .requestMatchers("/api/**").authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
}
```

---

## 🔁 Token Flow Summary

| Step | Action |
|------|--------|
| 1️⃣ | `auth-service` issues JWT with roles |
| 2️⃣ | Token sent in `Authorization: Bearer <token>` header |
| 3️⃣ | `cart-service` validates token in filter |
| 4️⃣ | Extracts `roles`, maps to `GrantedAuthority` |
| 5️⃣ | Sets Spring Security context |
| ✅ | Authenticated access to `/api/**` routes |

---

## 🧪 Sample Token (working)
```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6ImF1dGgtc2VydmljZSIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNzUwMDIxNTc0LCJleHAiOjE3NTAwMjUxNzR9.4MEEMCLpvysVdifMEdq2W5x5bYNEhl1U1T-pt-ynOVY
```

✅ Validated on jwt.io  
✅ Works with `ROLE_ADMIN` authority  
✅ Allows access to `/api/cart/{id}`

---

## ✅ Confirmed Working Aspects

- [x] JWT structure with `roles`
- [x] Signature verification
- [x] Spring Security context injection
- [x] Role-based `GrantedAuthority`
- [x] 401 for missing/invalid tokens
- [x] 403 for insufficient roles (if role checks added)

---

## 🛠️ Optional Enhancements
- `@PreAuthorize("hasRole('ADMIN')")` on controller methods
- Refresh tokens with rotation
- JWT expiry handling + renewal UI
- JWT blacklisting via Redis
- OAuth2 with real identity providers
