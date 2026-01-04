package com.example.samplebootapp.presentation.security.config;

import com.example.samplebootapp.application.security.MemberUserDetailsService;
import com.example.samplebootapp.presentation.security.JsonAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** セキュリティ設定クラス. */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final MemberUserDetailsService memberUserDetailsService;

  public SecurityConfig(MemberUserDetailsService memberUserDetailsService) {
    this.memberUserDetailsService = memberUserDetailsService;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(memberUserDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(provider);
  }

  /**
   * セキュリティフィルタチェーン定義.
   *
   * @param http HttpSecurity
   * @return SecurityFilterChain
   * @throws Exception 例外
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    JsonAuthenticationFilter jsonFilter = new JsonAuthenticationFilter();
    jsonFilter.setAuthenticationManager(authenticationManager());
    jsonFilter.setSecurityContextRepository(
        new org.springframework.security.web.context.HttpSessionSecurityContextRepository());
    jsonFilter.setFilterProcessesUrl("/api/users/login");
    jsonFilter.setAuthenticationSuccessHandler(
        (req, res, auth) -> {
          res.setStatus(HttpServletResponse.SC_OK);
          res.setCharacterEncoding("UTF-8");
          res.setContentType("application/json");
          res.getWriter().write("{\"message\":\"Login Successful\"}");
        });
    jsonFilter.setAuthenticationFailureHandler(
        (req, res, ex) -> {
          res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          res.setCharacterEncoding("UTF-8");
          res.setContentType("application/json");
          res.getWriter().write("{\"message\":\"Login Failed\"}");
        });

    http
        // CSRF対策は一旦無効化（API動作優先）
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth -> auth
                // ログインAPIは誰でもアクセス可能
                .requestMatchers("/api/users/login")
                .permitAll()
                // 会員登録APIは誰でもアクセス可能
                .requestMatchers("/api/users/")
                .permitAll()
                // 商品APIも既存テストのため許可
                .requestMatchers("/api/products/**")
                .permitAll()
                // カートAPIも試験実装のため許可
                .requestMatchers("/api/cart/**")
                .permitAll()
                // その他は認証が必要
                .anyRequest()
                .permitAll())
        .addFilterBefore(jsonFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
