package com.example.samplebootapp.presentation.product.api;

import com.example.samplebootapp.domain.product.model.CategoryId;
import com.example.samplebootapp.domain.product.model.Price;
import com.example.samplebootapp.domain.product.model.ProductSearchCriteria;
import java.io.Serializable;

/**
 * 商品検索リクエストDTO.
 */
public record ProductSearchRequest(
        String keyword,
        String categoryId,
        Long minPrice,
        Long maxPrice) implements Serializable {

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
