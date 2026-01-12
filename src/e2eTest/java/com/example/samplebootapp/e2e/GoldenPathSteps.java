package com.example.samplebootapp.e2e;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.samplebootapp.domain.product.model.CategoryId;
import com.example.samplebootapp.domain.product.model.Price;
import com.example.samplebootapp.domain.product.model.Product;
import com.example.samplebootapp.domain.product.model.ProductId;
import com.example.samplebootapp.domain.user.model.Member;
import com.example.samplebootapp.domain.user.model.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ja.かつ;
import io.cucumber.java.ja.ならば;
import io.cucumber.java.ja.もし;
import io.cucumber.java.ja.前提;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

public class GoldenPathSteps {

  @Autowired private MockMvc mockMvc;
  @Autowired private MemberRepository memberRepository;
  @Autowired private PasswordEncoder passwordEncoder;
  private final ObjectMapper objectMapper = new ObjectMapper();
  @Autowired private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;
  @Autowired private DataSource dataSource;

  private MockHttpSession session;
  private String memberEmail = "golden_path_user@example.com";
  private String memberPassword = "Password123!";

  @Transactional
  @前提("商品カタログに以下の商品が存在する")
  public void 商品カタログに以下の商品が存在する(DataTable dataTable) {
    // 既存データをクリーンアップ（必要であれば）
    // ここではテストごとにTransactionがロールバックされることを期待するか、
    // 明示的にデータを削除/作成する。
    // 今回は簡易的に必要なデータが存在することを確認・作成する。
    jdbcTemplate.update(
        "TRUNCATE TABLE order_order_items, order_orders, order_cart_items, order_carts, inventory_inventories, user_members, product_products, product_categories CASCADE");

    // Flywayを手動実行してテーブルを作成する
    // Flyway configuration is handled by Spring Boot
    // System.out.println("DEBUG: Running manual flyway config and migration");
    // Flyway flyway = Flyway.configure()
    // .dataSource(dataSource)
    // .locations("classpath:db/migration")
    // .cleanDisabled(false)
    // .load();
    // flyway.clean();
    // flyway.migrate();

    List<Map<String, String>> products = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> row : products) {
      String name = row.get("商品名");
      int price = Integer.parseInt(row.get("価格"));

      // カテゴリは仮のものを使用、なければ作成などのロジックが必要だが
      // ここでは既存のカテゴリID 'C001' を使用すると仮定 or 簡易作成
      // 実装簡略化のため、JdbcTemplateを使って直接保存
      Product product =
          new Product(
              new ProductId("P-" + name),
              name,
              "Description for " + name,
              new Price(BigDecimal.valueOf(price)),
              new CategoryId("C001"));
      ensureCategoryExists("C001", "Electronics");
      insertProduct(product);
      insertInventory(product.getId());
    }
  }

  private void insertInventory(ProductId productId) {
    jdbcTemplate.update(
        "INSERT INTO inventory_inventories (product_id, quantity, version) VALUES (?, ?, ?)",
        productId.getValue(),
        100,
        0L);
  }

  private void ensureCategoryExists(String categoryId, String categoryName) {
    Integer count =
        jdbcTemplate.queryForObject(
            "SELECT count(*) FROM product_categories WHERE id = ?", Integer.class, categoryId);
    if (count != null && count == 0) {
      jdbcTemplate.update(
          "INSERT INTO product_categories (id, name, parent_id) VALUES (?, ?, ?)",
          categoryId,
          categoryName,
          null);
    }
  }

  private void insertProduct(Product product) {
    jdbcTemplate.update(
        "INSERT INTO product_products (id, name, description, price_amount, category_id) VALUES (?, ?, ?, ?, ?)",
        product.getId().getValue(),
        product.getName(),
        product.getDescription(),
        product.getPrice().getAmount(),
        product.getCategoryId().getValue());
  }

  @Transactional
  @かつ("新規会員として登録済みである")
  public void 新規会員として登録済みである() {
    String encodedPassword = passwordEncoder.encode(memberPassword);
    Member member = Member.create("GoldenUser", memberEmail, encodedPassword);
    memberRepository.register(member);
  }

  @かつ("会員としてログインしている")
  public void 会員としてログインしている() throws Exception {
    Map<String, String> loginRequest = new HashMap<>();
    loginRequest.put("email", memberEmail);
    loginRequest.put("password", memberPassword);

    MvcResult result =
        mockMvc
            .perform(
                post("/api/users/login")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk())
            .andReturn();

    session = (MockHttpSession) result.getRequest().getSession();
    assertThat(session).isNotNull();
  }

  @もし("商品 {string} を検索する")
  public void 商品_を検索する(String productName) throws Exception {
    mockMvc
        .perform(get("/api/products").param("keyword", productName).session(session))
        .andExpect(status().isOk());
  }

  @ならば("検索結果に {string} が表示される")
  public void 検索結果に_が表示される(String productName) throws Exception {
    mockMvc
        .perform(get("/api/products").param("keyword", productName).session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value(productName));
  }

  @もし("商品 {string} をカートに入れる")
  public void 商品_をカートに入れる(String productName) throws Exception {
    // 商品IDを検索して取得
    // 本来は検索結果からIDを取得するフローだが、テストコード内で解決する
    // ProductRepositoryからIDを取得
    // P-Smartphone というIDで作った前提
    String productId = "P-" + productName;

    Map<String, Object> cartRequest = new HashMap<>();
    cartRequest.put("productId", productId);
    cartRequest.put("quantity", 1);

    mockMvc
        .perform(
            post("/api/cart/items")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(cartRequest))
                .session(session))
        .andExpect(status().isOk());
  }

  @ならば("カート内に {string} が {int} 点ある")
  public void カート内に_が_点ある(String productName, int quantity) throws Exception {
    mockMvc
        .perform(get("/api/cart").session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items[0].productName").value(productName))
        .andExpect(jsonPath("$.items[0].quantity").value(quantity));
  }

  @もし("カートの内容で注文手続きを行う")
  public void カートの内容で注文手続きを行う() throws Exception {
    mockMvc
        .perform(post("/api/orders").session(session))
        .andExpect(status().isOk()); // Controller returns 200 OK
  }

  @ならば("注文が正常に完了する")
  public void 注文が正常に完了する() {
    // ステータスコードのチェックは前のステップで行ったので、
    // ここでは特に何もしないか、あるいはDBの状態を確認するなど
  }

  @かつ("注文履歴で最新の注文を確認できる")
  public void 注文履歴で最新の注文を確認できる() throws Exception {
    mockMvc
        .perform(get("/api/orders").session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists()); // 少なくとも1件あること
  }
}
