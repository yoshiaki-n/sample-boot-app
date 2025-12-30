package com.example.samplebootapp.domain.shared;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntityBaseTest {

  // テスト用の具象クラス
  static class TestEntity extends EntityBase<String> {
    private final String otherField;

    TestEntity(String id, String otherField) {
      super(id);
      this.otherField = otherField;
    }
  }

  @Test
  @DisplayName("同じIDを持つエンティティは等価であること")
  void testEquals_SameId() {
    // 準備 (Arrange)
    TestEntity entity1 = new TestEntity("id-1", "valueA");
    TestEntity entity2 = new TestEntity("id-1", "valueB"); // 異なるフィールドを持っていてもIDが同じなら等価

    // 検証 (Assert)
    assertThat(entity1).isEqualTo(entity2);
  }

  @Test
  @DisplayName("異なるIDを持つエンティティは等価でないこと")
  void testEquals_DifferentId() {
    // 準備 (Arrange)
    TestEntity entity1 = new TestEntity("id-1", "valueA");
    TestEntity entity2 = new TestEntity("id-2", "valueA"); // 同じフィールドを持っていてもIDが異なれば非等価

    // 検証 (Assert)
    assertThat(entity1).isNotEqualTo(entity2);
  }

  @Test
  @DisplayName("コンストラクタでIDがnullの場合は例外がスローされること")
  void testConstructor_NullId() {
    // 検証 (Assert)
    assertThatThrownBy(() -> new TestEntity(null, "value"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ID must not be null");
  }

  @Test
  @DisplayName("同じIDを持つエンティティは同じハッシュコードを持つこと")
  void testHashCode_SameId() {
    // 準備 (Arrange)
    TestEntity entity1 = new TestEntity("id-1", "valueA");
    TestEntity entity2 = new TestEntity("id-1", "valueB");

    // 検証 (Assert)
    assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
  }

  @Test
  @DisplayName("toStringが属性値を含む文字列表現を返すこと")
  void testToString() {
    // 準備 (Arrange)
    TestEntity entity = new TestEntity("id-1", "valueA");

    // 実行 (Act)
    String str = entity.toString();

    // 検証 (Assert)
    assertThat(str).contains("TestEntity");
    assertThat(str).contains("id=id-1");
    assertThat(str).contains("otherField=valueA");
  }
}
