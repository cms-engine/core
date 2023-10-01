package com.ecommerce.engine.dto.admin.common;

import com.ecommerce.engine.entity.Localable;
import com.ecommerce.engine.util.StoreSettings;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public record NameDescriptionDto(@NotNull Locale locale, @NotBlank @Size(max = 255) String name) {
    @SuppressWarnings("SpellCheckingInspection")
    public static Set<NameDescriptionDto> createNameDescriptionDtoSet(Collection<? extends Localable> localables) {
        return localables.stream()
                .filter(localable -> StoreSettings.storeLocales.contains(localable.getLocale()))
                .map(NameDescriptionDto::new)
                .collect(Collectors.toSet());
    }

    public NameDescriptionDto(Localable localable) {
        this(localable.getLocale(), localable.getName());
    }
}
