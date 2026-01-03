package com.example.samplebootapp.presentation.inventory.api;

import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.samplebootapp.domain.inventory.model.Inventory;
import com.example.samplebootapp.domain.inventory.model.InventoryRepository;
import com.example.samplebootapp.domain.product.model.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class InventoryControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private JdbcTemplate jdbcTemplate;
  @MockitoSpyBean private InventoryRepository inventoryRepository;

  @Test
  @DisplayName("在庫数確認API: 存在する商品の在庫数を取得できること")
  void getQuantity() throws Exception {
    // 準備
    String productId = ProductId.generate().getValue();
    String categoryId = "cat-integration-1";

    // カテゴリと商品を事前に登録 (FK制約のため)
    jdbcTemplate.update(
        "INSERT INTO product_categories (id, name) VALUES (?, ?)", categoryId, "Test Category");
    jdbcTemplate.update(
        "INSERT INTO product_products (id, name, description, price_amount, category_id) VALUES (?, ?, ?, ?, ?)",
        productId,
        "Test Product",
        "Desc",
        1000,
        categoryId);

    // 在庫を登録
    jdbcTemplate.update(
        "INSERT INTO inventory_inventories (product_id, quantity) VALUES (?, ?)", productId, 100);

    // 実行 & 検証
    mockMvc
        .perform(
            get("/api/inventory/products/{productId}", productId)
                .with(SecurityMockMvcRequestPostProcessors.user("testuser")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(productId))
        .andExpect(jsonPath("$.quantity").value(100));
  }

  @Test
  @DisplayName("在庫数確認API: 存在しない商品の場合は404エラー")
  void getQuantityNotFound() throws Exception {
    mockMvc
        .perform(
            get("/api/inventory/products/{productId}", "unknown-id")
                .with(SecurityMockMvcRequestPostProcessors.user("testuser")))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("在庫引き当てAPI: 在庫引き当てができること")
  void reserve() throws Exception {
    // 準備
    String productId = ProductId.generate().getValue();
    String categoryId = "cat-integration-2";

    jdbcTemplate.update(
        "INSERT INTO product_categories (id, name) VALUES (?, ?)", categoryId, "Test Category 2");
    jdbcTemplate.update(
        "INSERT INTO product_products (id, name, description, price_amount, category_id) VALUES (?, ?, ?, ?, ?)",
        productId,
        "Test Product 2",
        "Desc",
        2000,
        categoryId);

    // versionカラムも含めて初期化 (0)
    jdbcTemplate.update(
        "INSERT INTO inventory_inventories (product_id, quantity, version) VALUES (?, ?, ?)",
        productId,
        100,
        0);

    // 実行 & 検証
    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(
                    "/api/inventory/reservations")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{\"productId\": \"" + productId + "\", \"quantity\": 10}")
                .with(SecurityMockMvcRequestPostProcessors.user("testuser"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isOk());

    // DB検証
    Integer quantity =
        jdbcTemplate.queryForObject(
            "SELECT quantity FROM inventory_inventories WHERE product_id = ?",
            Integer.class,
            productId);
    Long version =
        jdbcTemplate.queryForObject(
            "SELECT version FROM inventory_inventories WHERE product_id = ?",
            Long.class,
            productId);

    org.assertj.core.api.Assertions.assertThat(quantity).isEqualTo(90);
    org.assertj.core.api.Assertions.assertThat(version).isEqualTo(1L);
  }

  @Test
  @DisplayName("在庫引き当てAPI: 在庫不足の場合はエラーになること")
  void reserveInsufficientStock() throws Exception {
    // 準備
    String productId = ProductId.generate().getValue();
    String categoryId = "cat-integration-3";

    jdbcTemplate.update(
        "INSERT INTO product_categories (id, name) VALUES (?, ?)", categoryId, "Test Category 3");
    jdbcTemplate.update(
        "INSERT INTO product_products (id, name, description, price_amount, category_id) VALUES (?, ?, ?, ?, ?)",
        productId,
        "Test Product 3",
        "Desc",
        3000,
        categoryId);
    jdbcTemplate.update(
        "INSERT INTO inventory_inventories (product_id, quantity, version) VALUES (?, ?, ?)",
        productId,
        5,
        0);

    // 実行 & 検証 (要求数10 > 在庫数5)
    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(
                    "/api/inventory/reservations")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{\"productId\": \"" + productId + "\", \"quantity\": 10}")
                .with(SecurityMockMvcRequestPostProcessors.user("testuser"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("在庫引き当てAPI: 楽観ロックエラーが発生した場合は409 Conflictになること")
  void reserveOptimisticLockError() throws Exception {
    // 準備
    String productId = ProductId.generate().getValue();
    String categoryId = "cat-integration-4";

    jdbcTemplate.update(
        "INSERT INTO product_categories (id, name) VALUES (?, ?)", categoryId, "Test Category 4");
    jdbcTemplate.update(
        "INSERT INTO product_products (id, name, description, price_amount, category_id) VALUES (?, ?, ?, ?, ?)",
        productId,
        "Test Product 4",
        "Desc",
        4000,
        categoryId);
    jdbcTemplate.update(
        "INSERT INTO inventory_inventories (product_id, quantity, version) VALUES (?, ?, ?)",
        productId,
        100,
        0);

    // Repository.findByProductIdが呼ばれた後に、DBのversionを更新する
    doAnswer(
            invocation -> {
              // 本来のメソッドを呼び出す（現在のバージョン0のInventoryが返る）
              java.util.Optional<Inventory> result =
                  (java.util.Optional<Inventory>) invocation.callRealMethod();

              // DB更新 (versionを1に進める)
              jdbcTemplate.update(
                  "UPDATE inventory_inventories SET version = version + 1 WHERE product_id = ?",
                  productId);

              return result;
            })
        .when(inventoryRepository)
        .findByProductId(new ProductId(productId));

    // 実行 & 検証
    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(
                    "/api/inventory/reservations")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{\"productId\": \"" + productId + "\", \"quantity\": 10}")
                .with(SecurityMockMvcRequestPostProcessors.user("testuser"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isConflict()); // 409 Conflict
  }
}
