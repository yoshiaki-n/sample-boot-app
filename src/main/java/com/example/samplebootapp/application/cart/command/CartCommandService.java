package com.example.samplebootapp.application.cart.command;

import com.example.samplebootapp.domain.cart.model.Cart;
import com.example.samplebootapp.domain.cart.model.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartCommandService {

    private final CartRepository cartRepository;

    public CartCommandService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * カートに商品を追加します。
     *
     * @param userId    ユーザーID
     * @param productId 商品ID
     * @param quantity  数量
     */
    public void addItem(String userId, String productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> Cart.create(userId));

        cart.addItem(productId, quantity);

        cartRepository.save(cart);
    }
}
