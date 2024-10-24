package com.ecommerce.engine.admin.dto.grid;

import com.ecommerce.engine.entity.Page;
import java.time.Instant;

public record PageGridDto(
        long id, String title, boolean bottom, int sortOrder, Instant createdAt, Instant updatedAt, boolean enabled) {

    public PageGridDto(Page page) {
        this(
                page.getId(),
                page.getLocaleTitle(),
                page.isBottom(),
                page.getSortOrder(),
                page.getCreatedAt(),
                page.getUpdatedAt(),
                page.isEnabled());
    }
}
