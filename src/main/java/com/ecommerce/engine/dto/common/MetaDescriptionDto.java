package com.ecommerce.engine.dto.common;

import com.ecommerce.engine.repository.entity.DescriptionSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Locale;

public record MetaDescriptionDto(
        @NotNull Locale locale, @NotBlank String title, String description, String metaTitle, String metaDescription) {
    public MetaDescriptionDto(DescriptionSuperclass descriptionSuperclass) {
        this(
                descriptionSuperclass.getLocale(),
                descriptionSuperclass.getTitle(),
                descriptionSuperclass.getDescription(),
                descriptionSuperclass.getMetaTitle(),
                descriptionSuperclass.getMetaDescription());
    }
}
