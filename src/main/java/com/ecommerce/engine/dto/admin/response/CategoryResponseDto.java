package com.ecommerce.engine.dto.admin.response;

import com.ecommerce.engine.dto.admin.common.MetaDescriptionDto;
import com.ecommerce.engine.entity.Category;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record CategoryResponseDto(
        long id,
        Long parentId,
        UUID imageId,
        String imageSrc,
        int sortOrder,
        Instant created,
        Instant updated,
        boolean enabled,
        Set<MetaDescriptionDto> descriptions) {
    public CategoryResponseDto(Category category) {
        this(
                category.getId(),
                category.getParentId(),
                category.getImageId(),
                category.getImageSrc(),
                category.getSortOrder(),
                category.getCreated(),
                category.getUpdated(),
                category.isEnabled(),
                MetaDescriptionDto.createMetaDescriptionDtoSet(category.getDescriptions()));
    }
}
