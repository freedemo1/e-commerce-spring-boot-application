package com.ecom.api.cartservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartSagaListener {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order-created", groupId = "cart-service")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Cart Service received OrderCreatedEvent: {}", event);

        boolean validCart = event.getProductIds() != null && !event.getProductIds().isEmpty(); // dummy logic

        if (validCart) {
            kafkaTemplate.send("stock-reserved",
                    StockReservedEvent.builder()
                            .orderId(event.getOrderId())
                            .success(true)
                            .build());
        } else {
            kafkaTemplate.send("stock-reservation-failed", event.getOrderId());
        }
    }
}