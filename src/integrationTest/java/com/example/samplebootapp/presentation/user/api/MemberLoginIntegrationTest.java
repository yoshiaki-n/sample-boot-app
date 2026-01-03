package com.example.samplebootapp.presentation.user.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.samplebootapp.domain.user.model.Member;
import com.example.samplebootapp.domain.user.model.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberLoginIntegrationTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("ログインAPI: 正しいパスワードでログイン成功し、JSESSIONIDクッキーが返される")
  void testLoginSuccess() throws Exception {
    // 準備
    String email = "login_test@example.com";
    String password = "Password123!";
    String encodedPassword = passwordEncoder.encode(password);

    Member member = Member.create("LoginUser", email, encodedPassword);
    memberRepository.register(member);

    Map<String, String> loginRequest = new HashMap<>();
    loginRequest.put("email", email);
    loginRequest.put("password", password);

    // 実行 & 検証
    mockMvc.perform(post("/api/users/login")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Login Successful"))
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.request()
            .sessionAttribute("SPRING_SECURITY_CONTEXT", org.hamcrest.Matchers.notNullValue()));
  }

  @Test
  @DisplayName("ログインAPI: 誤ったパスワードでログイン失敗する")
  void testLoginFailure() throws Exception {
    // 準備
    String email = "fail_login@example.com";
    String password = "Password123!";
    String encodedPassword = passwordEncoder.encode(password);

    Member member = Member.create("FailUser", email, encodedPassword);
    memberRepository.register(member);

    Map<String, String> loginRequest = new HashMap<>();
    loginRequest.put("email", email);
    loginRequest.put("password", "WrongPassword");

    // 実行 & 検証
    mockMvc.perform(post("/api/users/login")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.message").value("Login Failed"))
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.request()
            .sessionAttribute("SPRING_SECURITY_CONTEXT", org.hamcrest.Matchers.nullValue()));
  }
}
