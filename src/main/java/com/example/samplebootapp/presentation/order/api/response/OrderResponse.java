package com.example.samplebootapp.presentation.order.api.response;

import com.example.samplebootapp.domain.order.model.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 注文レスポンス.
 *
 * @param id          注文ID
 * @param orderedAt   注文日時
 * @param totalAmount 合計金額
 * @param status      注文ステータス
 * @param items       注文明細リスト
 */
public record OrderResponse(
                @Schema(description = "注文ID", example = "ord-12345") String id,
                @Schema(description = "注文日時", example = "2023-01-01T12:00:00") LocalDateTime orderedAt,
                @Schema(description = "合計金額", example = "6000") int totalAmount,
                @Schema(description = "注文ステータス", example = "ORDERED") OrderStatus status,
                @Schema(description = "注文明細リスト") List<OrderItemResponse> items) {
        public OrderResponse(
                        String id,
                        LocalDateTime orderedAt,
                        int totalAmount,
                        OrderStatus status,
                        List<OrderItemResponse> items) {
                this.id = id;
                this.orderedAt = orderedAt;
                this.totalAmount = totalAmount;
                this.status = status;
                this.items = items != null ? java.util.List.copyOf(items) : java.util.List.of();
        }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP")
    @Override
    public List<OrderItemResponse> items() {
        return items;
    }
}
