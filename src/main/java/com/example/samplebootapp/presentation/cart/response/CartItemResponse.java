package com.example.samplebootapp.presentation.cart.response;

import com.example.samplebootapp.domain.cart.model.CartItem;

/**
 * カート商品レスポンス.
 */
public class CartItemResponse {

    private String productId;
    private int quantity;

    public CartItemResponse(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public static CartItemResponse from(CartItem cartItem) {
        return new CartItemResponse(cartItem.getProductId(), cartItem.getQuantity());
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
