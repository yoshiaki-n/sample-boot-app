package com.example.samplebootapp.presentation.order.api.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 注文明細レスポンス.
 *
 * @param name 商品名
 * @param price 単価
 * @param quantity 数量
 */
public record OrderItemResponse(
    @Schema(description = "商品名", example = "Tシャツ") String name,
    @Schema(description = "単価", example = "3000") int price,
    @Schema(description = "数量", example = "2") int quantity) {}
