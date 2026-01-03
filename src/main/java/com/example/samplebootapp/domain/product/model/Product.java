package com.example.samplebootapp.domain.product.model;

import com.example.samplebootapp.domain.shared.AggregateRootBase;
import jakarta.validation.constraints.NotNull;

/** 商品集約ルート. */
public class Product extends AggregateRootBase<ProductId> {

  private static final long serialVersionUID = 1L;

  @NotNull private String name;

  @NotNull private String description;

  @NotNull private Price price;

  @NotNull private CategoryId categoryId;

  /**
   * コンストラクタ.
   *
   * @param id 商品ID
   * @param name 商品名
   * @param description 商品説明
   * @param price 価格
   * @param categoryId カテゴリID
   */
  public Product(
      @NotNull ProductId id,
      @NotNull String name,
      @NotNull String description,
      @NotNull Price price,
      @NotNull CategoryId categoryId) {
    super(id);
    this.name = name;
    this.description = description;
    this.price = price;
    this.categoryId = categoryId;
  }

  /**
   * 価格を変更します.
   *
   * @param newPrice 新しい価格
   */
  public void changePrice(@NotNull Price newPrice) {
    this.price = newPrice;
  }

  /**
   * 商品詳細（名称と説明）を更新します.
   *
   * @param name 新しい商品名
   * @param description 新しい商品説明
   */
  public void updateDetail(@NotNull String name, @NotNull String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Price getPrice() {
    return price;
  }

  public CategoryId getCategoryId() {
    return categoryId;
  }
}
