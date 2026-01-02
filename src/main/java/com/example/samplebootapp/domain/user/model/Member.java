package com.example.samplebootapp.domain.user.model;

import com.example.samplebootapp.domain.shared.AggregateRootBase;
import com.example.samplebootapp.domain.shared.IdGenerator;

/**
 * 会員集約ルート.
 */
public class Member extends AggregateRootBase<String> {

    private final String name;
    private final String email;
    private final String password;

    /**
     * コンストラクタ.
     *
     * @param id       ID
     * @param name     氏名
     * @param email    メールアドレス
     * @param password パスワード(ハッシュ済み)
     */
    public Member(String id, String name, String email, String password) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * 新しい会員を生成します.
     *
     * @param name     氏名
     * @param email    メールアドレス
     * @param password パスワード(ハッシュ済み)
     * @return 新しい会員インスタンス
     */
    public static Member create(String name, String email, String password) {
        return new Member(IdGenerator.generate().toString(), name, email, password);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
