package com.example.samplebootapp.application.user.query;

import com.example.samplebootapp.domain.user.model.Member;
import com.example.samplebootapp.domain.user.model.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** ユーザー情報取得クエリサービス. */
@Service
@Transactional(readOnly = true)
public class UserQueryService {

  @SuppressWarnings("PMD.SingularField")
  private final MemberRepository memberRepository;

  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
  public UserQueryService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  /**
   * IDでユーザー情報を取得します.
   *
   * @param id ユーザーID
   * @return ユーザーレスポンス
   */
  public UserDto findById(String id) {
    Member member = memberRepository.findById(id);
    if (member == null) {
      // 本来はNotFoundExceptionなどを投げるべきだが、
      // 認証済みユーザーのIDで検索するため、ここに来ることは通常あり得ない
      return null;
    }
    return new UserDto(member.getId(), member.getName(), member.getEmail());
  }
}
