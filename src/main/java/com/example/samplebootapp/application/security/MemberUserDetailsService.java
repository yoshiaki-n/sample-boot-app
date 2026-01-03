package com.example.samplebootapp.application.security;

import com.example.samplebootapp.domain.user.model.Member;
import com.example.samplebootapp.domain.user.model.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** 会員UserDetailsService実装. */
@Service
public class MemberUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  /**
   * コンストラクタ.
   *
   * @param memberRepository 会員リポジトリ
   */
  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
  public MemberUserDetailsService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Member member = memberRepository.findByEmail(email);
    if (member == null) {
      throw new UsernameNotFoundException("会員が見つかりません: " + email);
    }
    return new MemberUserDetails(member);
  }
}
