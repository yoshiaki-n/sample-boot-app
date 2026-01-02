package com.example.samplebootapp.application.product.query;

import com.example.samplebootapp.domain.product.model.Product;
import com.example.samplebootapp.domain.product.model.ProductId;
import com.example.samplebootapp.domain.product.model.ProductRepository;
import com.example.samplebootapp.domain.product.model.ProductSearchCriteria;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 商品検索アプリケーションサービス. */
@Service
@Transactional(readOnly = true)
public class ProductQueryService {

  private final ProductRepository productRepository;

  public ProductQueryService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  /**
   * 条件に基づいて商品を検索します.
   *
   * @param criteria 検索条件
   * @return 検索結果のリスト
   */
  public List<Product> search(ProductSearchCriteria criteria) {
    return productRepository.search(criteria);
  }

  /**
   * IDで商品を検索します.
   *
   * @param id 商品ID
   * @return 商品（存在しない場合はOptional.empty）
   */
  public java.util.Optional<Product> findById(String id) {
    return productRepository.findById(new ProductId(id));
  }
}
