package com.example.samplebootapp.domain.cart.model;

import com.example.samplebootapp.domain.shared.ValueObjectBase;
import java.io.Serializable;
import java.util.Objects;
import org.springframework.util.Assert;

/**
 * カート内商品.
 */
public class CartItem extends ValueObjectBase<CartItem> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String productId;
    private final int quantity;

    /**
     * コンストラクタ.
     *
     * @param productId 商品ID
     * @param quantity  数量
     */
    public CartItem(String productId, int quantity) {
        Assert.hasText(productId, "productId must not be empty");
        Assert.isTrue(quantity > 0, "quantity must be greater than 0");
        this.productId = productId;
        this.quantity = quantity;
    }

    /**
     * 数量を加算した新しいCartItemを返します.
     *
     * @param addQuantity 加算する数量
     * @return 新しいCartItem
     */
    public CartItem add(int addQuantity) {
        Assert.isTrue(addQuantity > 0, "addQuantity must be greater than 0");
        return new CartItem(this.productId, this.quantity + addQuantity);
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        return Objects.equals(productId, cartItem.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
