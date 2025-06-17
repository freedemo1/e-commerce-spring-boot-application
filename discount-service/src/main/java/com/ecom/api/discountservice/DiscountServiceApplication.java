package com.ecom.api.discountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class DiscountServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscountServiceApplication.class, args);
    }
}
