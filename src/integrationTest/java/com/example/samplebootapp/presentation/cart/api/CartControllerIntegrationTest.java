package com.example.samplebootapp.presentation.cart.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.samplebootapp.infrastructure.cart.mapper.CartMapper;
import com.example.samplebootapp.presentation.cart.request.CartAddRequest;
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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ObjectMapper objectMapper;

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
}
