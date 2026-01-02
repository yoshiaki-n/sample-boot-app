package com.example.samplebootapp.presentation.product.api;

import com.example.samplebootapp.domain.product.model.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * カテゴリレスポンス.
 *
 * @param id       カテゴリID
 * @param name     カテゴリ名
 * @param children 子カテゴリ
 */
public record CategoryResponse(
        @Schema(description = "カテゴリID", example = "categoryId") String id,
        @Schema(description = "カテゴリ名", example = "カテゴリ名") String name,
        @Schema(description = "子カテゴリ") List<CategoryResponse> children) {

    /**
     * ドメインモデルからレスポンスを生成します.
     *
     * @param category カテゴリ
     * @return レスポンス
     */
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getId().getValue(), category.getName(), new ArrayList<>());
    }

    /**
     * 子カテゴリを追加します.
     *
     * @param child 子カテゴリ
     */
    public void addChild(CategoryResponse child) {
        this.children.add(child);
    }
}
