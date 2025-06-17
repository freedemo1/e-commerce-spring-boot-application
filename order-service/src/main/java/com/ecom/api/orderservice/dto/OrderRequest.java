package com.ecom.api.orderservice.dto;

import java.util.List;

public record OrderRequest(String userId, List<OrderItemRequest> items) {}