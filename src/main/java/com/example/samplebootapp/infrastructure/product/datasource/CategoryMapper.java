package com.example.samplebootapp.infrastructure.product.datasource;

import com.example.samplebootapp.domain.product.model.Category;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/** カテゴリMapper. */
@Mapper
public interface CategoryMapper {
    /**
     * 全カテゴリを取得します.
     *
     * @return カテゴリリスト
     */
    List<Category> findAll();
}
