package com.example.samplebootapp.application.order.command;

import com.example.samplebootapp.application.inventory.command.InventoryCommandService;
import com.example.samplebootapp.domain.order.model.Cart;
import com.example.samplebootapp.domain.order.model.CartRepository;
import com.example.samplebootapp.domain.order.model.Order;
import com.example.samplebootapp.domain.order.model.OrderItem;
import com.example.samplebootapp.domain.order.model.OrderRepository;
import com.example.samplebootapp.domain.product.model.Product;
import com.example.samplebootapp.domain.product.model.ProductId;
import com.example.samplebootapp.domain.product.model.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 注文コマンドサービス.
 */
@Service
@Transactional
public class OrderCommandService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final InventoryCommandService inventoryCommandService;

    public OrderCommandService(
            OrderRepository orderRepository,
            CartRepository cartRepository,
            ProductRepository productRepository,
            InventoryCommandService inventoryCommandService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.inventoryCommandService = inventoryCommandService;
    }

    /**
     * 注文を確定します.
     *
     * @param userId ユーザーID
     * @return 注文ID
     */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public String placeOrder(String userId) {
        // 1. カート取得
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // 2. 在庫引き当て & 注文明細作成
        List<OrderItem> orderItems = new ArrayList<>();
        for (com.example.samplebootapp.domain.order.model.CartItem cartItem : cart.getItems()) {
            // 商品情報取得
            Product product = productRepository.findById(new ProductId(cartItem.getProductId()))
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + cartItem.getProductId()));

            // 在庫引き当て
            inventoryCommandService.allocate(product.getId().getValue(), cartItem.getQuantity());

            // 注文明細作成
            orderItems.add(new OrderItem(
                    product.getId(),
                    product.getName(),
                    product.getPrice().getAmount().intValue(),
                    cartItem.getQuantity()));
        }

        // 3. 注文作成
        Order order = Order.create(userId, orderItems);

        // 4. 注文保存
        orderRepository.save(order);

        // 5. カートクリア
        cart.clear();
        cartRepository.save(cart);

        return order.getId();
    }
}
