package com.example.samplebootapp.application.order.query;

import com.example.samplebootapp.infrastructure.datasource.order.OrderData;
import com.example.samplebootapp.infrastructure.datasource.order.OrderMapper;
import com.example.samplebootapp.presentation.order.api.response.OrderItemResponse;
import com.example.samplebootapp.presentation.order.api.response.OrderResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 注文参照サービス. */
@Service
@Transactional(readOnly = true)
public class OrderQueryService {

    private final OrderMapper orderMapper;

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
    public OrderQueryService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    /**
     * ユーザーの注文履歴を取得します.
     *
     * @param userId ユーザーID
     * @return 注文履歴リスト
     */
    public List<OrderResponse> getOrders(String userId) {
        List<OrderData> orders = orderMapper.selectByUserId(userId);
        return orders.stream().map(this::toResponse).toList();
    }

    private OrderResponse toResponse(OrderData order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(
                        item -> new OrderItemResponse(
                                item.getProductName(), item.getPrice(), item.getQuantity()))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getOrderedAt(),
                order.getTotalAmount(),
                order.getStatus(),
                itemResponses);
    }
}
