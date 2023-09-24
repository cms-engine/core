package com.ecommerce.engine.dto.admin.common;

import com.ecommerce.engine.entity.DescriptionSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Locale;

public record MetaDescriptionDto(
        @NotNull Locale locale,
        @NotBlank @Size(max = 255) String title,
        String description,
        @Size(max = 255) String metaTitle,
        @Size(max = 255) String metaDescription) {
    public MetaDescriptionDto(DescriptionSuperclass descriptionSuperclass) {
        this(
                descriptionSuperclass.getLocale(),
                descriptionSuperclass.getTitle(),
                descriptionSuperclass.getDescription(),
                descriptionSuperclass.getMetaTitle(),
                descriptionSuperclass.getMetaDescription());
    }
}
