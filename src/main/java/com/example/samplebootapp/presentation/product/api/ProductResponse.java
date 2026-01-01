package com.example.samplebootapp.presentation.product.api;

import com.example.samplebootapp.domain.product.model.Product;
import java.io.Serializable;
import java.math.BigDecimal;

/** 商品レスポンスDTO. */
public record ProductResponse(
    String id, String name, String description, BigDecimal price, String categoryId)
    implements Serializable {

  /**
   * ドメインの商品オブジェクトからレスポンスDTOを生成します.
   *
   * @param product Product
   * @return ProductResponse
   */
  public static ProductResponse from(Product product) {
    return new ProductResponse(
        product.getId().getValue(),
        product.getName(),
        product.getDescription(),
        product.getPrice().getAmount(),
        product.getCategoryId().getValue());
  }
}
