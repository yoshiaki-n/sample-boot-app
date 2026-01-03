package com.example.samplebootapp.infrastructure.inventory.datasource;

import com.example.samplebootapp.domain.inventory.model.Inventory;
import com.example.samplebootapp.domain.inventory.model.InventoryRepository;
import com.example.samplebootapp.domain.product.model.ProductId;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * InventoryRepository実装クラス.
 */
@Repository
public class InventoryRepositoryImpl implements InventoryRepository {

    private final InventoryMapper inventoryMapper;

    public InventoryRepositoryImpl(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public Optional<Inventory> findByProductId(ProductId productId) {
        return inventoryMapper.findByProductId(productId);
    }
}
