package com.example.samplebootapp.application.user;

import com.example.samplebootapp.domain.user.model.Member;
import com.example.samplebootapp.domain.user.model.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会員アプリケーションサービス.
 *
 * <p>
 * 会員に関するユースケース（登録・更新・退会）を実装します。
 */
@Service
public class MemberApplicationService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * コンストラクタ.
   *
   * @param memberRepository 会員リポジトリ
   */
  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
  public MemberApplicationService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
    // SecurityConfigを作成しないため、ここで直接インスタンス化して使用する
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  /**
   * 会員登録を行います.
   *
   * @param name        氏名
   * @param email       メールアドレス
   * @param rawPassword パスワード（平文）
   */
  @Transactional
  public void register(String name, String email, String rawPassword) {
    // パスワードをハッシュ化
    String encodedPassword = passwordEncoder.encode(rawPassword);

    // 会員エンティティ生成
    Member member = Member.create(name, email, encodedPassword);

    // リポジトリに保存
    memberRepository.register(member);
  }

  /**
   * 会員情報を更新します.
   *
   * @param id    会員ID
   * @param name  氏名
   * @param email メールアドレス
   */
  @Transactional
  public void update(String id, String name, String email) {
    Member member = memberRepository.findById(id);
    if (member == null) {
      throw new IllegalArgumentException("会員が見つかりません: " + id);
    }
    member.update(name, email);
    memberRepository.update(member);
  }

  /**
   * 会員を退会します.
   *
   * @param id 会員ID
   */
  @Transactional
  public void withdraw(String id) {
    Member member = memberRepository.findById(id);
    if (member == null) {
      throw new IllegalArgumentException("会員が見つかりません: " + id);
    }
    memberRepository.delete(member);
  }
}
