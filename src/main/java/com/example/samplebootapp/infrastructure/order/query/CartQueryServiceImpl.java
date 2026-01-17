package com.example.samplebootapp.infrastructure.order.query;

import com.example.samplebootapp.application.order.query.CartDto;
import com.example.samplebootapp.application.order.query.CartItemDto;
import com.example.samplebootapp.application.order.query.CartQueryService;
import com.example.samplebootapp.infrastructure.order.mapper.CartMapper;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** カート参照サービス実装クラス. */
@Service
@Transactional(readOnly = true)
public class CartQueryServiceImpl implements CartQueryService {

    private final CartMapper cartMapper;

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
    public CartQueryServiceImpl(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    /**
     * カートを取得します.
     *
     * @param userId ユーザーID
     * @return カートDTO
     */
    @Override
    public CartDto getCart(String userId) {
        String cartId = cartMapper.findCartIdByUserId(userId);

        if (cartId != null) {
            List<CartItemDto> items = cartMapper.findCartItemDtos(cartId);
            return new CartDto(cartId, items);
        } else {
            // カートが存在しない場合は空のレスポンスを返す
            return new CartDto("", Collections.emptyList());
        }
    }
}
