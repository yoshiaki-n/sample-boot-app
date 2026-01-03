package com.example.samplebootapp.presentation.inventory.api;

import com.example.samplebootapp.domain.inventory.model.Inventory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 * 在庫レスポンスDTO.
 */
public record InventoryResponse(
        @Schema(description = "商品ID", example = "P001") String productId,
        @Schema(description = "在庫数", example = "100") Integer quantity)
        implements Serializable {

    /**
     * ドメインの在庫オブジェクトからレスポンスDTOを生成します.
     *
     * @param inventory 在庫
     * @return InventoryResponse
     */
    public static InventoryResponse from(Inventory inventory) {
        return new InventoryResponse(inventory.getId().getValue(), inventory.getQuantity());
    }
}
