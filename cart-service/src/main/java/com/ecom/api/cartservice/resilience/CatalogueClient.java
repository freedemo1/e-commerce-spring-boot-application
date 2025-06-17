package com.ecom.api.cartservice.resilience;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CatalogueClient {

    @CircuitBreaker(name = "catalogueService", fallbackMethod = "fallback")
    @Retry(name = "catalogueService")
    public String getCatalogue() {
        throw new RuntimeException("Simulated catalogue service failure");
    }

    public String fallback(Throwable t) {
        log.warn("Fallback executed due to: {}", t.getMessage());
        return "Catalogue currently unavailable";
    }
}