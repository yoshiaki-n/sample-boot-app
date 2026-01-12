package com.example.samplebootapp.infrastructure.order.mapper;

import com.example.samplebootapp.application.order.query.CartItemDto;
import com.example.samplebootapp.domain.order.model.Cart;
import com.example.samplebootapp.domain.order.model.CartItem;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface CartMapper {
  void insertCart(Cart cart);

  void deleteCartItems(@Param("cartId") String cartId);

  void insertCartItem(@Param("cartId") String cartId, @Param("item") CartItem item);

  Optional<Cart> findByUserId(@Param("userId") String userId);

  List<CartItem> findItemsByCartId(@Param("cartId") String cartId);

  String findCartIdByUserId(@Param("userId") String userId);

  List<CartItemDto> findCartItemDtos(@Param("cartId") String cartId);
}
