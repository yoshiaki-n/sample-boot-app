package com.example.samplebootapp.infrastructure.user.repository;

import com.example.samplebootapp.domain.user.model.Member;
import com.example.samplebootapp.domain.user.model.MemberRepository;
import com.example.samplebootapp.infrastructure.user.mapper.MemberMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Repository;

/** 会員リポジトリ実装クラス. */
@Repository
public class MemberRepositoryImpl implements MemberRepository {

  private final MemberMapper memberMapper;

  /**
   * コンストラクタ.
   *
   * @param memberMapper 会員Mapper
   */
  @SuppressFBWarnings("EI_EXPOSE_REP2")
  public MemberRepositoryImpl(MemberMapper memberMapper) {
    this.memberMapper = memberMapper;
  }

  @Override
  public void register(Member member) {
    memberMapper.insert(member);
  }

  @Override
  public Member findByEmail(String email) {
    return memberMapper.findByEmail(email);
  }

  @Override
  public Member findById(String id) {
    return memberMapper.findById(id);
  }

  @Override
  public void update(Member member) {
    memberMapper.update(member);
  }

  @Override
  public void delete(Member member) {
    memberMapper.delete(member);
  }
}
