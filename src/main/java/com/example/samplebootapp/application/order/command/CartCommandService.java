package com.example.samplebootapp.application.order.command;

import com.example.samplebootapp.domain.order.model.Cart;
import com.example.samplebootapp.domain.order.model.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartCommandService {

  private final CartRepository cartRepository;

  public CartCommandService(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  /**
   * カートに商品を追加します。
   *
   * @param userId ユーザーID
   * @param productId 商品ID
   * @param quantity 数量
   */
  public void addItem(String userId, String productId, int quantity) {
    Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> Cart.create(userId));

    cart.addItem(productId, quantity);

    cartRepository.save(cart);
  }

  /**
   * カート内の商品の数量を変更します。
   *
   * @param userId ユーザーID
   * @param productId 商品ID
   * @param quantity 数量
   */
  public void updateItemQuantity(String userId, String productId, int quantity) {
    Cart cart =
        cartRepository
            .findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));

    cart.changeQuantity(productId, quantity);

    cartRepository.save(cart);
  }
}
