package com.example.samplebootapp.domain.order.model;

import java.util.Optional;

/** カートリポジトリ. */
public interface CartRepository {

  /**
   * カートを保存します.
   *
   * @param cart カート
   */
  void save(Cart cart);

  /**
   * ユーザーIDでカートを検索します.
   *
   * @param userId ユーザーID
   * @return カート
   */
  Optional<Cart> findByUserId(String userId);
}
