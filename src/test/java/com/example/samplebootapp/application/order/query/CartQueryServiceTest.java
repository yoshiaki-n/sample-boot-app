package com.example.samplebootapp.application.order.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.samplebootapp.domain.order.model.Cart;
import com.example.samplebootapp.domain.order.model.CartRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartQueryServiceTest {

  @Mock private CartRepository cartRepository;

  @InjectMocks private CartQueryService cartQueryService;

  @Test
  @DisplayName("カート取得: カートが存在する場合、正しくマッピングされたレスポンスが返る")
  void getCart_exists() {
    // 準備
    String userId = "user-1";
    Cart cart = Cart.create(userId);
    cart.addItem("prod-1", 2);

    when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

    // 実行
    CartDto response = cartQueryService.getCart(userId);

    // 検証
    assertThat(response.getCartId()).isEqualTo(cart.getId().getValue());
    assertThat(response.getItems()).hasSize(1);
    assertThat(response.getItems().get(0).getProductId()).isEqualTo("prod-1");
    assertThat(response.getItems().get(0).getQuantity()).isEqualTo(2);
  }

  @Test
  @DisplayName("カート取得: カートが存在しない場合、空のレスポンスが返る")
  void getCart_notFound() {
    // 準備
    String userId = "user-2";
    when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

    // 実行
    CartDto response = cartQueryService.getCart(userId);

    // 検証
    assertThat(response.getCartId()).isEmpty(); // または null 確認など実装に合わせて
    assertThat(response.getItems()).isEmpty();
  }
}
