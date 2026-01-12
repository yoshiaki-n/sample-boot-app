package com.example.samplebootapp.application.order.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.samplebootapp.infrastructure.order.mapper.CartMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartQueryServiceTest {

  @Mock private CartMapper cartMapper;

  @InjectMocks private CartQueryService cartQueryService;

  @Test
  @DisplayName("カート取得: カートが存在する場合、正しくマッピングされたレスポンスが返る")
  void getCart_exists() {
    // 準備
    String userId = "user-1";
    String cartId = "cart-1";
    List<CartItemDto> items = List.of(new CartItemDto("prod-1", "Product A", 2));

    when(cartMapper.findCartIdByUserId(userId)).thenReturn(cartId);
    when(cartMapper.findCartItemDtos(cartId)).thenReturn(items);

    // 実行
    CartDto response = cartQueryService.getCart(userId);

    // 検証
    assertThat(response.getCartId()).isEqualTo(cartId);
    assertThat(response.getItems()).hasSize(1);
    assertThat(response.getItems().get(0).getProductId()).isEqualTo("prod-1");
    assertThat(response.getItems().get(0).getProductName()).isEqualTo("Product A");
    assertThat(response.getItems().get(0).getQuantity()).isEqualTo(2);
  }

  @Test
  @DisplayName("カート取得: カートが存在しない場合、空のレスポンスが返る")
  void getCart_notFound() {
    // 準備
    String userId = "user-2";
    when(cartMapper.findCartIdByUserId(userId)).thenReturn(null);

    // 実行
    CartDto response = cartQueryService.getCart(userId);

    // 検証
    assertThat(response.getCartId()).isEmpty();
    assertThat(response.getItems()).isEmpty();
  }
}
