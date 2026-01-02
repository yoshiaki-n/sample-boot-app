package com.example.samplebootapp.presentation.product.api;

import com.example.samplebootapp.application.product.query.ProductQueryService;
import com.example.samplebootapp.domain.product.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 商品APIコントローラ. */
@RestController
@RequestMapping("/api/products")
@Tag(name = "商品", description = "商品に関連する操作を提供します。")
public class ProductController {

  private final ProductQueryService productQueryService;

  public ProductController(ProductQueryService productQueryService) {
    this.productQueryService = productQueryService;
  }

  /**
   * 商品を検索します.
   *
   * @param request 検索条件
   * @return 商品リスト
   */
  @GetMapping
  @Operation(summary = "商品検索", description = "検索条件に基づいて商品を検索します。")
  public ResponseEntity<List<ProductResponse>> search(
      @ModelAttribute @Validated ProductSearchRequest request) {
    List<Product> products = productQueryService.search(request.toCriteria());
    List<ProductResponse> response = products.stream().map(ProductResponse::from).toList();
    return ResponseEntity.ok(response);
  }
}
