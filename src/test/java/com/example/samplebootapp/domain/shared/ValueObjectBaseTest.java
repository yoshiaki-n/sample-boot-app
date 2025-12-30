package com.example.samplebootapp.domain.shared;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValueObjectBaseTest {

  // テスト用の具象クラス
  static class TestValueObject extends ValueObjectBase<TestValueObject> {
    private final String value1;
    private final int value2;

    TestValueObject(String value1, int value2) {
      this.value1 = value1;
      this.value2 = value2;
    }
  }

  @Test
  @DisplayName("同じ属性値を持つオブジェクトは等価であること")
  void testEquals_SameAttributes() {
    // 準備 (Arrange)
    TestValueObject obj1 = new TestValueObject("test", 1);
    TestValueObject obj2 = new TestValueObject("test", 1);

    // 検証 (Assert)
    assertThat(obj1).isEqualTo(obj2);
  }

  @Test
  @DisplayName("異なる属性値を持つオブジェクトは等価でないこと")
  void testEquals_DifferentAttributes() {
    // 準備 (Arrange)
    TestValueObject obj1 = new TestValueObject("test", 1);
    TestValueObject obj2 = new TestValueObject("test", 2);
    TestValueObject obj3 = new TestValueObject("other", 1);

    // 検証 (Assert)
    assertThat(obj1).isNotEqualTo(obj2);
    assertThat(obj1).isNotEqualTo(obj3);
  }

  @Test
  @DisplayName("同じ属性値を持つオブジェクトは同じハッシュコードを持つこと")
  void testHashCode_SameAttributes() {
    // 準備 (Arrange)
    TestValueObject obj1 = new TestValueObject("test", 1);
    TestValueObject obj2 = new TestValueObject("test", 1);

    // 検証 (Assert)
    assertThat(obj1.hashCode()).isEqualTo(obj2.hashCode());
  }

  @Test
  @DisplayName("異なる属性値を持つオブジェクトは異なるハッシュコードを持つ可能性があること")
  void testHashCode_DifferentAttributes() {
    // 準備 (Arrange)
    TestValueObject obj1 = new TestValueObject("test", 1);
    TestValueObject obj2 = new TestValueObject("test", 2);

    // 検証 (Assert)
    // ハッシュ衝突の可能性はあるが、通常は異なる
    assertThat(obj1.hashCode()).isNotEqualTo(obj2.hashCode());
  }

  @Test
  @DisplayName("自分自身との比較は等価であること")
  void testEquals_Self() {
    // 準備 (Arrange)
    TestValueObject obj1 = new TestValueObject("test", 1);

    // 検証 (Assert)
    assertThat(obj1).isEqualTo(obj1);
  }

  @Test
  @DisplayName("nullとの比較は等価でないこと")
  void testEquals_Null() {
    // 準備 (Arrange)
    TestValueObject obj1 = new TestValueObject("test", 1);

    // 検証 (Assert)
    assertThat(obj1).isNotEqualTo(null);
  }

  @Test
  @DisplayName("異なるクラスとの比較は等価でないこと")
  void testEquals_DifferentClass() {
    // 準備 (Arrange)
    TestValueObject obj1 = new TestValueObject("test", 1);

    // 検証 (Assert)
    assertThat(obj1).isNotEqualTo("test");
  }

  @Test
  @DisplayName("toStringが属性値を含む文字列表現を返すこと")
  void testToString() {
    // 準備 (Arrange)
    TestValueObject obj = new TestValueObject("testString", 123);

    // 実行 (Act)
    String str = obj.toString();

    // 検証 (Assert)
    assertThat(str).contains("TestValueObject");
    assertThat(str).contains("value1=testString");
    assertThat(str).contains("value2=123");
  }
}
