package com.ecom.api.cartservice.util;

import com.ecom.api.cartservice.model.Cart;
import com.ecom.api.cartservice.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class CartUtil {

    /**
     * It deduplicates items by productId, summing the quantities if necessary:
     *
     * @param cart
     * @return
     */
    public Cart createOrUpdateCart(Cart cart) {

        // Remove duplicate productIds by merging quantities
        Map<String, CartItem> deduplicated = new HashMap<>();
        for (CartItem item : cart.getCartItems()) {
            deduplicated.merge(item.getProductId(), item,
                    (existing, incoming) -> {
                        existing.setQuantity(existing.getQuantity() + incoming.getQuantity());
                        return existing;
                    });
        }
        cart.setCartItems(new ArrayList<>(deduplicated.values()));

        return cart;
    }

}
