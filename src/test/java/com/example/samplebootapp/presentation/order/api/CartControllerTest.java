package com.example.samplebootapp.presentation.order.api;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.samplebootapp.application.order.command.CartCommandService;
import com.example.samplebootapp.application.order.query.CartDto;
import com.example.samplebootapp.application.order.query.CartQueryService;
import com.example.samplebootapp.presentation.order.request.CartAddRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

  private MockMvc mockMvc;

  @Mock
  private CartCommandService cartCommandService;

  @Mock
  private CartQueryService cartQueryService;

  @InjectMocks
  private CartController cartController;

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
  }

  @Test
  @DisplayName("カート追加API: 正常系")
  void addItem_success() throws Exception {
    // 準備 (Arrange)
    CartAddRequest request = new CartAddRequest();
    request.setProductId("prod_001");
    request.setQuantity(1);

    // 実行 (Act) & 検証 (Assert)
    mockMvc
        .perform(
            post("/api/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());

    verify(cartCommandService).addItem("test-user-001", "prod_001", 1);
  }

  @Test
  @DisplayName("カート商品数量変更API: 正常系")
  void updateItemQuantity_success() throws Exception {
    // 準備
    com.example.samplebootapp.presentation.order.request.CartItemUpdateRequest request = new com.example.samplebootapp.presentation.order.request.CartItemUpdateRequest(
        3);

    // 実行 & 検証
    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(
                "/api/cart/items/{itemId}", "prod_001")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());

    verify(cartCommandService).updateItemQuantity("test-user-001", "prod_001", 3);
  }

  @Test
  @DisplayName("カート参照API: 正常系")
  void getCart_success() throws Exception {
    // 準備
    CartDto response = new CartDto("cart-1", java.util.Collections.emptyList());

    org.mockito.Mockito.when(cartQueryService.getCart("test-user-001")).thenReturn(response);

    // 実行 & 検証
    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/cart"))
        .andExpect(status().isOk())
        .andExpect(
            org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.cartId")
                .value("cart-1"));
  }
}
