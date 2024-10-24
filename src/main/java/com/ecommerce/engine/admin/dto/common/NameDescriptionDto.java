package com.ecommerce.engine.admin.dto.common;

import com.ecommerce.engine.entity.NameDescriptionSuperclass;
import com.ecommerce.engine.util.StoreSettings;
import com.ecommerce.engine.validation.EntityPresence;
import com.ecommerce.engine.validation.EntityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record NameDescriptionDto(
        @EntityPresence(EntityType.LANGUAGE) int languageId, @NotBlank @Size(max = 255) String name) {

    public static Set<NameDescriptionDto> createNameDescriptionDtoSet(
            Collection<? extends NameDescriptionSuperclass> descriptionSuperclasses) {
        return descriptionSuperclasses.stream()
                .filter(descriptionSuperclass ->
                        StoreSettings.storeLocales.containsKey(descriptionSuperclass.getLanguageId()))
                .map(NameDescriptionDto::new)
                .collect(Collectors.toSet());
    }

    public NameDescriptionDto(NameDescriptionSuperclass descriptionSuperclass) {
        this(descriptionSuperclass.getLanguageId(), descriptionSuperclass.getName());
    }
}
