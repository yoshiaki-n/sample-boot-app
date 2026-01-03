package com.example.samplebootapp.domain.product.model;

import com.example.samplebootapp.domain.shared.EntityBase;
import jakarta.validation.constraints.NotNull;

/** カテゴリエンティティ. */
public class Category extends EntityBase<CategoryId> {
  private static final long serialVersionUID = 1L;

  @NotNull private String name;
  private CategoryId parentId;

  public Category(@NotNull CategoryId id, @NotNull String name, CategoryId parentId) {
    super(id);
    this.name = name;
    this.parentId = parentId;
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

  public CategoryId getParentId() {
    return parentId;
  }
}
