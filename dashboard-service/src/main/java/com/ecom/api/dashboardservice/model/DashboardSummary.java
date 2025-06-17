package com.ecom.api.dashboardservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Entity
@Table(name = "dashboard_summary")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummary {
    @Id
    private Long id;
    private int totalOrders;
    private int totalUsers;
    private BigDecimal totalRevenue;
    private int totalCartItems;
}