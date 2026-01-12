package com.example.samplebootapp.application.order.query;

import com.example.samplebootapp.infrastructure.order.mapper.CartMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** カート参照サービス. */
@Service
@Transactional(readOnly = true)
public class CartQueryService {

  private final CartMapper cartMapper;

  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
  public CartQueryService(CartMapper cartMapper) {
    this.cartMapper = cartMapper;
  }

  /**
   * カートを取得します.
   *
   * @param userId ユーザーID
   * @return カートDTO
   */
  public CartDto getCart(String userId) {
    String cartId = cartMapper.findCartIdByUserId(userId);

    if (cartId != null) {
      List<CartItemDto> items = cartMapper.findCartItemDtos(cartId);
      return new CartDto(cartId, items);
    } else {
      // カートが存在しない場合は空のレスポンスを返す
      return new CartDto("", Collections.emptyList());
    }
  }
}
