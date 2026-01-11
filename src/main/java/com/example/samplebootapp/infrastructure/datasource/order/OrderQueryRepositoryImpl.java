package com.example.samplebootapp.infrastructure.datasource.order;

import com.example.samplebootapp.application.order.query.OrderQueryRepository;
import com.example.samplebootapp.application.order.query.dto.OrderDto;
import com.example.samplebootapp.application.order.query.dto.OrderItemDto;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/** 注文参照リポジトリの実装. */
@Repository
public class OrderQueryRepositoryImpl implements OrderQueryRepository {

  private final OrderMapper orderMapper;

  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
  public OrderQueryRepositoryImpl(OrderMapper orderMapper) {
    this.orderMapper = orderMapper;
  }

  @Override
  public List<OrderDto> findByUserId(String userId) {
    List<OrderData> orders = orderMapper.selectByUserId(userId);
    return orders.stream().map(this::toDto).toList();
  }

  @Override
  public Optional<OrderDto> findByIdAndUserId(String orderId, String userId) {
    OrderData order = orderMapper.selectByIdAndUserId(orderId, userId);
    return Optional.ofNullable(order).map(this::toDto);
  }

  private OrderDto toDto(OrderData order) {
    List<OrderItemDto> items =
        order.getItems().stream()
            .map(
                item ->
                    new OrderItemDto(item.getProductName(), item.getPrice(), item.getQuantity()))
            .toList();

    return new OrderDto(
        order.getId(), order.getOrderedAt(), order.getTotalAmount(), order.getStatus(), items);
  }
}
