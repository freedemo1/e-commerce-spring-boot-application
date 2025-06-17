package com.ecom.api.dashboardservice.repository;

import com.ecom.api.dashboardservice.model.DashboardSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardSummaryRepository extends JpaRepository<DashboardSummary, Long> {
}