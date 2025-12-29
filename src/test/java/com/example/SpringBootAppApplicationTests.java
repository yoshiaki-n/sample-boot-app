package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/** アプリケーションコンテキストの読み込みを確認する統合テストです。 */
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class SpringBootAppApplicationTests {

  @Test
  @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
  void contextLoads() {
  }
}
