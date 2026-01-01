package com.example.samplebootapp.domain.shared;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;
import java.util.UUID;

/**
 * ID生成クラス。
 *
 * <p>システム共通で使用するユニークIDを生成します。 生成されるIDはUUID v7（Time-ordered Epoch）形式であり、 生成順にソート可能です。
 */
public final class IdGenerator {

  private static final NoArgGenerator GENERATOR = Generators.timeBasedEpochGenerator();

  private IdGenerator() {
    // インスタンス化禁止
  }

  /**
   * 新しいIDを生成します。
   *
   * @return ソート可能なユニークID(UUID v7)
   */
  public static UUID generate() {
    // UUID v7を生成することで、同一ミリ秒内の生成でも順序を保証します
    return GENERATOR.generate();
  }
}
