package com.ecommerce.engine._admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Locale;

public record LanguageRequestDto(
        @NotBlank @Size(max = 255) String name,
        @NotNull Locale hreflang,
        @Pattern(regexp = "[a-z]{2}([_-][a-z]{2})?") String subFolder,
        @Size(max = 5) String urlSuffix,
        int sortOrder,
        boolean enabled) {}
