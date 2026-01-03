package com.example.samplebootapp.presentation.inventory.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.samplebootapp.domain.product.model.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class InventoryControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  @DisplayName("在庫数確認API: 存在する商品の在庫数を取得できること")
  void getQuantity() throws Exception {
    // 準備
    String productId = ProductId.generate().getValue();
    String categoryId = "cat-integration-1";

    // カテゴリと商品を事前に登録 (FK制約のため)
    jdbcTemplate.update("INSERT INTO product_categories (id, name) VALUES (?, ?)", categoryId,
        "Test Category");
    jdbcTemplate.update(
        "INSERT INTO product_products (id, name, description, price_amount, category_id) VALUES (?, ?, ?, ?, ?)",
        productId, "Test Product", "Desc", 1000, categoryId);

    // 在庫を登録
    jdbcTemplate.update(
        "INSERT INTO inventory_inventories (product_id, quantity) VALUES (?, ?)", productId,
        100);

    // 実行 & 検証
    mockMvc
        .perform(get("/api/inventory/products/{productId}", productId)
            .with(SecurityMockMvcRequestPostProcessors.user("testuser")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(productId))
        .andExpect(jsonPath("$.quantity").value(100));
  }

  @Test
  @DisplayName("在庫数確認API: 存在しない商品の場合は404エラー")
  void getQuantityNotFound() throws Exception {
    mockMvc
        .perform(get("/api/inventory/products/{productId}", "unknown-id")
            .with(SecurityMockMvcRequestPostProcessors.user("testuser")))
        .andExpect(status().isNotFound());
  }
}
