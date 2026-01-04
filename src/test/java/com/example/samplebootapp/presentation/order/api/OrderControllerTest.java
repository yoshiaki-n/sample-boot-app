package com.example.samplebootapp.presentation.order.api;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.samplebootapp.application.order.command.OrderCommandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

  private MockMvc mockMvc;

  @Mock private OrderCommandService orderCommandService;

  @InjectMocks private OrderController orderController;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
  }

  @Test
  @DisplayName("注文確定API: 正常系")
  void placeOrder_success() throws Exception {
    // 実行 & 検証
    // AuthenticationPrincipalのモックはMockMvc standalone setupでは少し工夫が必要だが、
    // ここでは簡易的にControllerのロジックが userDetails.getUsername() を呼ぶ前提で
    // CustomArgumentResolverを使うか、あるいは SecurityContextHolder を設定するかなどが必要。
    // しかし standaloneSetup では SecurityContext はデフォルトでは効かない。
    // Controllerの実装をみると @AuthenticationPrincipal UserDetails userDetails を引数にとっている。
    // MockMvc でこれを再現するには CustomArgumentResolver を登録するか、
    // あるいはシンプルに認証Principalを注入できる仕組みを使う。

    // 今回は簡易的に、Controllerのテストとして「呼び出しが行われること」を確認したいが、
    // 引数の解決ができないとエラーになる可能性がある。
    // MockMvcBuilders.standaloneSetup(...).setCustomArgumentResolvers(...) で解決できる。

    // しかし、一番手っ取り早いのは、テスト用の ArgumentResolver を作ること。
    // ここでは匿名クラスで簡易実装する。

    mockMvc =
        MockMvcBuilders.standaloneSetup(orderController)
            .setCustomArgumentResolvers(
                new org.springframework.web.method.support.HandlerMethodArgumentResolver() {
                  @Override
                  public boolean supportsParameter(
                      org.springframework.core.MethodParameter parameter) {
                    return org.springframework.security.core.annotation.AuthenticationPrincipal
                        .class
                        .isAssignableFrom(
                            parameter
                                .getParameterAnnotation(
                                    org.springframework.security.core.annotation
                                        .AuthenticationPrincipal.class)
                                .annotationType());
                  }

                  @Override
                  public Object resolveArgument(
                      org.springframework.core.MethodParameter parameter,
                      org.springframework.web.method.support.ModelAndViewContainer mavContainer,
                      org.springframework.web.context.request.NativeWebRequest webRequest,
                      org.springframework.web.bind.support.WebDataBinderFactory binderFactory) {
                    return new org.springframework.security.core.userdetails.User(
                        "test-user", "password", java.util.Collections.emptyList());
                  }
                })
            .build();

    mockMvc.perform(post("/api/orders")).andExpect(status().isOk());

    verify(orderCommandService).placeOrder("test-user");
  }
}
