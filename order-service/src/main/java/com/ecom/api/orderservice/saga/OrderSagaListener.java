package com.ecom.api.orderservice.saga;


import com.ecom.api.orderservice.model.OrderStatus;
import com.ecom.api.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderSagaListener {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "stock-reserved", groupId = "order-service")
    public void handleStockReserved(StockReservedEvent event) {
        log.info("Received StockReservedEvent: {}", event);
        orderRepository.findById(Long.parseLong(event.getOrderId())).ifPresent(order -> {
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
        });
    }

    @KafkaListener(topics = "stock-reservation-failed", groupId = "order-service")
    public void handleStockFailed(String orderId) {
        log.warn("Stock reservation failed for order: {}", orderId);
        orderRepository.findById(Long.parseLong(orderId)).ifPresent(order -> {
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        });
    }
}