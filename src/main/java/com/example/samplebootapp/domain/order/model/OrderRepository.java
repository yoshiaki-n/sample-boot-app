package com.example.samplebootapp.domain.order.model;

/**
 * 注文リポジトリ.
 */
public interface OrderRepository {
    /**
     * 注文を保存します.
     *
     * @param order 注文
     */
    void save(Order order);
}
