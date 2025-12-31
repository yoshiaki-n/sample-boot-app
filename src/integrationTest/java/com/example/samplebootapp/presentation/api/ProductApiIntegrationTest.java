package com.example.samplebootapp.presentation.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductApiIntegrationTest {

  @Test
  @DisplayName("商品一覧取得APIの統合テスト: 初期データ投入確認 (アサーションなし)")
  @Sql(scripts = { "/db/testdata/delete_products.sql",
      "/db/testdata/insert_products.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/db/testdata/delete_products.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  void testGetProducts_setupOnly() {
    // 現在はデータのセットアップと実行確認のみ
  }
}
