package com.example.samplebootapp.presentation.inventory.api;

import com.example.samplebootapp.application.inventory.command.InventoryCommandService;
import com.example.samplebootapp.application.inventory.query.InventoryQueryService;
import com.example.samplebootapp.domain.product.model.ProductId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 在庫APIコントローラ. */
@RestController
@RequestMapping("/api/inventory")
@Tag(name = "在庫コンテキスト (Inventory Context)", description = "在庫に関連する操作を提供します。")
public class InventoryController {

  private final InventoryQueryService inventoryQueryService;
  private final InventoryCommandService inventoryCommandService;

  public InventoryController(
      InventoryQueryService inventoryQueryService,
      InventoryCommandService inventoryCommandService) {
    this.inventoryQueryService = inventoryQueryService;
    this.inventoryCommandService = inventoryCommandService;
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

  /**
   * 在庫を引き当てます.
   *
   * @param request 引き当てリクエスト
   * @return レスポンス
   */
  @PostMapping("/reservations")
  @Operation(summary = "在庫引き当て", description = "指定された商品の在庫を引き当て（確保）します。")
  @ApiResponse(responseCode = "200", description = "引き当て成功")
  @ApiResponse(
      responseCode = "400",
      description = "入力エラーまたは在庫不足",
      content = @Content(schema = @Schema(hidden = true)))
  @ApiResponse(
      responseCode = "409",
      description = "競合エラー",
      content = @Content(schema = @Schema(hidden = true)))
  @ApiResponse(
      responseCode = "404",
      description = "商品が見つかりません",
      content = @Content(schema = @Schema(hidden = true)))
  public ResponseEntity<Void> reserve(@RequestBody @Validated InventoryReservationRequest request) {
    inventoryCommandService.allocate(request.getProductId(), request.getQuantity());
    return ResponseEntity.ok().build();
  }
}
