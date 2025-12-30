package com.example.samplebootapp.domain.shared;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AggregateRootBaseTest {

  // テスト用の具象クラス
  static class TestAggregateRoot extends AggregateRootBase<String> {
    TestAggregateRoot(String id) {
      super(id);
    }
  }

  @Test
  @DisplayName("コンストラクタでIDを設定でき、getIdで取得できること")
  void testConstructorAndGetId() {
    // 準備 (Arrange)
    String expectedId = "id-123";

    // 実行 (Act)
    TestAggregateRoot aggregate = new TestAggregateRoot(expectedId);

    // 検証 (Assert)
    assertThat(aggregate.getId()).isEqualTo(expectedId);
  }

  @Test
  @DisplayName("等価性判定がIDに基づいて正しく機能すること（EntityBaseの振る舞いを継承していること）")
  void testEquality() {
    // 準備 (Arrange)
    TestAggregateRoot agg1 = new TestAggregateRoot("id-1");
    TestAggregateRoot agg2 = new TestAggregateRoot("id-1");
    TestAggregateRoot agg3 = new TestAggregateRoot("id-2");

    // 検証 (Assert)
    assertThat(agg1).isEqualTo(agg2);
    assertThat(agg1).isNotEqualTo(agg3);
  }

  @Test
  @DisplayName("toStringが正しく生成されること")
  void testToString() {
    // 準備 (Arrange)
    TestAggregateRoot agg = new TestAggregateRoot("id-abc");

    // 実行 (Act)
    String str = agg.toString();

    // 検証 (Assert)
    assertThat(str).contains("TestAggregateRoot");
    assertThat(str).contains("id=id-abc");
  }
}
