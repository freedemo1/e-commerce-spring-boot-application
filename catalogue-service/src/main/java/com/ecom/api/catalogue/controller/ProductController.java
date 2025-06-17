package com.ecom.api.catalogue.controller;

import com.ecom.api.catalogue.dto.ProductRequest;
import com.ecom.api.catalogue.model.Product;
import com.ecom.api.catalogue.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogue")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody ProductRequest request) {
        service.addProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
         return ResponseEntity.ok(service.getAllProducts());
    }
}