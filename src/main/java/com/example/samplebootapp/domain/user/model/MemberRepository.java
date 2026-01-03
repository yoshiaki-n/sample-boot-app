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

    /**
     * メールアドレスで会員を検索します.
     *
     * @param email メールアドレス
     * @return 会員（存在しない場合はnull）
     */
    Member findByEmail(String email);

    /**
     * IDで会員を検索します.
     *
     * @param id ID
     * @return 会員（存在しない場合はnull）
     */
    Member findById(String id);

    /**
     * 会員情報を更新します.
     *
     * @param member 会員
     */
    void update(Member member);

    /**
     * 会員を削除します.
     *
     * @param member 会員
     */
    void delete(Member member);
}
