package com.ecommerce.engine.dto.response;

import com.ecommerce.engine.dto.common.MetaDescriptionDto;
import com.ecommerce.engine.repository.entity.Page;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public record PageResponseDto(
        long id,
        boolean bottom,
        int sortOrder,
        Instant created,
        Instant updated,
        boolean enabled,
        Set<MetaDescriptionDto> descriptions) {
    public PageResponseDto(Page page) {
        this(
                page.getId(),
                page.isBottom(),
                page.getSortOrder(),
                page.getCreated(),
                page.getUpdated(),
                page.isEnabled(),
                page.getDescriptions().stream().map(MetaDescriptionDto::new).collect(Collectors.toSet()));
    }
}
