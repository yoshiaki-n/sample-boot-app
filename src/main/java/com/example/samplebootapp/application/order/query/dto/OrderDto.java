package com.example.samplebootapp.application.order.query.dto;

import com.example.samplebootapp.domain.order.model.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 注文DTO.
 *
 * @param id 注文ID
 * @param orderedAt 注文日時
 * @param totalAmount 合計金額
 * @param status 注文ステータス
 * @param items 注文明細リスト
 */
public record OrderDto(
    String id,
    LocalDateTime orderedAt,
    int totalAmount,
    OrderStatus status,
    List<OrderItemDto> items) {
  public OrderDto(
      String id,
      LocalDateTime orderedAt,
      int totalAmount,
      OrderStatus status,
      List<OrderItemDto> items) {
    this.id = id;
    this.orderedAt = orderedAt;
    this.totalAmount = totalAmount;
    this.status = status;
    this.items = items != null ? java.util.List.copyOf(items) : java.util.List.of();
  }

  @Override
  public List<OrderItemDto> items() {
    return items;
  }
}
