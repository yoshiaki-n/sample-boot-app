package com.example.samplebootapp.domain.cart.model;

import com.example.samplebootapp.domain.shared.AggregateRootBase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.util.Assert;

/**
 * カート集約ルート.
 */
public class Cart extends AggregateRootBase<CartId> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId; // 会員ID。未ログイン時はどうするか要検討だが、今回は会員コンテキスト前提のため必須とする。
    private List<CartItem> items;

    /** デフォルトコンストラクタ(Framework/Serialization用). */
    protected Cart() {
        super(new CartId(""));
        this.userId = "";
        this.items = new ArrayList<>();
    }

    /**
     * コンストラクタ.
     *
     * @param id     カートID
     * @param userId ユーザーID
     * @param items  カート内商品リスト
     */
    public Cart(CartId id, String userId, List<CartItem> items) {
        super(id);
        Assert.hasText(userId, "userId must not be empty");
        this.userId = userId;
        this.items = new ArrayList<>(items != null ? items : Collections.emptyList());
    }

    /**
     * 新しいカートを作成します.
     *
     * @param userId ユーザーID
     * @return 新しいカート
     */
    public static Cart create(String userId) {
        return new Cart(CartId.generate(), userId, new ArrayList<>());
    }

    /**
     * カートに商品を追加します.
     * 既に同じ商品がある場合は数量を加算します.
     *
     * @param productId 商品ID
     * @param quantity  数量
     */
    public void addItem(String productId, int quantity) {
        Assert.hasText(productId, "productId must not be empty");
        Assert.isTrue(quantity > 0, "quantity must be greater than 0");

        Optional<CartItem> existingItem = items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int index = items.indexOf(item);
            items.set(index, item.add(quantity));
        } else {
            items.add(new CartItem(productId, quantity));
        }
    }

    /**
     * カートから商品を削除します.
     *
     * @param productId 商品ID
     */
    public void removeItem(String productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
    }

    /**
     * 商品の数量を変更します.
     *
     * @param productId 商品ID
     * @param quantity  新しい数量
     */
    public void changeQuantity(String productId, int quantity) {
        if (quantity <= 0) {
            removeItem(productId);
            return;
        }

        // 既存の商品を探して更新。なければ何もしない（あるいは例外？）
        // ここでは「あれば更新」とする
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProductId().equals(productId)) {
                items.set(i, new CartItem(productId, quantity));
                return;
            }
        }
    }

    public void clear() {
        items.clear();
    }

    public String getUserId() {
        return userId;
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
