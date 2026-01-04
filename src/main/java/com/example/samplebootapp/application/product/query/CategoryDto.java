package com.example.samplebootapp.application.product.query;

import java.util.ArrayList;
import java.util.List;

/**
 * カテゴリDTO.
 * アプリケーション層から返却されるカテゴリ情報です。
 */
public class CategoryDto {
    private final String id;
    private final String name;
    private final List<CategoryDto> children = new ArrayList<>();

    public CategoryDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addChild(CategoryDto child) {
        this.children.add(child);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CategoryDto> getChildren() {
        return children;
    }
}
