package com.example.samplebootapp.domain.shared;

/**
 * 集約ルート（Aggregate Root）の基底クラス.
 *
 * <p>集約のルートとなるエンティティを表します。 外部のオブジェクトは、このルートエンティティを通じてのみ、集約内部のオブジェクトを操作できます。
 *
 * @param <ID> 識別子の型
 */
@SuppressWarnings({"checkstyle:SummaryJavadoc", "checkstyle:ClassTypeParameterName"})
public abstract class AggregateRootBase<ID> extends EntityBase<ID> {

  /**
   * コンストラクタ.
   *
   * @param id 識別子
   */
  protected AggregateRootBase(ID id) {
    super(id);
  }
}
