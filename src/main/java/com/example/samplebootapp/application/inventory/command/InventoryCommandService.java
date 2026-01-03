package com.example.samplebootapp.application.inventory.command;

import com.example.samplebootapp.domain.inventory.model.Inventory;
import com.example.samplebootapp.domain.inventory.model.InventoryRepository;
import com.example.samplebootapp.domain.product.model.ProductId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 在庫コマンドサービス.
 */
@Service
@Transactional
public class InventoryCommandService {

    private final InventoryRepository inventoryRepository;

    public InventoryCommandService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * 在庫を引き当て（確保）します.
     *
     * @param productId 商品ID
     * @param quantity  引き当て数
     */
    public void allocate(String productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(new ProductId(productId))
                .orElseThrow(() -> new IllegalArgumentException("商品が見つかりません: " + productId));

        inventory.decreaseQuantity(quantity);

        inventoryRepository.save(inventory);
    }
}
