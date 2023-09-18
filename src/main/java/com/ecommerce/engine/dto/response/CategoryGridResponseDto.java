package com.ecommerce.engine.dto.response;

import java.time.Instant;

public record CategoryGridResponseDto(
        long id,
        String title,
        Long parentId,
        String parentTitle,
        int sortOrder,
        Instant created,
        Instant updated,
        boolean enabled) {}
