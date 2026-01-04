package com.example.samplebootapp.application.product.query;

import com.example.samplebootapp.domain.product.model.Category;
import com.example.samplebootapp.domain.product.model.CategoryId;
import com.example.samplebootapp.domain.product.model.CategoryRepository;
import com.example.samplebootapp.domain.product.model.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** カテゴリ照会サービス. */
@Service
@Transactional(readOnly = true)
public class CategoryQueryService {

  private final CategoryRepository categoryRepository;

  public CategoryQueryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  /**
   * カテゴリ一覧を階層構造で取得します.
   *
   * @return カテゴリリスト（階層構造）
   */
  public List<CategoryDto> listCategories() {
    List<Category> allCategories = categoryRepository.findAll();

    // IDでマップ化
    Map<CategoryId, CategoryDto> dtoMap = allCategories.stream()
        .collect(
            Collectors.toMap(
                Category::getId, c -> new CategoryDto(c.getId().getValue(), c.getName())));

    List<CategoryDto> rootCategories = new ArrayList<>();

    for (Category category : allCategories) {
      CategoryDto dto = dtoMap.get(category.getId());
      CategoryId parentId = category.getParentId();

      if (parentId == null) {
        rootCategories.add(dto);
      } else {
        CategoryDto parent = dtoMap.get(parentId);
        if (parent != null) {
          parent.addChild(dto);
        } else {
          // 親が見つからない場合はルートとして扱う（データ整合性の問題だが、APIとしては表示する）
          rootCategories.add(dto);
        }
      }
    }

    return rootCategories;
  }
}
