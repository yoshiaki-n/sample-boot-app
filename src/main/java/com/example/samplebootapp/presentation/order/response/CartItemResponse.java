package com.example.samplebootapp.presentation.order.response;

import com.example.samplebootapp.application.order.query.CartItemDto;
import com.example.samplebootapp.domain.order.model.CartItem;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/** カート商品レスポンス. */
public class CartItemResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "商品ID", example = "prod-001")
  private String productId;

  @Schema(description = "商品名", example = "Smartphone")
  private String productName;

  @Schema(description = "数量", example = "1")
  private int quantity;

  public CartItemResponse(String productId, String productName, int quantity) {
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
  }

  public static CartItemResponse from(CartItem cartItem) {
    // Domain Object doesn't have product name, so pass empty or fetch it?
    // Current usage in CartResponse uses this. But CartMapper will be updated to
    // fetch DTOs directly?
    // Or we need to update CartDomain? No, QueryService should handle names.
    // For now, let's keep it null or empty string if converting from Domain,
    // BUT the critical path uses CartDto.
    return new CartItemResponse(cartItem.getProductId(), "", cartItem.getQuantity());
  }

  public static CartItemResponse from(CartItemDto dto) {
    return new CartItemResponse(dto.getProductId(), dto.getProductName(), dto.getQuantity());
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
