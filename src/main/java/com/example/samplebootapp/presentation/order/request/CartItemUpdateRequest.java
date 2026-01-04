package com.example.samplebootapp.presentation.order.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import java.io.Serializable;

@Schema(description = "カート商品数量変更リクエスト")
public class CartItemUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "数量", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 1, message = "数量は1以上でなくてはなりません")
    private int quantity;

    public CartItemUpdateRequest() {
    }

    public CartItemUpdateRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
