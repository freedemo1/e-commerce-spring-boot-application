package com.ecom.api.catalogue.service;

import com.ecom.api.catalogue.dto.ProductRequest;
import com.ecom.api.catalogue.event.ProductCreatedEvent;
import com.ecom.api.catalogue.model.Product;
import com.ecom.api.catalogue.repository.ProductRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public ProductService(ProductRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackSendToKafka")
    @Retry(name = "productService")
    public void addProduct(ProductRequest request) {
        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .build();

        repository.save(product);
        ProductCreatedEvent event = new ProductCreatedEvent(product.getId(), product.getName(), product.getPrice());
        kafkaTemplate.send("product-events", event);
        log.info("product added ...");

    }

    public void fallbackSendToKafka(ProductRequest request, Throwable ex) {
        // Optionally store the product or event in a fallback queue or log for retry
        log.warn("Fallback: Failed to send Kafka event for product: {}", request.name(), ex);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }
}