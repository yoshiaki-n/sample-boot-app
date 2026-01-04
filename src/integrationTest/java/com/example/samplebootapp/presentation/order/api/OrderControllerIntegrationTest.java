package com.example.samplebootapp.presentation.order.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.samplebootapp.domain.inventory.model.Inventory;
import com.example.samplebootapp.domain.inventory.model.InventoryRepository;
import com.example.samplebootapp.domain.order.model.Cart;
import com.example.samplebootapp.domain.order.model.CartRepository;
import com.example.samplebootapp.domain.order.model.OrderRepository;
import com.example.samplebootapp.domain.product.model.ProductId;
import com.example.samplebootapp.domain.product.model.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private JdbcTemplate jdbcTemplate;
        @Autowired
        private OrderRepository orderRepository;
        @Autowired
        private CartRepository cartRepository;
        @Autowired
        private ProductRepository productRepository;
        @Autowired
        private InventoryRepository inventoryRepository;

        @BeforeEach
        void setUp() {
                // データクリア
                // テーブル作成 (存在しない場合)
                jdbcTemplate.execute(
                                "CREATE TABLE IF NOT EXISTS order_orders ("
                                                + "id VARCHAR(255) PRIMARY KEY, "
                                                + "user_id VARCHAR(255) NOT NULL, "
                                                + "status VARCHAR(50) NOT NULL, "
                                                + "total_amount INTEGER NOT NULL, "
                                                + "ordered_at TIMESTAMP NOT NULL"
                                                + ")");

                jdbcTemplate.execute(
                                "CREATE TABLE IF NOT EXISTS order_order_items ("
                                                + "order_id VARCHAR(255) NOT NULL, "
                                                + "product_id VARCHAR(255) NOT NULL, "
                                                + "product_name VARCHAR(255) NOT NULL, "
                                                + "price INTEGER NOT NULL, "
                                                + "quantity INTEGER NOT NULL, "
                                                + "FOREIGN KEY (order_id) REFERENCES order_orders(id)"
                                                + ")");
        }

        @Test
        @DisplayName("注文確定_正常に注文が作成され在庫が減りカートが空になる")
        @WithMockUser(username = "test-user-order", roles = "USER")
        void placeOrder_success() throws Exception {
                // 1. 準備 (Arrange)
                // カテゴリ作成
                jdbcTemplate.update(
                                "INSERT INTO product_categories (id, name) VALUES (?, ?)", "cat-1", "Test Category");

                // 商品作成
                ProductId productId = new ProductId("prod-order-1");
                jdbcTemplate.update(
                                "INSERT INTO product_products (id, name, description, price_amount, category_id) VALUES (?, ?, ?, ?, ?)",
                                "prod-order-1",
                                "Order Test Product",
                                "Desc",
                                1000,
                                "cat-1");

                // 在庫作成
                jdbcTemplate.update(
                                "INSERT INTO inventory_inventories (product_id, quantity, version) VALUES (?, ?, ?)",
                                "prod-order-1",
                                10,
                                1);

                // カート作成
                Cart cart = Cart.create("test-user-order");
                cart.addItem("prod-order-1", 2);
                cartRepository.save(cart);

                // 2. 実行 (Act)
                mockMvc
                                .perform(
                                                post("/api/orders")
                                                                .with(user("test-user-order").roles("USER"))
                                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());

                // 3. 検証 (Assert)
                Integer count = jdbcTemplate.queryForObject(
                                "SELECT count(*) FROM order_orders WHERE user_id = ?",
                                Integer.class,
                                "test-user-order");
                assertThat(count).isEqualTo(1);

                Integer itemCount = jdbcTemplate.queryForObject(
                                "SELECT count(*) FROM order_order_items oi JOIN order_orders o ON oi.order_id = o.id WHERE o.user_id = ?",
                                Integer.class,
                                "test-user-order");
                assertThat(itemCount).isEqualTo(1);

                // 在庫が減っているか確認 (10 -> 8)
                Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow();
                assertThat(inventory.getQuantity()).isEqualTo(8);

                // カートが空になっているか確認
                Cart updatedCart = cartRepository.findByUserId("test-user-order").orElseThrow();
                assertThat(updatedCart.getItems()).isEmpty();
        }

        @Test
        @DisplayName("注文履歴取得_正常系")
        @WithMockUser(username = "test-user-order-list", roles = "USER")
        void list_success() throws Exception {
                // 1. 準備 (Arrange)
                // カテゴリ作成
                jdbcTemplate.update(
                                "INSERT INTO product_categories (id, name) VALUES (?, ?)", "cat-list-1",
                                "List Test Category");

                // 商品作成
                jdbcTemplate.update(
                                "INSERT INTO product_products (id, name, description, price_amount, category_id) VALUES (?, ?, ?, ?, ?)",
                                "prod-1",
                                "Product 1",
                                "Desc",
                                1000,
                                "cat-list-1");

                // 注文データ作成
                jdbcTemplate.update(
                                "INSERT INTO order_orders (id, user_id, status, total_amount, ordered_at) VALUES (?, ?, ?, ?, ?)",
                                "ord-list-1",
                                "test-user-order-list",
                                "ORDERED",
                                2000,
                                java.time.LocalDateTime.of(2023, 1, 1, 12, 0, 0));

                jdbcTemplate.update(
                                "INSERT INTO order_order_items (order_id, product_id, product_name, price, quantity) VALUES (?, ?, ?, ?, ?)",
                                "ord-list-1",
                                "prod-1",
                                "Product 1",
                                1000,
                                2);

                // 2. 実行 (Act)
                mockMvc
                                .perform(get("/api/orders").with(user("test-user-order-list").roles("USER")))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value("ord-list-1"))
                                .andExpect(jsonPath("$[0].totalAmount").value(2000))
                                .andExpect(jsonPath("$[0].items[0].name").value("Product 1"))
                                .andExpect(jsonPath("$[0].items[0].quantity").value(2));
        }
}
