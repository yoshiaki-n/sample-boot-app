package com.example.samplebootapp.domain.order.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 注文集約ルート.
 */
public class Order {
    private final String id;
    private final String userId;
    private final OrderStatus status;
    private final List<OrderItem> items;
    private final int totalAmount;
    private final LocalDateTime orderedAt;

    private Order(
            String id,
            String userId,
            OrderStatus status,
            List<OrderItem> items,
            int totalAmount,
            LocalDateTime orderedAt) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.items = items;
        this.totalAmount = totalAmount;
        this.orderedAt = orderedAt;
    }

    /**
     * 注文を生成します.
     *
     * @param userId ユーザーID
     * @param items  注文明細リスト
     * @return 注文
     */
    public static Order create(String userId, List<OrderItem> items) {
        String id = UUID.randomUUID().toString();
        int totalAmount = items.stream().mapToInt(OrderItem::calculateSubtotal).sum();
        return new Order(id, userId, OrderStatus.ORDERED, items, totalAmount, LocalDateTime.now());
    }

    // 再構築用コンストラクタ
    public static Order reconstruct(
            String id,
            String userId,
            OrderStatus status,
            List<OrderItem> items,
            int totalAmount,
            LocalDateTime orderedAt) {
        return new Order(id, userId, status, items, totalAmount, orderedAt);
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }
}
