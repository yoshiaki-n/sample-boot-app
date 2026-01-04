package com.example.samplebootapp.domain.order.model;

import com.example.samplebootapp.domain.product.model.ProductId;

/**
 * 注文明細.
 */
public class OrderItem {
    private final ProductId productId;
    private final String productName;
    private final int price;
    private final int quantity;

    public OrderItem(ProductId productId, String productName, int price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * 小計を計算します.
     *
     * @return 小計
     */
    public int calculateSubtotal() {
        return price * quantity;
    }
}
