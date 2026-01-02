package com.example.samplebootapp.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** OpenAPI設定クラス. */
@Configuration
public class OpenApiConfig {

  /**
   * OpenAPI定義を生成します.
   *
   * @return OpenAPI
   */
  @Bean
  public OpenAPI openApi() {
    return new OpenAPI()
        .info(
            new Info()
                .title("サンプル ブート アプリケーション API")
                .description("サンプルアプリケーションのAPI仕様書です。")
                .version("v0.0.1"));
  }
}
