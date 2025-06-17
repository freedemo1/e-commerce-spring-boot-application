package com.ecom.api.cartservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventListener {

    @KafkaListener(topics = "order-created", groupId = "cart-group")
    public void listen(OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent in Cart Service: {}", event);
        // Handle logic like clearing items from cart, etc.
    }
}