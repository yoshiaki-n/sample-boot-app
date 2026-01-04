package com.example.samplebootapp.presentation.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/** 会員更新リクエスト. */
@Schema(description = "会員更新リクエスト")
public class MemberUpdateRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "氏名", example = "山田 太郎")
  @NotBlank
  private String name;

  @Schema(description = "メールアドレス", example = "taro.yamada@example.com")
  @NotBlank
  @Email
  private String email;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
