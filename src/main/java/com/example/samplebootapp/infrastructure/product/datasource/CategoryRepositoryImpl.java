package com.example.samplebootapp.infrastructure.product.datasource;

import com.example.samplebootapp.domain.product.model.Category;
import com.example.samplebootapp.domain.product.model.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

/** カテゴリリポジトリ実装. */
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

  private final CategoryMapper categoryMapper;

  public CategoryRepositoryImpl(CategoryMapper categoryMapper) {
    this.categoryMapper = categoryMapper;
  }

  @Override
  public List<Category> findAll() {
    return categoryMapper.findAll();
  }
}
