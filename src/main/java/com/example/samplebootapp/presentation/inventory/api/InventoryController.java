package com.example.samplebootapp.presentation.inventory.api;

import com.example.samplebootapp.application.inventory.query.InventoryQueryService;
import com.example.samplebootapp.domain.product.model.ProductId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在庫APIコントローラ.
 */
@RestController
@RequestMapping("/api/inventory")
@Tag(name = "在庫", description = "在庫に関連する操作を提供します。")
public class InventoryController {

    private final InventoryQueryService inventoryQueryService;

    public InventoryController(InventoryQueryService inventoryQueryService) {
        this.inventoryQueryService = inventoryQueryService;
    }

    /**
     * 商品の在庫数を取得します.
     *
     * @param productId 商品ID
     * @return 在庫情報
     */
    @GetMapping("/products/{productId}")
    @Operation(summary = "在庫数確認", description = "指定された商品の現在の在庫数を返します。")
    public ResponseEntity<InventoryResponse> getQuantity(@PathVariable String productId) {
        return inventoryQueryService
                .findByProductId(new ProductId(productId))
                .map(InventoryResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
