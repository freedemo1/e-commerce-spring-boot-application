package com.ecom.api.orderservice.command;

import com.ecom.api.orderservice.dto.OrderRequest;
import com.ecom.api.orderservice.model.Order;
import com.ecom.api.orderservice.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderCommandController {

    private final OrderCommandService commandService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest request) {
        List<OrderItem> items = request.items().stream()
                .map(i -> new OrderItem(i.productId(), i.quantity()))
                .toList();
        return ResponseEntity.ok(commandService.createOrder(request.userId(), items));
    }
}