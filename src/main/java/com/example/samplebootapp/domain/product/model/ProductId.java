package com.example.samplebootapp.domain.product.model;

import com.example.samplebootapp.domain.shared.IdGenerator;
import com.example.samplebootapp.domain.shared.ValueObjectBase;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * 商品ID値オブジェクト.
 */
public class ProductId extends ValueObjectBase<ProductId> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private final String value;

    public ProductId(@NotNull String value) {
        this.value = value;
    }

    /**
     * 新しい商品IDを生成します.
     *
     * @return 新しい商品ID
     */
    public static ProductId generate() {
        return new ProductId(IdGenerator.generate().toString());
    }

    public String getValue() {
        return value;
    }
}
