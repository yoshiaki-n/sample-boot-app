package com.example.samplebootapp.infrastructure.cart.mapper;

import com.example.samplebootapp.domain.cart.model.Cart;
import com.example.samplebootapp.domain.cart.model.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Optional;

@Mapper
public interface CartMapper {
    void insertCart(Cart cart);

    void deleteCartItems(@Param("cartId") String cartId);

    void insertCartItem(@Param("cartId") String cartId, @Param("item") CartItem item);

    Optional<Cart> findByUserId(@Param("userId") String userId);

    List<CartItem> findItemsByCartId(@Param("cartId") String cartId);
}
