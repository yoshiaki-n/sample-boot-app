package com.example.samplebootapp.presentation.order.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.samplebootapp.infrastructure.order.mapper.CartMapper;
import com.example.samplebootapp.presentation.order.request.CartAddRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CartControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private CartMapper cartMapper;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("カート追加API: 正常系 (統合テスト)")
  void addItem_success_integration() throws Exception {
    // 準備 (Arrange)
    CartAddRequest request = new CartAddRequest();
    request.setProductId("prod_001");
    request.setQuantity(1);

    // 実行 (Act)
    mockMvc
        .perform(
            post("/api/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());

    // 検証 (Assert)
    // 簡易的にDB確認は省略するか、Mapperで検索して確認
    // ここではエラーにならないこと（200 OK）を確認
  }

  @Test
  @DisplayName("カート参照API: 正常系 (統合テスト)")
  void getCart_success_integration() throws Exception {
    // 準備 (Arrange)
    // 事前にデータを投入
    CartAddRequest request = new CartAddRequest();
    request.setProductId("prod_002");
    request.setQuantity(3);

    mockMvc
        .perform(
            post("/api/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());

    // 実行 (Act) & 検証 (Assert)
    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/cart"))
        .andExpect(status().isOk())
        .andExpect(
            org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                    "$.items[0].productId")
                .value("prod_002"))
        .andExpect(
            org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                    "$.items[0].quantity")
                .value(3));
  }

  @Test
  @DisplayName("カート商品数量変更API: 正常系 (統合テスト)")
  void updateItemQuantity_success_integration() throws Exception {
    // 準備 (Arrange)
    // 事前にデータを投入
    CartAddRequest addRequest = new CartAddRequest();
    addRequest.setProductId("prod_003");
    addRequest.setQuantity(1);

    mockMvc
        .perform(
            post("/api/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addRequest)))
        .andExpect(status().isOk());

    // 実行 (Act): 数量を5に変更
    com.example.samplebootapp.presentation.order.request.CartItemUpdateRequest updateRequest =
        new com.example.samplebootapp.presentation.order.request.CartItemUpdateRequest(5);

    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(
                    "/api/cart/items/{itemId}", "prod_003")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk());

    // 検証 (Assert): 数量が5になっているか確認
    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/cart"))
        .andExpect(status().isOk())
        .andExpect(
            org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                    "$.items[?(@.productId == 'prod_003')].quantity")
                .value(5));
  }

  @Test
  @DisplayName("カート削除API: 正常系 (統合テスト)")
  void deleteItem_success_integration() throws Exception {
    // 準備 (Arrange)
    // 事前にデータを投入
    CartAddRequest addRequest = new CartAddRequest();
    addRequest.setProductId("prod_004");
    addRequest.setQuantity(1);

    mockMvc
        .perform(
            post("/api/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addRequest)))
        .andExpect(status().isOk());

    // 実行 (Act): 削除
    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(
                "/api/cart/items/{itemId}", "prod_004"))
        .andExpect(status().isOk());

    // 検証 (Assert): 削除されていることを確認
    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/cart"))
        .andExpect(status().isOk())
        .andExpect(
            org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath(
                    "$.items[?(@.productId == 'prod_004')]")
                .doesNotExist());
  }
}
