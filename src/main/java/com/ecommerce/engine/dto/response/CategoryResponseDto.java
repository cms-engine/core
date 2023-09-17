package com.ecommerce.engine.dto.response;

import java.time.Instant;
import java.util.Locale;
import java.util.Set;

public record CategoryResponseDto(
        long id,
        Long parentId,
        int sortOrder,
        Instant created,
        Instant updated,
        boolean enabled,
        Set<CategoryDescription> descriptions) {
    public record CategoryDescription(
            Locale locale, String title, String description, String metaTitle, String metaDescription) {}
}
