package com.example.samplebootapp.presentation.order.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.samplebootapp.infrastructure.order.mapper.CartMapper;
import com.example.samplebootapp.presentation.order.request.CartAddRequest;
import com.example.samplebootapp.presentation.order.request.CartItemUpdateRequest;
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
    CartAddRequest request = new CartAddRequest();
    request.setProductId("prod_001");
    request.setQuantity(1);

    mockMvc
        .perform(
            post("/api/cart/items")
                .with(user("user-1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("カート参照API: 正常系 (統合テスト)")
  void getCart_success_integration() throws Exception {
    CartAddRequest request = new CartAddRequest();
    request.setProductId("prod_002");
    request.setQuantity(3);

    mockMvc
        .perform(
            post("/api/cart/items")
                .with(user("user-1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk());

    mockMvc
        .perform(get("/api/cart").with(user("user-1")))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items[0].productId").value("prod_002"))
        .andExpect(jsonPath("$.items[0].quantity").value(3));
  }

  @Test
  @DisplayName("カート商品数量変更API: 正常系 (統合テスト)")
  void updateItemQuantity_success_integration() throws Exception {
    CartAddRequest addRequest = new CartAddRequest();
    addRequest.setProductId("prod_003");
    addRequest.setQuantity(1);

    mockMvc
        .perform(
            post("/api/cart/items")
                .with(user("user-1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addRequest)))
        .andDo(print())
        .andExpect(status().isOk());

    CartItemUpdateRequest updateRequest = new CartItemUpdateRequest(5);

    mockMvc
        .perform(
            put("/api/cart/items/{itemId}", "prod_003")
                .with(user("user-1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
        .andDo(print())
        .andExpect(status().isOk());

    mockMvc
        .perform(get("/api/cart").with(user("user-1")))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items[?(@.productId == 'prod_003')].quantity").value(5));
  }

  @Test
  @DisplayName("カート削除API: 正常系 (統合テスト)")
  void deleteItem_success_integration() throws Exception {
    CartAddRequest addRequest = new CartAddRequest();
    addRequest.setProductId("prod_004");
    addRequest.setQuantity(1);

    mockMvc
        .perform(
            post("/api/cart/items")
                .with(user("user-1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addRequest)))
        .andDo(print())
        .andExpect(status().isOk());

    mockMvc
        .perform(delete("/api/cart/items/{itemId}", "prod_004").with(user("user-1")))
        .andDo(print())
        .andExpect(status().isOk());

    mockMvc
        .perform(get("/api/cart").with(user("user-1")))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items[?(@.productId == 'prod_004')]").doesNotExist());
  }
}
