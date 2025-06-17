package com.ecom.api.dashboardservice.service;

import com.ecom.api.dashboardservice.model.DashboardSummary;
import com.ecom.api.dashboardservice.repository.DashboardSummaryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DashboardService {

    private final DashboardSummaryRepository repository;

    public DashboardService(DashboardSummaryRepository repository) {
        this.repository = repository;
    }

    public DashboardSummary getSummary() {
        return repository.findById(1L).orElseGet(() -> {
            DashboardSummary summary = new DashboardSummary();
            summary.setTotalRevenue(BigDecimal.ZERO);
            return repository.save(summary);
        });
    }

    public void incrementOrderCount() {
        DashboardSummary summary = getSummary();
        summary.setTotalOrders(summary.getTotalOrders() + 1);
        repository.save(summary);
    }

    public void incrementRevenue() {
        DashboardSummary summary = getSummary();
        summary.setTotalRevenue(summary.getTotalRevenue().add(new BigDecimal("100.00")));
        repository.save(summary);
    }
}