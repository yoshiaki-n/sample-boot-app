package com.example.samplebootapp.presentation.cart.response;

import com.example.samplebootapp.domain.cart.model.Cart;

import java.util.List;
import java.util.stream.Collectors;

/**
 * カートレスポンス.
 */
public class CartResponse {

    private String cartId;
    private List<CartItemResponse> items;

    public CartResponse(String cartId, List<CartItemResponse> items) {
        this.cartId = cartId;
        this.items = items;
    }

    public static CartResponse from(Cart cart) {
        List<CartItemResponse> itemResponses = cart.getItems().stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());
        return new CartResponse(cart.getId().getValue(), itemResponses);
    }

    public String getCartId() {
        return cartId;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }
}
