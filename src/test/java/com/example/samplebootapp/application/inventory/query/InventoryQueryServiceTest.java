package com.example.samplebootapp.application.inventory.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.samplebootapp.domain.inventory.model.Inventory;
import com.example.samplebootapp.domain.inventory.model.InventoryRepository;
import com.example.samplebootapp.domain.product.model.ProductId;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InventoryQueryServiceTest {

  @Mock private InventoryRepository inventoryRepository;

  @InjectMocks private InventoryQueryService inventoryQueryService;

  @Test
  @DisplayName("商品IDで在庫を検索して取得できること")
  void findByProductId() {
    // 準備 (Arrange)
    ProductId productId = ProductId.generate();
    Inventory inventory = new Inventory(productId, 100, 0L);

    when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.of(inventory));

    // 実行 (Act)
    Optional<Inventory> result = inventoryQueryService.findByProductId(productId);

    // 検証 (Assert)
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(inventory);
    verify(inventoryRepository).findByProductId(productId);
  }

  @Test
  @DisplayName("存在しない商品IDで在庫がない場合、空が返されること")
  void findByProductIdNotFound() {
    // 準備 (Arrange)
    ProductId productId = ProductId.generate();
    when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.empty());

    // 実行 (Act)
    Optional<Inventory> result = inventoryQueryService.findByProductId(productId);

    // 検証 (Assert)
    assertThat(result).isEmpty();
    verify(inventoryRepository).findByProductId(productId);
  }
}
