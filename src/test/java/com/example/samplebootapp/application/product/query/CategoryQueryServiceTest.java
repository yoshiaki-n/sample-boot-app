package com.example.samplebootapp.application.product.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.samplebootapp.domain.product.model.Category;
import com.example.samplebootapp.domain.product.model.CategoryId;
import com.example.samplebootapp.domain.product.model.CategoryRepository;
import com.example.samplebootapp.presentation.product.api.CategoryResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryQueryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryQueryService categoryQueryService;

    @Test
    @DisplayName("カテゴリ一覧を階層構造で取得できること")
    void testListCategories() {
        // Arrange
        CategoryId rootId = new CategoryId("root");
        CategoryId childId = new CategoryId("child");
        CategoryId grandChildId = new CategoryId("grandChild");

        Category root = new Category(rootId, "Root", null);
        Category child = new Category(childId, "Child", rootId);
        Category grandChild = new Category(grandChildId, "GrandChild", childId);

        when(categoryRepository.findAll()).thenReturn(List.of(root, child, grandChild));

        // Act
        List<CategoryResponse> result = categoryQueryService.listCategories();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo("root");
        assertThat(result.get(0).getChildren()).hasSize(1);
        assertThat(result.get(0).getChildren().get(0).getId()).isEqualTo("child");
        assertThat(result.get(0).getChildren().get(0).getChildren()).hasSize(1);
        assertThat(result.get(0).getChildren().get(0).getChildren().get(0).getId()).isEqualTo("grandChild");
    }
}
