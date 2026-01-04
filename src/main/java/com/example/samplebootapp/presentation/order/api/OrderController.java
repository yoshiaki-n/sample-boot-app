package com.example.samplebootapp.presentation.order.api;

import com.example.samplebootapp.application.order.command.OrderCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "注文管理")
public class OrderController {

    private final OrderCommandService orderCommandService;

    public OrderController(OrderCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    @PostMapping
    @Operation(summary = "注文確定", description = "カートの内容で注文を作成し、在庫を引き当てて確定します。")
    public ResponseEntity<Void> placeOrder(@AuthenticationPrincipal UserDetails userDetails) {
        // TODO: 本来は UserDetails から安全に ID を取り出す仕組みが必要だが、簡易的に Username を ID とする
        String userId = userDetails.getUsername();
        orderCommandService.placeOrder(userId);
        return ResponseEntity.ok().build();
    }
}
