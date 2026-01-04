package com.example.samplebootapp.presentation.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/** ユーザー情報レスポンス. */
public class UserResponse implements Serializable {

  @Schema(description = "ユーザーID", example = "user-001")
  private final String id;

  @Schema(description = "氏名", example = "山田 太郎")
  private final String name;

  @Schema(description = "メールアドレス", example = "taro.yamada@example.com")
  private final String email;

  /**
   * コンストラクタ.
   *
   * @param id ID
   * @param name 氏名
   * @param email メールアドレス
   */
  public UserResponse(String id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }
}
