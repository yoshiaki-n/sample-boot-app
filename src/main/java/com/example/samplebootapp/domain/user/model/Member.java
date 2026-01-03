package com.example.samplebootapp.domain.user.model;

import com.example.samplebootapp.domain.shared.AggregateRootBase;
import com.example.samplebootapp.domain.shared.IdGenerator;
import java.io.Serializable;

/**
 * 会員集約ルート.
 */
public class Member extends AggregateRootBase<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * デフォルトコンストラクタ(Framework/Serialization用).
     */
    protected Member() {
        super("");
    }

    private String name;
    private String email;
    private String password;

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

    /**
     * 会員情報を更新します.
     *
     * @param name  氏名
     * @param email メールアドレス
     */
    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
