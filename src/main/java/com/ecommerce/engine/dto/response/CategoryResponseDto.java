package com.ecommerce.engine.dto.response;

import com.ecommerce.engine.dto.common.DescriptionDto;
import com.ecommerce.engine.repository.entity.Category;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record CategoryResponseDto(
        long id,
        Long parentId,
        UUID imageId,
        String imageSrc,
        int sortOrder,
        Instant created,
        Instant updated,
        boolean enabled,
        Set<DescriptionDto> descriptions) {
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
                category.getDescriptions().stream().map(DescriptionDto::new).collect(Collectors.toSet()));
    }
}
