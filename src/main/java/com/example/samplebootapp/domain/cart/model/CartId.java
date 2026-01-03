package com.example.samplebootapp.domain.cart.model;

import com.example.samplebootapp.domain.shared.IdGenerator;
import com.example.samplebootapp.domain.shared.ValueObjectBase;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * カートID値オブジェクト.
 */
public class CartId extends ValueObjectBase<CartId> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private final String value;

    public CartId(@NotNull String value) {
        this.value = value;
    }

    /**
     * 新しいカートIDを生成します.
     *
     * @return 新しいカートID
     */
    public static CartId generate() {
        return new CartId(IdGenerator.generate().toString());
    }

    public String getValue() {
        return value;
    }
}
