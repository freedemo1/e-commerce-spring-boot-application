package com.ecom.api.orderservice.service;

import com.ecom.api.orderservice.dto.OrderRequest;
import com.ecom.api.orderservice.model.Order;
import com.ecom.api.orderservice.model.OrderItem;
import com.ecom.api.orderservice.repository.OrderRepository;
import com.ecom.api.orderservice.resilience.ResilientDiscountClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository repository;
    private final ResilientDiscountClient discountClient;

    public OrderService(OrderRepository repository, ResilientDiscountClient discountClient) {
        this.repository = repository;
        this.discountClient = discountClient;
    }

    public Order placeOrder(OrderRequest request) {
        Order order = new Order();
        order.setUserId(request.userId());
        order.setItems(
            request.items().stream()
                .map(item -> new OrderItem(item.productId(), item.quantity()))
                .collect(Collectors.toList())
        );

        // 💬 Apply Resilient Discount logic. this one is optional see if it works or removed it for later
        String discount = discountClient.getDiscount(request.userId());
        log.info("Applied discount info for user {}: {}", request.userId(), discount);

        return repository.save(order);
    }

    public Order getOrder(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}