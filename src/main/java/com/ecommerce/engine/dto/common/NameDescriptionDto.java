package com.ecommerce.engine.dto.common;

import com.ecommerce.engine.repository.entity.Localable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Locale;

public record NameDescriptionDto(@NotNull Locale locale, @NotBlank String name) {
    public NameDescriptionDto(Localable localable) {
        this(localable.getLocale(), localable.getName());
    }
}
