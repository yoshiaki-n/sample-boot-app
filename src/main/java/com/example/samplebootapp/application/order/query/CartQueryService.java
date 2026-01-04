package com.example.samplebootapp.application.order.query;

import com.example.samplebootapp.domain.order.model.Cart;
import com.example.samplebootapp.domain.order.model.CartRepository;
import com.example.samplebootapp.presentation.order.response.CartResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * カート参照サービス.
 */
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
   * @return カートレスポンス
   */
  public CartResponse getCart(String userId) {
    Optional<Cart> cartOpt = cartRepository.findByUserId(userId);

    if (cartOpt.isPresent()) {
      return CartResponse.from(cartOpt.get());
    } else {
      // カートが存在しない場合は空のレスポンスを返す（要件に合わせて調整）
      // IDは仮で空文字、またはnullとするか、空のカートオブジェクトを作成して変換するなど
      // ここでは空のカートとして扱う
      return new CartResponse("", Collections.emptyList());
    }
  }
}
