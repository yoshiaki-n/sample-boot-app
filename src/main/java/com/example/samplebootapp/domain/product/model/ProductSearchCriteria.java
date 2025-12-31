package com.example.samplebootapp.domain.product.model;

import com.example.samplebootapp.domain.shared.ValueObjectBase;
import java.io.Serializable;

/**
 * 商品検索条件値オブジェクト.
 */
public class ProductSearchCriteria extends ValueObjectBase<ProductSearchCriteria>
        implements Serializable {

    private final String keyword;
    private final CategoryId categoryId;
    private final Price minPrice;
    private final Price maxPrice;

    /**
     * コンストラクタ.
     *
     * @param keyword    キーワード (null可)
     * @param categoryId カテゴリID (null可)
     * @param minPrice   最低価格 (null可)
     * @param maxPrice   最高価格 (null可)
     */
    public ProductSearchCriteria(
            String keyword, CategoryId categoryId, Price minPrice, Price maxPrice) {
        this.keyword = keyword;
        this.categoryId = categoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getKeyword() {
        return keyword;
    }

    public CategoryId getCategoryId() {
        return categoryId;
    }

    public Price getMinPrice() {
        return minPrice;
    }

    public Price getMaxPrice() {
        return maxPrice;
    }
}
