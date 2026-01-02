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
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private JdbcTemplate jdbcTemplate;
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
    Map<String, Object> memberMap = jdbcTemplate.queryForMap("SELECT * FROM members WHERE email = ?",
        "hanako@example.com");

    assertThat(memberMap).isNotNull();
    assertThat(memberMap.get("name")).isEqualTo("テスト花子");

    // パスワードがハッシュ化されているか検証
    String storedPassword = (String) memberMap.get("password");
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    assertThat(encoder.matches("Password123!", storedPassword)).isTrue();
  }
}
