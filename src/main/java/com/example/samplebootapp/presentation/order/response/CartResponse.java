package com.example.samplebootapp.presentation.order.response;

import com.example.samplebootapp.application.order.query.CartDto;
import com.example.samplebootapp.domain.order.model.Cart;
import java.util.List;
import java.util.stream.Collectors;

/** カートレスポンス. */
public class CartResponse {

  private String cartId;
  private List<CartItemResponse> items;

  public CartResponse(String cartId, List<CartItemResponse> items) {
    this.cartId = cartId;
    this.items = items != null ? new java.util.ArrayList<>(items) : new java.util.ArrayList<>();
  }

  public static CartResponse from(Cart cart) {
    List<CartItemResponse> itemResponses =
        cart.getItems().stream().map(CartItemResponse::from).collect(Collectors.toList());
    return new CartResponse(cart.getId().getValue(), itemResponses);
  }

  public static CartResponse from(CartDto dto) {
    List<CartItemResponse> itemResponses =
        dto.getItems().stream().map(CartItemResponse::from).collect(Collectors.toList());
    return new CartResponse(dto.getCartId(), itemResponses);
  }

  public String getCartId() {
    return cartId;
  }

  public List<CartItemResponse> getItems() {
    return java.util.Collections.unmodifiableList(items);
  }
}
