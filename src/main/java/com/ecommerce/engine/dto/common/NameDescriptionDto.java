package com.ecommerce.engine.dto.common;

import com.ecommerce.engine.entity.Localable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Locale;

public record NameDescriptionDto(@NotNull Locale locale, @NotBlank @Size(max = 255) String name) {
    public NameDescriptionDto(Localable localable) {
        this(localable.getLocale(), localable.getName());
    }
}
