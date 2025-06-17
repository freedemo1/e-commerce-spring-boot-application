package com.ecom.api.discountservice.dto;

import java.math.BigDecimal;
import java.util.List;

public record DiscountRequest(
        String code,
        String description,
        BigDecimal discountPercentage,
        String validFrom,
        String validTo,
        boolean active,
        List<String> applicableProductIds
) {
}