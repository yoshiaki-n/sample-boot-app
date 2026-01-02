package com.example.samplebootapp.domain.user.model;

/**
 * 会員リポジトリ.
 */
public interface MemberRepository {

    /**
     * 会員登録.
     *
     * @param member 会員
     */
    void register(Member member);
}
