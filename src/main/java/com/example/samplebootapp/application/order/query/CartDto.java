package com.example.samplebootapp.application.order.query;

import java.util.List;

/** カートDTO. アプリケーション層から返却されるカート情報です。 */
public class CartDto {
  private final String cartId;
  private final List<CartItemDto> items;

  public CartDto(String cartId, List<CartItemDto> items) {
    this.cartId = cartId;
    this.items = items != null ? new java.util.ArrayList<>(items) : new java.util.ArrayList<>();
  }

  public String getCartId() {
    return cartId;
  }

  public List<CartItemDto> getItems() {
    return new java.util.ArrayList<>(items);
  }
}
