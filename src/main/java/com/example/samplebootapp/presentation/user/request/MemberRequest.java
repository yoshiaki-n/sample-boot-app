package com.example.samplebootapp.presentation.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 会員登録リクエスト.
 */
@Schema(description = "会員登録リクエスト")
public class MemberRequest {

    @Schema(description = "氏名", example = "山田 太郎")
    @NotBlank
    @Size(max = 255)
    private String name;

    @Schema(description = "メールアドレス", example = "taro.yamada@example.com")
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;

    @Schema(description = "パスワード", example = "Password123!")
    @NotBlank
    @Size(min = 8, max = 255)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$", message = "パスワードは英数字を含み、8文字以上である必要があります")
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
