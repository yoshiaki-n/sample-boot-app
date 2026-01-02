package com.example.samplebootapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * セキュリティ設定クラス.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * セキュリティフィルタチェーン定義.
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 例外
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF対策は一旦無効化（API動作優先）
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 会員登録APIは誰でもアクセス可能
                        .requestMatchers("/api/users/").permitAll()
                        // 商品APIも既存テストのため許可
                        .requestMatchers("/api/products/**").permitAll()
                        // その他は認証が必要
                        .anyRequest().authenticated());
        return http.build();
    }
}
