package com.example.samplebootapp.presentation.inventory.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.samplebootapp.application.inventory.query.InventoryQueryService;
import com.example.samplebootapp.domain.inventory.model.Inventory;
import com.example.samplebootapp.domain.inventory.model.InventoryRepository;
import com.example.samplebootapp.domain.product.model.ProductId;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class InventoryControllerTest {

    private InventoryRepository inventoryRepository;
    private InventoryController inventoryController;

    @BeforeEach
    void setUp() {
        inventoryRepository = mock(InventoryRepository.class);
        InventoryQueryService inventoryQueryService = new InventoryQueryService(inventoryRepository);
        inventoryController = new InventoryController(inventoryQueryService);
    }

    @Test
    @DisplayName("在庫数確認APIが正常に動作し、在庫情報を返すこと")
    void getQuantity() {
        // 準備 (Arrange)
        ProductId productId = ProductId.generate();
        Inventory inventory = new Inventory(productId, 50);
        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.of(inventory));

        // 実行 (Act)
        ResponseEntity<InventoryResponse> responseEntity = inventoryController.getQuantity(productId.getValue());

        // 検証 (Assert)
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        InventoryResponse body = responseEntity.getBody();
        assertThat(body).isNotNull();
        assertThat(body.productId()).isEqualTo(productId.getValue());
        assertThat(body.quantity()).isEqualTo(50);
    }

    @Test
    @DisplayName("存在しない商品の在庫を確認しようとした場合、404を返すこと")
    void getQuantityNotFound() {
        // 準備 (Arrange)
        when(inventoryRepository.findByProductId(any(ProductId.class))).thenReturn(Optional.empty());

        // 実行 (Act)
        ResponseEntity<InventoryResponse> responseEntity = inventoryController.getQuantity("unknown-id");

        // 検証 (Assert)
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
