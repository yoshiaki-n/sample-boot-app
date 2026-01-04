package com.example.samplebootapp.infrastructure.datasource.order;

import com.example.samplebootapp.domain.order.model.Order;
import com.example.samplebootapp.domain.order.model.OrderItem;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    void insertOrder(Order order);

    void insertOrderItems(@Param("orderId") String orderId, @Param("items") List<OrderItem> items);
}
