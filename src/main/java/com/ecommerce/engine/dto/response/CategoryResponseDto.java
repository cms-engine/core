package com.ecommerce.engine.dto.response;

import com.ecommerce.engine.dto.common.DescriptionDto;
import java.time.Instant;
import java.util.Set;

public record CategoryResponseDto(
        long id,
        Long parentId,
        int sortOrder,
        Instant created,
        Instant updated,
        boolean enabled,
        Set<DescriptionDto> descriptions) {}
