package com.example.samplebootapp.presentation.order.response;

import com.example.samplebootapp.application.order.query.CartItemDto;
import com.example.samplebootapp.domain.order.model.CartItem;

/** カート商品レスポンス. */
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

  public static CartItemResponse from(CartItemDto dto) {
    return new CartItemResponse(dto.getProductId(), dto.getQuantity());
  }

  public String getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }
}
