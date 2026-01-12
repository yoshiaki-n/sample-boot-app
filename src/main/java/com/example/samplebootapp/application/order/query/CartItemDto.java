package com.example.samplebootapp.application.order.query;

/** カート商品DTO. */
public class CartItemDto {
  private final String productId;
  private final String productName;
  private final int quantity;

  public CartItemDto(String productId, String productName, int quantity) {
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
  }

  public String getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public int getQuantity() {
    return quantity;
  }
}
