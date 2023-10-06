package com.ecommerce.engine.dto.admin.common;

import com.ecommerce.engine.entity.DescriptionSuperclass;
import com.ecommerce.engine.util.StoreSettings;
import com.ecommerce.engine.validation.EntityPresence;
import com.ecommerce.engine.validation.EntityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record MetaDescriptionDto(
        @EntityPresence(EntityType.LANGUAGE) int languageId,
        @NotBlank @Size(max = 255) String title,
        String description,
        @Size(max = 255) String metaTitle,
        @Size(max = 255) String metaDescription) {
    public static Set<MetaDescriptionDto> createMetaDescriptionDtoSet(
            Collection<? extends DescriptionSuperclass> descriptionSuperclasses) {
        return descriptionSuperclasses.stream()
                .filter(meta -> StoreSettings.storeLocales.containsKey(meta.getLanguageId()))
                .map(MetaDescriptionDto::new)
                .collect(Collectors.toSet());
    }

    public MetaDescriptionDto(DescriptionSuperclass descriptionSuperclass) {
        this(
                descriptionSuperclass.getLanguageId(),
                descriptionSuperclass.getTitle(),
                descriptionSuperclass.getDescription(),
                descriptionSuperclass.getMetaTitle(),
                descriptionSuperclass.getMetaDescription());
    }
}
