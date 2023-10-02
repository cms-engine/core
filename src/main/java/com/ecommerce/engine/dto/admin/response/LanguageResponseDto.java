package com.ecommerce.engine.dto.admin.response;

import com.ecommerce.engine.entity.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

public record LanguageResponseDto(
        int id,
        String name,
        String hreflang,
        String subFolder,
        String suffixUrl,
        @JsonProperty("default") boolean defaultLang,
        boolean enabled) {
    public LanguageResponseDto(Language language) {
        this(
                language.getId(),
                language.getName(),
                language.getHreflang().toString().replace("_", "-"),
                language.getSubFolder(),
                language.getUrlSuffix(),
                language.isDefaultLang(),
                language.isEnabled());
    }
}
