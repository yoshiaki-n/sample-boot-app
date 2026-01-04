package com.example.samplebootapp.infrastructure.order.repository;

import com.example.samplebootapp.domain.order.model.Cart;
import com.example.samplebootapp.domain.order.model.CartRepository;
import com.example.samplebootapp.infrastructure.order.mapper.CartMapper;

import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class CartRepositoryImpl implements CartRepository {

  private final CartMapper cartMapper;

  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
  public CartRepositoryImpl(CartMapper cartMapper) {
    this.cartMapper = cartMapper;
  }

  @Override
  public void save(Cart cart) {
    // カートが存在するか確認（本来はupsertが良いが、今回は簡易的に）
    // Cartにはversion等の楽観ロックがないため、単純に存在チェックしてなければinsert
    // itemsは全削除してinsertしなおす（簡易実装）

    Optional<Cart> existing = cartMapper.findByUserId(cart.getUserId());
    if (existing.isEmpty()) {
      cartMapper.insertCart(cart);
    }

    // items更新
    cartMapper.deleteCartItems(cart.getId().getValue());
    cart.getItems().forEach(item -> cartMapper.insertCartItem(cart.getId().getValue(), item));
  }

  @Override
  public Optional<Cart> findByUserId(String userId) {
    return cartMapper.findByUserId(userId);
  }
}
