package com.example.samplebootapp.application.order.query;

import com.example.samplebootapp.application.order.query.dto.OrderDto;
import java.util.List;
import java.util.Optional;

/** 注文参照リポジトリ. */
public interface OrderQueryRepository {

  /**
   * ユーザーの注文履歴を取得します.
   *
   * @param userId ユーザーID
   * @return 注文履歴リスト
   */
  List<OrderDto> findByUserId(String userId);

  /**
   * 指定された注文IDの注文詳細を取得します.
   *
   * @param orderId 注文ID
   * @param userId ユーザーID
   * @return 注文詳細 (存在しない場合は空)
   */
  Optional<OrderDto> findByIdAndUserId(String orderId, String userId);
}
