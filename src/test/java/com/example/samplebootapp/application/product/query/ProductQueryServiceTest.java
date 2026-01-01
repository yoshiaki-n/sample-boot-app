package com.example.samplebootapp.application.product.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.samplebootapp.domain.product.model.CategoryId;
import com.example.samplebootapp.domain.product.model.Price;
import com.example.samplebootapp.domain.product.model.Product;
import com.example.samplebootapp.domain.product.model.ProductId;
import com.example.samplebootapp.domain.product.model.ProductRepository;
import com.example.samplebootapp.domain.product.model.ProductSearchCriteria;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductQueryServiceTest {

  @Mock private ProductRepository productRepository;

  @InjectMocks private ProductQueryService productQueryService;

  @Test
  @DisplayName("商品検索条件に基づいて商品を検索できること")
  void searchProductsByCriteria() {
    // 準備 (Arrange)
    ProductSearchCriteria criteria =
        new ProductSearchCriteria("test", new CategoryId("cat-1"), Price.of(100), Price.of(1000));

    Product product =
        new Product(
            ProductId.generate(),
            "Test Product",
            "Description",
            Price.of(500),
            new CategoryId("cat-1"));

    when(productRepository.search(criteria)).thenReturn(List.of(product));

    // 実行 (Act)
    List<Product> result = productQueryService.search(criteria);

    // 検証 (Assert)
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(product);
    verify(productRepository).search(criteria);
  }
}
