package com.example.samplebootapp.domain.product.model;

import java.util.List;
import java.util.Optional;

/**
 * 商品リポジトリインターフェース.
 *
 * <p>
 * 商品の検索と取得を行うためのリポジトリです。
 */
public interface ProductRepository {

    /**
     * 商品IDで商品を検索します.
     *
     * @param id 商品ID
     * @return 商品（存在しない場合はOptional.empty）
     */
    Optional<Product> findById(ProductId id);

    /**
     * 検索条件に基づいて商品を検索します.
     *
     * @param criteria 検索条件
     * @return 検索結果のリスト
     */
    List<Product> search(ProductSearchCriteria criteria);
}
