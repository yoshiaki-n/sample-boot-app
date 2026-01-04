package com.example.samplebootapp.application.order.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.samplebootapp.application.inventory.command.InventoryCommandService;
import com.example.samplebootapp.domain.order.model.Cart;
import com.example.samplebootapp.domain.order.model.CartRepository;
import com.example.samplebootapp.domain.order.model.Order;
import com.example.samplebootapp.domain.order.model.OrderRepository;
import com.example.samplebootapp.domain.product.model.CategoryId;
import com.example.samplebootapp.domain.product.model.Price;
import com.example.samplebootapp.domain.product.model.Product;
import com.example.samplebootapp.domain.product.model.ProductId;
import com.example.samplebootapp.domain.product.model.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderCommandServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private InventoryCommandService inventoryCommandService;

    @InjectMocks
    private OrderCommandService orderCommandService;

    private Cart cart;
    private Product product;

    @BeforeEach
    void setUp() {
        cart = Cart.create("user-1");
        // カートにアイテム追加
        cart.addItem("prod-1", 2);

        product = new Product(
                new ProductId("prod-1"),
                "Test Product",
                "Description",
                Price.of(1000),
                new CategoryId("cat-1"));
    }

    @Test
    @DisplayName("注文確定_正常系")
    void placeOrder_success() {
        // Arrange
        when(cartRepository.findByUserId("user-1")).thenReturn(Optional.of(cart));
        when(productRepository.findById(new ProductId("prod-1"))).thenReturn(Optional.of(product));

        // Act
        String orderId = orderCommandService.placeOrder("user-1");

        // Assert
        assertThat(orderId).isNotNull();

        // 在庫引き当て確認
        verify(inventoryCommandService).allocate("prod-1", 2);

        // 注文保存確認
        verify(orderRepository).save(any(Order.class));

        // カートクリアと保存確認
        assertThat(cart.getItems()).isEmpty();
        verify(cartRepository).save(cart);
    }

    @Test
    @DisplayName("注文確定_カートが空の場合エラー")
    void placeOrder_emptyCart() {
        // Arrange
        cart.clear(); // 空にする
        when(cartRepository.findByUserId("user-1")).thenReturn(Optional.of(cart));

        // Act & Assert
        assertThatThrownBy(() -> orderCommandService.placeOrder("user-1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cart is empty");
    }

    @Test
    @DisplayName("注文確定_商品が見つからない場合エラー")
    void placeOrder_productNotFound() {
        // Arrange
        when(cartRepository.findByUserId("user-1")).thenReturn(Optional.of(cart));
        when(productRepository.findById(new ProductId("prod-1"))).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> orderCommandService.placeOrder("user-1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product not found: prod-1");
    }
}
