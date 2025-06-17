package com.ecom.api.orderservice.command;

import com.ecom.api.orderservice.model.Order;
import com.ecom.api.orderservice.model.OrderItem;
import com.ecom.api.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCommandService {

    private final OrderRepository orderRepository;

    public Order createOrder(String userId, java.util.List<OrderItem> items) {
        Order order = new Order();
        order.setUserId(userId);
        order.setItems(items);
        return orderRepository.save(order);
    }
}