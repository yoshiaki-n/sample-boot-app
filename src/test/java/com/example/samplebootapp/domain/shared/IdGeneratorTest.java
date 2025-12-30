package com.example.samplebootapp.domain.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class IdGeneratorTest {

    @Test
    @DisplayName("IDが生成され、nullでないこと、およびバージョン7であることを確認する")
    void shouldGenerateId() {
        // Arrange
        // (No arrangement needed for static method call)

        // Act
        UUID id = IdGenerator.generate();

        // Assert
        assertThat(id).isNotNull();
        assertThat(id.version()).isEqualTo(7);
    }

    @Test
    @DisplayName("生成されたIDが生成順にソート可能であることを確認する")
    void shouldGenerateSortableIds() {
        // Arrange
        int count = 1000; // 衝突確認のため少し多めに生成
        List<UUID> ids = new ArrayList<>();

        // Act
        for (int i = 0; i < count; i++) {
            ids.add(IdGenerator.generate());
        }

        // Assert
        // リストをコピーしてソート
        List<UUID> sortedIds = new ArrayList<>(ids);
        Collections.sort(sortedIds);

        // 生成順のリストとソート後のリストが一致することを確認（生成順 == 昇順）
        assertThat(ids).isEqualTo(sortedIds);

        // 重複がないことも確認
        assertThat(ids).doesNotHaveDuplicates();
    }
}
