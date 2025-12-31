package com.example.samplebootapp.presentation.product.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.samplebootapp.application.product.query.ProductQueryService;
import com.example.samplebootapp.domain.product.model.CategoryId;
import com.example.samplebootapp.domain.product.model.Price;
import com.example.samplebootapp.domain.product.model.Product;
import com.example.samplebootapp.domain.product.model.ProductId;
import com.example.samplebootapp.domain.product.model.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ProductControllerTest {

  private ProductRepository productRepository;
  private ProductController productController;

  @BeforeEach
  void setUp() {
    productRepository = mock(ProductRepository.class);
    // User requested to use mock for ProductRepository, so we instantiate the real
    // service
    // injecting the mock repository, and then use that service for the controller.
    ProductQueryService productQueryService = new ProductQueryService(productRepository);
    productController = new ProductController(productQueryService);
  }

  @Test
  @DisplayName("商品検索APIが正常に動作し、レスポンスを返すこと")
  void searchProducts() {
    // 準備 (Arrange)
    Product product = new Product(
        ProductId.generate(),
        "Test Product",
        "Test Description",
        Price.of(1000),
        new CategoryId("cat-1"));
    when(productRepository.search(any())).thenReturn(List.of(product));

    ProductSearchRequest request = new ProductSearchRequest("Test", null, 500L, 2000L);

    // 実行 (Act)
    ResponseEntity<List<ProductResponse>> responseEntity = productController.search(request);

    // 検証 (Assert)
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    List<ProductResponse> body = responseEntity.getBody();
    assertThat(body).hasSize(1);
    assertThat(body.get(0).name()).isEqualTo("Test Product");
    assertThat(body.get(0).price()).isEqualTo(BigDecimal.valueOf(1000));

    // Verify interaction with Repository through the Service
    verify(productRepository).search(any());
  }
}
