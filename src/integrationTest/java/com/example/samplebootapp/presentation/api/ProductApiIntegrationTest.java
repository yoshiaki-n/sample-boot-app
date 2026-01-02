package com.example.samplebootapp.presentation.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductApiIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Test
  @DisplayName("商品一覧取得APIの統合テスト: ステータスコード200と正しいJSONレスポンスが返ること")
  @Sql(
      scripts = {"/db/testdata/delete_products.sql", "/db/testdata/insert_products.sql"},
      executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(
      scripts = "/db/testdata/delete_products.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  void testGetProducts() throws Exception {
    mockMvc
        .perform(get("/api/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(3))
        // P001: Smartphone
        .andExpect(jsonPath("$[0].id").value("P001"))
        .andExpect(jsonPath("$[0].name").value("Smartphone"))
        .andExpect(jsonPath("$[0].price").value(100000))
        .andExpect(jsonPath("$[0].categoryId").value("C001"))
        // P002: Laptop
        .andExpect(jsonPath("$[1].id").value("P002"))
        .andExpect(jsonPath("$[1].name").value("Laptop"))
        .andExpect(jsonPath("$[1].price").value(200000))
        .andExpect(jsonPath("$[1].categoryId").value("C001"))
        // P003: Tech Book
        .andExpect(jsonPath("$[2].id").value("P003"))
        .andExpect(jsonPath("$[2].name").value("Tech Book"))
        .andExpect(jsonPath("$[2].price").value(3000))
        .andExpect(jsonPath("$[2].categoryId").value("C002"));
  }

  @Test
  @DisplayName("商品詳細取得APIの統合テスト: 存在するID指定でステータスコード200と正しいJSONレスポンスが返ること")
  @Sql(
      scripts = {"/db/testdata/delete_products.sql", "/db/testdata/insert_products.sql"},
      executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(
      scripts = "/db/testdata/delete_products.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  void testGetProductById() throws Exception {
    mockMvc
        .perform(get("/api/products/P001"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("P001"))
        .andExpect(jsonPath("$.name").value("Smartphone"))
        .andExpect(jsonPath("$.price").value(100000))
        .andExpect(jsonPath("$.categoryId").value("C001"));
  }

  @Test
  @DisplayName("商品詳細取得APIの統合テスト: 存在しないID指定でステータスコード404が返ること")
  void testGetProductByIdNotFound() throws Exception {
    mockMvc.perform(get("/api/products/P999")).andExpect(status().isNotFound());
  }
}
