package com.ecommerce.engine.dto.admin.common;

import com.ecommerce.engine.entity.HasLocale;
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

    public static Set<NameDescriptionDto> createNameDescriptionDtoSet(Collection<? extends HasLocale> hasLocales) {
        return hasLocales.stream()
                .filter(hasLocale -> StoreSettings.storeLocales.containsKey(hasLocale.getLanguageId()))
                .map(NameDescriptionDto::new)
                .collect(Collectors.toSet());
    }

    public NameDescriptionDto(HasLocale hasLocale) {
        this(hasLocale.getLanguageId(), hasLocale.getName());
    }
}
