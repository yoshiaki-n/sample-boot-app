package com.example.samplebootapp.infrastructure.datasource.order;

import com.example.samplebootapp.domain.order.model.OrderStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderData {
  private String id;
  private String userId;
  private OrderStatus status;
  private int totalAmount;
  private LocalDateTime orderedAt;
  private List<OrderItemData> items = new ArrayList<>();

  public OrderData() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public int getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(int totalAmount) {
    this.totalAmount = totalAmount;
  }

  public LocalDateTime getOrderedAt() {
    return orderedAt;
  }

  public void setOrderedAt(LocalDateTime orderedAt) {
    this.orderedAt = orderedAt;
  }

  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP")
  public List<OrderItemData> getItems() {
    return items;
  }

  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
  public void setItems(List<OrderItemData> items) {
    this.items = items;
  }
}
