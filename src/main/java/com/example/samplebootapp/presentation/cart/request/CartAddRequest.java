package com.example.samplebootapp.presentation.cart.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "カート追加リクエスト")
public class CartAddRequest {

    @Schema(description = "商品ID", example = "prod_001")
    @NotEmpty
    private String productId;

    @Schema(description = "数量", example = "1")
    @Min(1)
    private int quantity;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
