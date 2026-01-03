package com.example.samplebootapp.presentation.user.response;

/**
 * ユーザー情報レスポンス.
 */
public class UserResponse {

    private final String id;
    private final String name;
    private final String email;

    /**
     * コンストラクタ.
     *
     * @param id    ID
     * @param name  氏名
     * @param email メールアドレス
     */
    public UserResponse(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
