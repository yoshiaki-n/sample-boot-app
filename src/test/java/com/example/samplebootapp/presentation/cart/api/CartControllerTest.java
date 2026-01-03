package com.example.samplebootapp.presentation.cart.api;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.samplebootapp.application.cart.command.CartCommandService;
import com.example.samplebootapp.presentation.cart.request.CartAddRequest;
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
}
