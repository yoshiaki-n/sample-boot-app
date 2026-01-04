package com.example.samplebootapp.application.order.command;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
class CartCommandServiceTest {

  @Mock
  private CartRepository cartRepository;

  @InjectMocks
  private CartCommandService cartCommandService;

  @Test
  @DisplayName("カート追加: 新規カート作成")
  void addItem_createNewCart() {
    // 準備 (Arrange)
    String userId = "user1";
    String productId = "prod1";
    int quantity = 2;

    when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

    // 実行 (Act)
    cartCommandService.addItem(userId, productId, quantity);

    // 検証 (Assert)
    verify(cartRepository).save(any(Cart.class));
  }
}
