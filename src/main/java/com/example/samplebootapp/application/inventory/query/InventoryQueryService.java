package com.example.samplebootapp.application.inventory.query;

import com.example.samplebootapp.domain.inventory.model.Inventory;
import com.example.samplebootapp.domain.inventory.model.InventoryRepository;
import com.example.samplebootapp.domain.product.model.ProductId;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 在庫参照サービス. */
@Service
@Transactional(readOnly = true)
public class InventoryQueryService {

  private final InventoryRepository inventoryRepository;

  public InventoryQueryService(InventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }

  /**
   * 商品IDで在庫を取得します.
   *
   * @param productId 商品ID
   * @return 在庫（存在しない場合はOptional.empty）
   */
  public Optional<Inventory> findByProductId(ProductId productId) {
    return inventoryRepository.findByProductId(productId);
  }
}
