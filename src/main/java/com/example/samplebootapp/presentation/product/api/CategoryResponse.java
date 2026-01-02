package com.example.samplebootapp.presentation.product.api;

import com.example.samplebootapp.domain.product.model.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * カテゴリレスポンス.
 */
public class CategoryResponse {

    @Schema(description = "カテゴリID", example = "categoryId")
    private final String id;

    @Schema(description = "カテゴリ名", example = "カテゴリ名")
    private final String name;

    @Schema(description = "子カテゴリ")
    private final List<CategoryResponse> children;

    /**
     * コンストラクタ.
     *
     * @param id       カテゴリID
     * @param name     カテゴリ名
     * @param children 子カテゴリ
     */
    public CategoryResponse(String id, String name, List<CategoryResponse> children) {
        this.id = id;
        this.name = name;
        // EI_EXPOSE_REP2対策: 防御的コピー
        this.children = new ArrayList<>(children);
    }

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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * 子カテゴリを取得します.
     *
     * @return 子カテゴリ（変更不可）
     */
    public List<CategoryResponse> getChildren() {
        // EI_EXPOSE_REP対策: 不変リストを返す
        return Collections.unmodifiableList(children);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoryResponse that = (CategoryResponse) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, children);
    }

    @Override
    public String toString() {
        return "CategoryResponse{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", children=" + children
                + '}';
    }
}
