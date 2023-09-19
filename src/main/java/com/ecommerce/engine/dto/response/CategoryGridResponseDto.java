package com.ecommerce.engine.dto.response;

import com.ecommerce.engine.repository.entity.Category;
import java.time.Instant;

public record CategoryGridResponseDto(
        long id,
        String title,
        Long parentId,
        String parentTitle,
        int sortOrder,
        Instant created,
        Instant updated,
        boolean enabled) {

    public CategoryGridResponseDto(Category category) {
        this(
                category.getId(),
                category.getLocaleTitle(),
                category.getParentId(),
                category.getParentLocaleTitle(),
                category.getSortOrder(),
                category.getCreated(),
                category.getUpdated(),
                category.isEnabled());
    }
}
