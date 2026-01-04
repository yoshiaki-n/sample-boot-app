package com.example.samplebootapp.application.order.query;

/** カート商品DTO. */
public class CartItemDto {
  private final String productId;
  private final int quantity;

  public CartItemDto(String productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public String getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }
}
