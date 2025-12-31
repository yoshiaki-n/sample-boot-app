package com.example.samplebootapp.domain.product.model;

import com.example.samplebootapp.domain.shared.ValueObjectBase;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 価格値オブジェクト.
 */
public class Price extends ValueObjectBase<Price> implements Serializable {

    @NotNull
    @Min(0)
    private final BigDecimal amount;

    public Price(@NotNull BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("価格は0以上である必要があります");
        }
        this.amount = amount;
    }

    public static Price of(long amount) {
        return new Price(BigDecimal.valueOf(amount));
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
