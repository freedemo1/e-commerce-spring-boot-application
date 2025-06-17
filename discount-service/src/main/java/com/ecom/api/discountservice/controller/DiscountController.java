package com.ecom.api.discountservice.controller;

import com.ecom.api.discountservice.dto.DiscountRequest;
import com.ecom.api.discountservice.model.Discount;
import com.ecom.api.discountservice.service.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discount")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping
    public ResponseEntity<Void> createDiscount(@RequestBody DiscountRequest request) {
        discountService.createDiscount(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Discount> getDiscount(@PathVariable String code) {
        return ResponseEntity.ok(discountService.getDiscount(code));
    }
}