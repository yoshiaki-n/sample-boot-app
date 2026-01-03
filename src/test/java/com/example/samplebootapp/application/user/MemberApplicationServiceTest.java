package com.example.samplebootapp.application.user;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.samplebootapp.domain.user.model.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class MemberApplicationServiceTest {

  @Test
  @DisplayName("会員登録ができること。パスワードがハッシュ化されていること")
  void testRegister() {
    // 準備 (Arrange)
    MemberRepository memberRepository = mock(MemberRepository.class);
    MemberApplicationService sut = new MemberApplicationService(memberRepository);
    String rawPassword = "password123";
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 実行 (Act)
    sut.register("山田太郎", "taro@example.com", rawPassword);

    // 検証 (Assert)
    verify(memberRepository)
        .register(
            argThat(
                member -> {
                  return member.getName().equals("山田太郎")
                      && member.getEmail().equals("taro@example.com")
                      && passwordEncoder.matches(rawPassword, member.getPassword());
                }));
  }
}
