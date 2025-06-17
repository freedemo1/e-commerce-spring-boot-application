package com.ecom.api.cartservice.controller;

import com.ecom.api.cartservice.model.Cart;
import com.ecom.api.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable String userId) {

        Optional<Cart> cart = cartService.getCartByUserId(userId);

        return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cart> saveCart(@RequestBody Cart cart) {
        Cart cartResp = null;
        try {
            cartResp = cartService.saveCart(cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(cartResp);
    }
}