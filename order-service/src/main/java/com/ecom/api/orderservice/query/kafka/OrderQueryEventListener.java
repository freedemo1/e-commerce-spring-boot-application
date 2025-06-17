package com.ecom.api.orderservice.query.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderQueryEventListener {

    @KafkaListener(topics = "order-created", groupId = "order-query-group")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Read Model Received OrderCreatedEvent: {}", event);
        // In a real async CQRS, you'd write to a separate read DB here
    }
}