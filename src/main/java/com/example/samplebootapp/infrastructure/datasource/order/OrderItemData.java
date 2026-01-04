package com.example.samplebootapp.infrastructure.datasource.order;

import com.example.samplebootapp.domain.product.model.ProductId;

public class OrderItemData {
  private ProductId productId;
  private String productName;
  private int price;
  private int quantity;

  public OrderItemData() {}

  public ProductId getProductId() {
    return productId;
  }

  public void setProductId(ProductId productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
