package com.ecom.api.dashboardservice;

import com.ecom.api.dashboardservice.model.DashboardSummary;
import com.ecom.api.dashboardservice.repository.DashboardSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final DashboardSummaryRepository repository;

    @KafkaListener(topics = "order-events", groupId = "dashboard-group")
    public void handleOrderEvent(String message) {
        DashboardSummary summary = repository.findById("main").orElse(
            DashboardSummary.builder()
                .id(1L)
                .totalOrders(0)
                .totalUsers(0)
                .totalRevenue(BigDecimal.ZERO)
                .totalCartItems(0)
                .build()
        );
        summary.setTotalOrders(summary.getTotalOrders() + 1);
        summary.setTotalRevenue(summary.getTotalRevenue().add(new BigDecimal("100"))); // Dummy value
        repository.save(summary);
    }
}