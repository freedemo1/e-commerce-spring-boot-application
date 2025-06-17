package com.ecom.api.cartservice.service;

import com.ecom.api.cartservice.model.Cart;
import com.ecom.api.cartservice.repository.CartRepository;
import com.ecom.api.cartservice.util.CartUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartUtil cartUtil;

    public Optional<Cart> getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cartUtil.createOrUpdateCart(cart));
    }
}