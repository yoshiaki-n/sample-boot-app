package com.example.samplebootapp.presentation.product.api;

import com.example.samplebootapp.domain.product.model.CategoryId;
import com.example.samplebootapp.domain.product.model.Price;
import com.example.samplebootapp.domain.product.model.ProductSearchCriteria;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/** 商品検索リクエストDTO. */
public record ProductSearchRequest(
    @Schema(description = "検索キーワード", example = "ペン") String keyword,
    @Schema(description = "カテゴリID", example = "C001") String categoryId,
    @Schema(description = "最低価格", example = "100") Long minPrice,
    @Schema(description = "最高価格", example = "1000") Long maxPrice)
    implements Serializable {

  /**
   * ドメインの検索条件オブジェクトに変換します.
   *
   * @return ProductSearchCriteria
   */
  public ProductSearchCriteria toCriteria() {
    return new ProductSearchCriteria(
        keyword,
        categoryId != null ? new CategoryId(categoryId) : null,
        minPrice != null ? Price.of(minPrice) : null,
        maxPrice != null ? Price.of(maxPrice) : null);
  }
}
