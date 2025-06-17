package com.ecom.api.orderservice.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter(JwtAuthenticationFilter filter) {
        FilterRegistrationBean<JwtAuthenticationFilter> regBean = new FilterRegistrationBean<>();
        regBean.setFilter(filter);
        regBean.addUrlPatterns("/api/orders/*", "/api/query/orders/*");
        return regBean;
    }
}