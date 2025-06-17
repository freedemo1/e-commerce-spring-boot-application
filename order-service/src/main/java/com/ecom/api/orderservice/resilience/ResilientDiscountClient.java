package com.ecom.api.orderservice.resilience;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResilientDiscountClient {

    @CircuitBreaker(name = "discountService", fallbackMethod = "fallbackGetDiscount")
    @Retry(name = "discountService")
    public String getDiscount(String userId) {
        // Simulate remote call (e.g., to discount-service)
        throw new RuntimeException("Simulated failure when calling discount-service");
    }

    public String fallbackGetDiscount(String userId, Throwable ex) {
        log.warn("Fallback triggered for userId={} due to {}", userId, ex.getMessage());
        return "No discount available";
    }
}