package com.example.samplebootapp.application.order.query;

/** カート参照サービス. */
/** カート参照サービス. */
public interface CartQueryService {

  /**
   * カートを取得します.
   *
   * @param userId ユーザーID
   * @return カートDTO
   */
  CartDto getCart(String userId);
}
