package com.example.samplebootapp.application.order.query;

import com.example.samplebootapp.application.order.query.dto.OrderDto;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 注文参照サービス. */
@Service
@Transactional(readOnly = true)
public class OrderQueryService {

  private final OrderQueryRepository orderQueryRepository;

  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
  public OrderQueryService(OrderQueryRepository orderQueryRepository) {
    this.orderQueryRepository = orderQueryRepository;
  }

  /**
   * ユーザーの注文履歴を取得します.
   *
   * @param userId ユーザーID
   * @return 注文履歴リスト
   */
  public List<OrderDto> getOrders(String userId) {
    return orderQueryRepository.findByUserId(userId);
  }

  /**
   * 指定された注文IDの注文詳細を取得します.
   *
   * @param orderId 注文ID
   * @param userId ユーザーID
   * @return 注文詳細 (存在しない場合は空)
   */
  public Optional<OrderDto> getOrder(String orderId, String userId) {
    return orderQueryRepository.findByIdAndUserId(orderId, userId);
  }
}
