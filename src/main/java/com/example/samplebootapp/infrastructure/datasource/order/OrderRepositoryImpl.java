package com.example.samplebootapp.infrastructure.datasource.order;

import com.example.samplebootapp.domain.order.model.Order;
import com.example.samplebootapp.domain.order.model.OrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderMapper orderMapper;

    public OrderRepositoryImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public void save(Order order) {
        orderMapper.insertOrder(order);
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            orderMapper.insertOrderItems(order.getId(), order.getItems());
        }
    }
}
