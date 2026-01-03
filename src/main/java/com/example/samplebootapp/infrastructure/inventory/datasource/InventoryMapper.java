package com.example.samplebootapp.infrastructure.inventory.datasource;

import com.example.samplebootapp.domain.inventory.model.Inventory;
import com.example.samplebootapp.domain.product.model.ProductId;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 在庫MyBatis Mapper.
 */
@Mapper
public interface InventoryMapper {

    /**
     * 商品IDで在庫を検索します.
     *
     * @param productId 商品ID
     * @return 在庫（Optional）
     */
    Optional<Inventory> findByProductId(@Param("productId") ProductId productId);

    /**
     * 在庫を更新します.
     *
     * @param inventory 在庫
     * @return 更新件数
     */
    int update(Inventory inventory);
}
