package com.ecommerce.engine._admin.dto.common;

import com.ecommerce.engine.entity.MetaDescriptionSuperclass;
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
            Collection<? extends MetaDescriptionSuperclass> descriptionSuperclasses) {
        return descriptionSuperclasses.stream()
                .filter(descriptionSuperclass ->
                        StoreSettings.storeLocales.containsKey(descriptionSuperclass.getLanguageId()))
                .map(MetaDescriptionDto::new)
                .collect(Collectors.toSet());
    }

    public MetaDescriptionDto(MetaDescriptionSuperclass descriptionSuperclass) {
        this(
                descriptionSuperclass.getLanguageId(),
                descriptionSuperclass.getTitle(),
                descriptionSuperclass.getDescription(),
                descriptionSuperclass.getMetaTitle(),
                descriptionSuperclass.getMetaDescription());
    }
}
