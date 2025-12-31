package com.example.samplebootapp.infrastructure.product.datasource;

import com.example.samplebootapp.domain.product.model.Product;
import com.example.samplebootapp.domain.product.model.ProductId;
import com.example.samplebootapp.domain.product.model.ProductRepository;
import com.example.samplebootapp.domain.product.model.ProductSearchCriteria;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository実装クラス.
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductMapper productMapper;

    public ProductRepositoryImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return productMapper.findById(id);
    }

    @Override
    public List<Product> search(ProductSearchCriteria criteria) {
        return productMapper.search(criteria);
    }
}
