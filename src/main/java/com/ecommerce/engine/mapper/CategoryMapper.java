package com.ecommerce.engine.mapper;

import com.ecommerce.engine.dto.request.CategoryRequestDto;
import com.ecommerce.engine.dto.response.CategoryResponseDto;
import com.ecommerce.engine.repository.entity.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface CategoryMapper {

    @Mapping(target = "parentId", source = "parent.id")
    CategoryResponseDto toDto(Category category);

    @Mapping(target = "parent.id", source = "parentId")
    Category toEntity(CategoryRequestDto categoryRequestDto);

    @AfterMapping
    default void nullify(@MappingTarget Category category) {
        if (category.getParent() != null && category.getParent().getId() == null) {
            category.setParent(null);
        }

        category.getDescriptions().forEach(categoryDescription -> categoryDescription.setCategory(category));
    }
}
