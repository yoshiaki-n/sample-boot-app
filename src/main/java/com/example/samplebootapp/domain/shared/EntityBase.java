package com.example.samplebootapp.domain.shared;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * エンティティ（Entity）の基底クラス.
 *
 * <p>エンティティは、一意の識別子（ID）によって同一性が判断されるオブジェクトです。
 *
 * @param <ID> 識別子の型
 */
@SuppressWarnings({
  "PMD.AbstractClassWithoutAbstractMethod",
  "checkstyle:SummaryJavadoc",
  "checkstyle:ClassTypeParameterName"
})
@SuppressFBWarnings("CT_CONSTRUCTOR_THROW")
public abstract class EntityBase<ID> implements Serializable {

  private static final long serialVersionUID = 1L;

  private final ID id;

  /**
   * コンストラクタ.
   *
   * @param id 識別子
   * @throws IllegalArgumentException idがnullの場合
   */
  protected EntityBase(ID id) {
    if (id == null) {
      throw new IllegalArgumentException("ID must not be null");
    }
    this.id = id;
  }

  /**
   * 識別子を取得します.
   *
   * @return 識別子
   */
  public ID getId() {
    return id;
  }

  /**
   * 等価性を判定します. エンティティ同士の比較は、識別子の等価性によって行われます。
   *
   * @param o 比較対象のオブジェクト
   * @return IDが等しい場合は {@code true}、そうでない場合は {@code false}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntityBase<?> entity = (EntityBase<?>) o;
    return Objects.equals(id, entity.id);
  }

  /**
   * ハッシュコードを返します.
   *
   * @return IDに基づくハッシュコード
   */
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  /**
   * 文字列表現を返します. Apache Commons Langの {@link ToStringBuilder#reflectionToString} を使用して生成します。
   *
   * @return オブジェクトの文字列表現
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
