package com.example.samplebootapp.presentation.order.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.samplebootapp.application.order.command.OrderCommandService;
import com.example.samplebootapp.application.order.query.OrderQueryService;
import com.example.samplebootapp.domain.order.model.OrderStatus;
import com.example.samplebootapp.presentation.order.api.response.OrderItemResponse;
import com.example.samplebootapp.presentation.order.api.response.OrderResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

  private MockMvc mockMvc;

  @Mock private OrderCommandService orderCommandService;
  @Mock private OrderQueryService orderQueryService;

  @InjectMocks private OrderController orderController;

  @BeforeEach
  void setUp() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(orderController)
            .setCustomArgumentResolvers(
                new HandlerMethodArgumentResolver() {
                  @Override
                  public boolean supportsParameter(MethodParameter parameter) {
                    return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
                  }

                  @Override
                  public Object resolveArgument(
                      MethodParameter parameter,
                      ModelAndViewContainer mavContainer,
                      NativeWebRequest webRequest,
                      WebDataBinderFactory binderFactory) {
                    return new User("test-user", "", Collections.emptyList());
                  }
                })
            .build();
  }

  @Test
  @DisplayName("注文履歴_正常系")
  void list_success() throws Exception {
    OrderResponse orderResponse =
        new OrderResponse(
            "ord-1",
            LocalDateTime.now(),
            1000,
            OrderStatus.ORDERED,
            List.of(new OrderItemResponse("item1", 1000, 1)));
    when(orderQueryService.getOrders("test-user")).thenReturn(List.of(orderResponse));

    mockMvc
        .perform(get("/api/orders"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value("ord-1"))
        .andExpect(jsonPath("$[0].totalAmount").value(1000));

    verify(orderQueryService).getOrders("test-user");
  }

  @Test
  @DisplayName("注文確定_正常系")
  void placeOrder_success() throws Exception {
    when(orderCommandService.placeOrder(any())).thenReturn("ord-123");

    mockMvc
        .perform(post("/api/orders"))
        .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
        .andExpect(status().isOk());

    verify(orderCommandService).placeOrder(any());
  }
}
