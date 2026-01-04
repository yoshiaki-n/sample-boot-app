package com.example.samplebootapp.application.user.query;

/** ユーザーDTO. アプリケーション層から返却されるユーザー情報です。 */
public class UserDto {
  private final String id;
  private final String name;
  private final String email;

  public UserDto(String id, String name, String email) {
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
