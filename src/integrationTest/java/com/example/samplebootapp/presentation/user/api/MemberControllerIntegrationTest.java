package com.example.samplebootapp.presentation.user.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.samplebootapp.presentation.user.request.MemberRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private JdbcTemplate jdbcTemplate;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("会員登録API: 正常に登録でき、DBに保存されること")
  void testRegister() throws Exception {
    // 準備
    MemberRequest request = new MemberRequest();
    request.setName("テスト花子");
    request.setEmail("hanako@example.com");
    request.setPassword("Password123!");

    // 実行
    mockMvc
        .perform(
            post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());

    // 検証
    Map<String, Object> memberMap =
        jdbcTemplate.queryForMap(
            "SELECT * FROM user_members WHERE email = ?", "hanako@example.com");

    assertThat(memberMap).isNotNull();
    assertThat(memberMap.get("name")).isEqualTo("テスト花子");

    // パスワードがハッシュ化されているか検証
    String storedPassword = (String) memberMap.get("password");
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    assertThat(encoder.matches("Password123!", storedPassword)).isTrue();
  }

  @Test
  @DisplayName("会員情報取得API: ログイン後に自分の情報を取得できること")
  void testLoginAndGetMe() throws Exception {
    // 準備: ユーザーを登録
    MemberRequest request = new MemberRequest();
    request.setName("テスト太郎");
    request.setEmail("taro@example.com");
    request.setPassword("Password123!");

    mockMvc
        .perform(
            post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());

    // ログイン
    Map<String, String> loginMap = Map.of("email", "taro@example.com", "password", "Password123!");
    var loginResult =
        mockMvc
            .perform(
                post("/api/users/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginMap)))
            .andExpect(status().isOk())
            .andReturn();

    // セッションを取得
    var session = loginResult.getRequest().getSession();

    // 実行: 自分の情報を取得
    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/users/me")
                .session((org.springframework.mock.web.MockHttpSession) session))
        .andExpect(status().isOk())
        .andExpect(
            org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.name")
                .value("テスト太郎"))
        .andExpect(
            org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.email")
                .value("taro@example.com"))
        .andExpect(
            org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.id")
                .exists());
  }
}
