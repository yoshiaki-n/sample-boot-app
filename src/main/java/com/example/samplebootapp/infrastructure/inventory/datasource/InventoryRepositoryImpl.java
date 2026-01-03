package com.example.samplebootapp.infrastructure.inventory.datasource;

import com.example.samplebootapp.domain.inventory.model.Inventory;
import com.example.samplebootapp.domain.inventory.model.InventoryRepository;
import com.example.samplebootapp.domain.product.model.ProductId;
import java.util.Optional;
import org.springframework.dao.OptimisticLockingFailureException;
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

    @Override
    public void save(Inventory inventory) {
        int updatedCount = inventoryMapper.update(inventory);
        if (updatedCount == 0) {
            throw new OptimisticLockingFailureException("以前に他のユーザーによって更新されています。");
        }
    }
}
