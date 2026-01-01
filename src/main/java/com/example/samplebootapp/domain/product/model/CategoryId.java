package com.example.samplebootapp.domain.product.model;

import com.example.samplebootapp.domain.shared.IdGenerator;
import com.example.samplebootapp.domain.shared.ValueObjectBase;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/** カテゴリID値オブジェクト. */
public class CategoryId extends ValueObjectBase<CategoryId> implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull private final String value;

  public CategoryId(@NotNull String value) {
    this.value = value;
  }

  /**
   * 新しいカテゴリIDを生成します.
   *
   * @return 新しいカテゴリID
   */
  public static CategoryId generate() {
    return new CategoryId(IdGenerator.generate().toString());
  }

  public String getValue() {
    return value;
  }
}
