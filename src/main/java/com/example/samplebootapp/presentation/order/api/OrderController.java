package com.example.samplebootapp.presentation.order.api;

import com.example.samplebootapp.application.order.command.OrderCommandService;
import com.example.samplebootapp.application.order.query.OrderQueryService;
import com.example.samplebootapp.presentation.order.api.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "注文管理")
public class OrderController {

  private final OrderCommandService orderCommandService;
  private final OrderQueryService orderQueryService;

  public OrderController(
      OrderCommandService orderCommandService, OrderQueryService orderQueryService) {
    this.orderCommandService = orderCommandService;
    this.orderQueryService = orderQueryService;
  }

  @GetMapping
  @Operation(summary = "注文履歴", description = "ユーザーの過去の注文履歴を一覧で返します。")
  public ResponseEntity<List<OrderResponse>> list(
      @AuthenticationPrincipal UserDetails userDetails) {
    // TODO: 本来は UserDetails から安全に ID を取り出す仕組みが必要だが、簡易的に Username を ID とする
    String userId = userDetails.getUsername();
    List<com.example.samplebootapp.application.order.query.dto.OrderDto> orders = orderQueryService.getOrders(userId);
    return ResponseEntity.ok(orders.stream().map(this::toResponse).toList());
  }

  @GetMapping("/{orderId}")
  @Operation(summary = "注文詳細", description = "指定された注文の詳細情報を返します。")
  public ResponseEntity<OrderResponse> get(
      @AuthenticationPrincipal UserDetails userDetails,
      @org.springframework.web.bind.annotation.PathVariable("orderId") String orderId) {
    // TODO: 本来は UserDetails から安全に ID を取り出す仕組みが必要だが、簡易的に Username を ID とする
    String userId = userDetails.getUsername();
    return orderQueryService
        .getOrder(orderId, userId)
        .map(this::toResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @Operation(summary = "注文確定", description = "カートの内容で注文を作成し、在庫を引き当てて確定します。")
  public ResponseEntity<Void> placeOrder(@AuthenticationPrincipal UserDetails userDetails) {
    // TODO: 本来は UserDetails から安全に ID を取り出す仕組みが必要だが、簡易的に Username を ID とする
    String userId = userDetails.getUsername();
    orderCommandService.placeOrder(userId);
    return ResponseEntity.ok().build();
  }

  private OrderResponse toResponse(
      com.example.samplebootapp.application.order.query.dto.OrderDto dto) {
    List<com.example.samplebootapp.presentation.order.api.response.OrderItemResponse> itemResponses = dto.items()
        .stream()
        .map(
            item -> new com.example.samplebootapp.presentation.order.api.response.OrderItemResponse(
                item.name(), item.price(), item.quantity()))
        .toList();

    return new OrderResponse(
        dto.id(), dto.orderedAt(), dto.totalAmount(), dto.status(), itemResponses);
  }
}
