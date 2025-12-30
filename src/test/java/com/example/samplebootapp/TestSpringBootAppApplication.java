package com.example.samplebootapp;

import org.springframework.boot.SpringApplication;

/** テストアプリケーションのエントリーポイントです。 */
@SuppressWarnings("PMD.TestClassWithoutTestCases")
public class TestSpringBootAppApplication {

  /**
   * メインメソッドです。
   *
   * @param args コマンドライン引数
   */
  public static void main(String[] args) {
    SpringApplication.from(SpringBootAppApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }
}
