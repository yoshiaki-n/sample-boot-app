package com.example.samplebootapp.domain.shared;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 値オブジェクト（Value Object）の基底クラス.
 *
 * <p>値オブジェクトは、属性の値によって等価性が判断されるオブジェクトです。 Apache Commons Langを使用して実装を簡略化しています。
 *
 * @param <T> 具体的な値オブジェクトの型
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class ValueObjectBase<T extends ValueObjectBase<T>> {

  /**
   * 等価性を判定します. Apache Commons Langの {@link EqualsBuilder#reflectionEquals} を使用して、
   * リフレクションによりすべてのフィールドを比較します。
   *
   * @param o 比較対象のオブジェクト
   * @return 等しい場合は {@code true}、そうでない場合は {@code false}
   */
  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  /**
   * ハッシュコードを返します. Apache Commons Langの {@link HashCodeBuilder#reflectionHashCode} を使用して、
   * リフレクションによりすべてのフィールドからハッシュコードを生成します。
   *
   * @return ハッシュコード
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  /**
   * 文字列表現を返します. Apache Commons Langの {@link ToStringBuilder#reflectionToString} を使用して、
   * リフレクションによりすべてのフィールドを含む文字列表現を生成します。
   *
   * @return オブジェクトの文字列表現
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
