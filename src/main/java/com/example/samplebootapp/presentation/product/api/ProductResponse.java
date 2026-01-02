package com.example.samplebootapp.presentation.product.api;

import com.example.samplebootapp.domain.product.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;

/** 商品レスポンスDTO. */
public record ProductResponse(
    @Schema(description = "商品ID", example = "P001") String id,
    @Schema(description = "商品名", example = "ボールペン") String name,
    @Schema(description = "商品説明", example = "書きやすいボールペンです。") String description,
    @Schema(description = "価格", example = "150") BigDecimal price,
    @Schema(description = "カテゴリID", example = "C001") String categoryId)
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
