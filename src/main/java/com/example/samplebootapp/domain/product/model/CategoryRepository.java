package com.example.samplebootapp.domain.product.model;

import java.util.List;

/** カテゴリリポジトリ. */
public interface CategoryRepository {
    /**
     * 全カテゴリを取得します.
     *
     * @return カテゴリリスト
     */
    List<Category> findAll();
}
