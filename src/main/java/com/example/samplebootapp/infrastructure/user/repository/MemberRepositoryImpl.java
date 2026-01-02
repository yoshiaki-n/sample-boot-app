package com.example.samplebootapp.infrastructure.user.repository;

import com.example.samplebootapp.domain.user.model.Member;
import com.example.samplebootapp.domain.user.model.MemberRepository;
import com.example.samplebootapp.infrastructure.user.mapper.MemberMapper;
import org.springframework.stereotype.Repository;

/**
 * 会員リポジトリ実装クラス.
 */
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberMapper memberMapper;

    /**
     * コンストラクタ.
     *
     * @param memberMapper 会員Mapper
     */
    public MemberRepositoryImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public void register(Member member) {
        memberMapper.insert(member);
    }
}
