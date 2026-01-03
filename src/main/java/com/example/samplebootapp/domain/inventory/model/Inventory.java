package com.example.samplebootapp.domain.inventory.model;

import com.example.samplebootapp.domain.product.model.ProductId;
import com.example.samplebootapp.domain.shared.AggregateRootBase;
import jakarta.validation.constraints.NotNull;

/** 在庫集約ルート. */
public class Inventory extends AggregateRootBase<ProductId> {

  private static final long serialVersionUID = 1L;

  @NotNull private Integer quantity;

  @NotNull private Long version;

  /**
   * コンストラクタ.
   *
   * @param productId 商品ID
   * @param quantity 在庫数
   * @param version バージョン
   */
  public Inventory(@NotNull ProductId productId, @NotNull Integer quantity, @NotNull Long version) {
    super(productId);
    this.quantity = quantity;
    this.version = version;
  }

  /**
   * 在庫数を減らします.
   *
   * @param amount 減らす量
   * @throws InsufficientStockException 在庫が不足している場合
   */
  public void decreaseQuantity(int amount) {
    if (this.quantity < amount) {
      throw new InsufficientStockException(
          "在庫が不足しています。現在の在庫数: " + this.quantity + ", 要求数: " + amount);
    }
    this.quantity -= amount;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public Long getVersion() {
    return version;
  }
}
