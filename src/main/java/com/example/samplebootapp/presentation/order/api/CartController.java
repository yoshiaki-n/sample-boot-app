package com.example.samplebootapp.presentation.order.api;

import com.example.samplebootapp.application.order.command.CartCommandService;
import com.example.samplebootapp.application.order.query.CartDto;
import com.example.samplebootapp.application.order.query.CartQueryService;
import com.example.samplebootapp.presentation.order.request.CartAddRequest;
import com.example.samplebootapp.presentation.order.response.CartResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "注文コンテキスト (Order Context)", description = "カートAPI")
@RestController
@RequestMapping("/api/cart")
public class CartController {

  private final CartCommandService cartCommandService;
  private final CartQueryService cartQueryService;

  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
  public CartController(CartCommandService cartCommandService, CartQueryService cartQueryService) {
    this.cartCommandService = cartCommandService;
    this.cartQueryService = cartQueryService;
  }

  @Operation(summary = "カート参照", description = "現在のユーザーのカート内の商品一覧を返します。")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public CartResponse getCart() {
    // 簡易的にユーザーID固定
    String userId = "test-user-001";
    CartDto dto = cartQueryService.getCart(userId);
    return CartResponse.from(dto);
  }

  @Operation(summary = "カート追加", description = "カートに商品を追加します。")
  @PostMapping("/items")
  @ResponseStatus(HttpStatus.OK)
  public void addItem(@RequestBody @Validated CartAddRequest request) {
    // 簡易的にユーザーID固定（認証未実装のため）
    String userId = "test-user-001";
    cartCommandService.addItem(userId, request.getProductId(), request.getQuantity());
  }
}
