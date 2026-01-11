package com.example.samplebootapp.application.order.query.dto;

/**
 * 注文明細DTO.
 *
 * @param name     商品名
 * @param price    単価
 * @param quantity 数量
 */
public record OrderItemDto(String name, int price, int quantity) {
}
