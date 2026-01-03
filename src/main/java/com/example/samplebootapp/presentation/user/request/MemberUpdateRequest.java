package com.example.samplebootapp.presentation.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 会員更新リクエスト.
 */
public class MemberUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
