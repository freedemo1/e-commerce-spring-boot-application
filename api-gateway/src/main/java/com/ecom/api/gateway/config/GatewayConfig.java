package com.ecom.api.gateway.config;

import com.ecom.api.gateway.filter.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class    GatewayConfig {

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> jwtFilter(AuthenticationFilter filter) {
        FilterRegistrationBean<AuthenticationFilter> regBean =
                new FilterRegistrationBean<>();
        regBean.setFilter(filter);
        regBean.addUrlPatterns("/api/orders/*",
                "/api/cart/*",
                "/api/catalogue/*",
                "/api/discount/*");
        return regBean;
    }
}