package com.example.samplebootapp.domain.inventory.model;

import com.example.samplebootapp.domain.product.model.ProductId;
import com.example.samplebootapp.domain.shared.AggregateRootBase;
import jakarta.validation.constraints.NotNull;

/**
 * 在庫集約ルート.
 */
public class Inventory extends AggregateRootBase<ProductId> {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer quantity;

    /**
     * コンストラクタ.
     *
     * @param productId 商品ID
     * @param quantity  在庫数
     */
    public Inventory(@NotNull ProductId productId, @NotNull Integer quantity) {
        super(productId);
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
