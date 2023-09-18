package com.ecommerce.engine.dto.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Locale;

public record DescriptionDto(
        @NotNull Locale locale, @NotBlank String title, String description, String metaTitle, String metaDescription) {}
