package com.example.samplebootapp.domain.product.model;

import com.example.samplebootapp.domain.shared.EntityBase;
import jakarta.validation.constraints.NotNull;

/**
 * カテゴリエンティティ.
 */
public class Category extends EntityBase<CategoryId> {

    @NotNull
    private String name;

    public Category(@NotNull CategoryId id, @NotNull String name) {
        super(id);
        this.name = name;
    }

    /**
     * カテゴリ名を変更します.
     *
     * @param newName 新しいカテゴリ名
     */
    public void rename(@NotNull String newName) {
        this.name = newName;
    }

    public String getName() {
        return name;
    }
}
