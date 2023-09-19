package com.ecommerce.engine.dto.common;

import com.ecommerce.engine.repository.entity.CategoryDescription;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Locale;

public record DescriptionDto(
        @NotNull Locale locale, @NotBlank String title, String description, String metaTitle, String metaDescription) {
    public DescriptionDto(CategoryDescription categoryDescription) {
        this(
                categoryDescription.getLocale(),
                categoryDescription.getTitle(),
                categoryDescription.getDescription(),
                categoryDescription.getMetaTitle(),
                categoryDescription.getMetaDescription());
    }
}
