package com.example.samplebootapp.domain.inventory.model;

import com.example.samplebootapp.domain.product.model.ProductId;
import java.util.Optional;

/**
 * 在庫リポジトリインターフェース.
 *
 * <p>
 * 在庫の検索と取得を行うためのリポジトリです。
 */
public interface InventoryRepository {

    /**
     * 商品IDで在庫を検索します.
     *
     * @param productId 商品ID
     * @return 在庫（存在しない場合はOptional.empty）
     */
    Optional<Inventory> findByProductId(ProductId productId);
}
