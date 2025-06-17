package com.ecom.api.dashboardservice.kafka;

import com.ecom.api.dashboardservice.service.DashboardService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventConsumers {

    private final DashboardService dashboardService;

    public EventConsumers(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @KafkaListener(topics = "order-created", groupId = "dashboard-service")
    public void handleOrderCreated(ConsumerRecord<String, String> record) {
        dashboardService.incrementOrderCount();
    }

    @KafkaListener(topics = "stock-reserved", groupId = "dashboard-service")
    public void handleStockReserved(ConsumerRecord<String, String> record) {
        dashboardService.incrementRevenue();
    }

    @KafkaListener(topics = "stock-reservation-failed", groupId = "dashboard-service")
    public void handleStockFailed(ConsumerRecord<String, String> record) {
        // optional: maybe log or track failures
    }
}
