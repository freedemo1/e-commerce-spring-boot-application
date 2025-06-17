package com.ecom.api.discountservice.service;

import com.ecom.api.discountservice.dto.DiscountRequest;
import com.ecom.api.discountservice.model.Discount;
import com.ecom.api.discountservice.repository.DiscountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DiscountService {

    private final DiscountRepository repository;

    public DiscountService(DiscountRepository repository) {
        this.repository = repository;
    }

    //we'll just add retry later resilience circuit breaker not required
    public void createDiscount(DiscountRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Discount discount = Discount.builder()
                .code(request.code())
                .description(request.description())
                .discountPercentage(request.discountPercentage())
                .validFrom(LocalDate.parse(request.validFrom(), formatter))
                .validTo(LocalDate.parse(request.validTo(), formatter))
                .active(request.active())
                .applicableProductIds(request.applicableProductIds())
                .build();
        repository.save(discount);
    }

    public Discount getDiscount(String code) {
        return repository.findByCode(code).orElseThrow(() -> new RuntimeException("Discount not found"));
    }
}