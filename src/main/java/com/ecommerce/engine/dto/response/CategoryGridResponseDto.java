package com.ecommerce.engine.dto.response;

import java.time.Instant;

public record CategoryGridResponseDto(
        long id,
        Long parentId,
        String parentName,
        int sortOrder,
        Instant created,
        Instant updated,
        boolean enabled,
        String title) {}
