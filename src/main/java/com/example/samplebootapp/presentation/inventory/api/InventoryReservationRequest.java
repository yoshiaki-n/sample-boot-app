package com.example.samplebootapp.presentation.inventory.api;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/** 在庫引き当てリクエスト. */
@SuppressFBWarnings("EQ_DOESNT_OVERRIDE_EQUALS")
@Schema(description = "在庫引き当てリクエスト")
public class InventoryReservationRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Schema(description = "商品ID", example = "prod-001")
  private String productId;

  @NotNull
  @Min(1)
  @Schema(description = "引き当て数", example = "1")
  private Integer quantity;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
