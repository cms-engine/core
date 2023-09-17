package com.ecommerce.engine.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Locale;
import java.util.Set;

public record CategoryRequestDto(
        Long parentId, int sortOrder, boolean enabled, @Size(min = 1) Set<@Valid CategoryDescription> descriptions) {
    public record CategoryDescription(
            @NotNull Locale locale,
            @NotBlank String title,
            String description,
            String metaTitle,
            String metaDescription) {}
}
