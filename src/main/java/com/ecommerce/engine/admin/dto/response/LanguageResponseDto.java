package com.ecommerce.engine.admin.dto.response;

import com.ecommerce.engine.entity.Language;

public record LanguageResponseDto(
        int id, String name, String hreflang, String subFolder, String suffixUrl, int sortOrder, boolean enabled) {
    public LanguageResponseDto(Language language) {
        this(
                language.getId(),
                language.getName(),
                language.getHreflang().toString().replace("_", "-"),
                language.getSubFolder(),
                language.getUrlSuffix(),
                language.getSortOrder(),
                language.isEnabled());
    }
}
