package com.ecommerce.engine.admin.dto.response;

import com.ecommerce.engine.admin.dto.common.MetaDescriptionDto;
import com.ecommerce.engine.entity.Page;
import java.time.Instant;
import java.util.Set;

public record PageResponseDto(
        long id,
        boolean bottom,
        int sortOrder,
        Instant createdAt,
        Instant updatedAt,
        boolean enabled,
        Set<MetaDescriptionDto> descriptions) {
    public PageResponseDto(Page page) {
        this(
                page.getId(),
                page.isBottom(),
                page.getSortOrder(),
                page.getCreatedAt(),
                page.getUpdatedAt(),
                page.isEnabled(),
                MetaDescriptionDto.createMetaDescriptionDtoSet(page.getDescriptions()));
    }
}
