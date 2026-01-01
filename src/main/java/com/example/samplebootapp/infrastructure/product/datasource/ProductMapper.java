package com.example.samplebootapp.infrastructure.product.datasource;

import com.example.samplebootapp.domain.product.model.Product;
import com.example.samplebootapp.domain.product.model.ProductId;
import com.example.samplebootapp.domain.product.model.ProductSearchCriteria;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/** 商品MyBatis Mapper. */
@Mapper
public interface ProductMapper {

  /**
   * 商品IDで商品を検索します.
   *
   * @param id 商品ID
   * @return 商品（Optional）
   */
  Optional<Product> findById(@Param("id") ProductId id);

  /**
   * 検索条件に基づいて商品を検索します.
   *
   * @param criteria 検索条件
   * @return 商品リスト
   */
  List<Product> search(@Param("criteria") ProductSearchCriteria criteria);
}
