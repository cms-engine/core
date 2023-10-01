package com.ecommerce.engine.dto.admin.common;

import com.ecommerce.engine.entity.DescriptionSuperclass;
import com.ecommerce.engine.util.StoreSettings;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public record MetaDescriptionDto(
        @NotNull Locale locale,
        @NotBlank @Size(max = 255) String title,
        String description,
        @Size(max = 255) String metaTitle,
        @Size(max = 255) String metaDescription) {
    public static Set<MetaDescriptionDto> createMetaDescriptionDtoSet(
            Collection<? extends DescriptionSuperclass> descriptionSuperclasses) {
        return descriptionSuperclasses.stream()
                .filter(meta -> StoreSettings.storeLocales.contains(meta.getLocale()))
                .map(MetaDescriptionDto::new)
                .collect(Collectors.toSet());
    }

    public MetaDescriptionDto(DescriptionSuperclass descriptionSuperclass) {
        this(
                descriptionSuperclass.getLocale(),
                descriptionSuperclass.getTitle(),
                descriptionSuperclass.getDescription(),
                descriptionSuperclass.getMetaTitle(),
                descriptionSuperclass.getMetaDescription());
    }
}
