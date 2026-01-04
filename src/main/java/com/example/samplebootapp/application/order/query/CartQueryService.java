package com.example.samplebootapp.application.order.query;

import com.example.samplebootapp.domain.order.model.Cart;
import com.example.samplebootapp.domain.order.model.CartRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** カート参照サービス. */
@Service
@Transactional(readOnly = true)
public class CartQueryService {

  private final CartRepository cartRepository;

  public CartQueryService(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  /**
   * カートを取得します.
   *
   * @param userId ユーザーID
   * @return カートDTO
   */
  public CartDto getCart(String userId) {
    Optional<Cart> cartOpt = cartRepository.findByUserId(userId);

    if (cartOpt.isPresent()) {
      Cart cart = cartOpt.get();
      List<CartItemDto> items =
          cart.getItems().stream()
              .map(item -> new CartItemDto(item.getProductId(), item.getQuantity()))
              .toList();
      return new CartDto(cart.getId().getValue(), items);
    } else {
      // カートが存在しない場合は空のレスポンスを返す（要件に合わせて調整）
      // IDは仮で空文字、またはnullとするか、空のカートオブジェクトを作成して変換するなど
      // ここでは空のカートとして扱う
      return new CartDto("", Collections.emptyList());
    }
  }
}
