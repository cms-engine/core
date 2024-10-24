package com.ecommerce.engine.admin.dto.grid;

import com.ecommerce.engine.entity.Category;
import java.time.Instant;

public record CategoryGridDto(
        long id,
        String title,
        Long parentId,
        String parentTitle,
        String imageSrc,
        int sortOrder,
        Instant createdAt,
        Instant updatedAt,
        boolean enabled) {

    public CategoryGridDto(Category category) {
        this(
                category.getId(),
                category.getLocaleTitle(),
                category.getParentId(),
                category.getParentLocaleTitle(),
                category.getImageSrc(),
                category.getSortOrder(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.isEnabled());
    }
}
