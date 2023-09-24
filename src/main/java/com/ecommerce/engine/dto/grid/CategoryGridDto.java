package com.ecommerce.engine.dto.grid;

import com.ecommerce.engine.entity.Category;
import java.time.Instant;

public record CategoryGridDto(
        long id,
        String title,
        Long parentId,
        String parentTitle,
        String imageSrc,
        int sortOrder,
        Instant created,
        Instant updated,
        boolean enabled) {

    public CategoryGridDto(Category category) {
        this(
                category.getId(),
                category.getLocaleTitle(),
                category.getParentId(),
                category.getParentLocaleTitle(),
                category.getImageSrc(),
                category.getSortOrder(),
                category.getCreated(),
                category.getUpdated(),
                category.isEnabled());
    }
}
