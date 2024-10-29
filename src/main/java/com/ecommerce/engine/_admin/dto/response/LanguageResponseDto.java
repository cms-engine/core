package com.ecommerce.engine._admin.dto.response;

import com.ecommerce.engine.entity.Language;
import java.util.Locale;

public record LanguageResponseDto(
        int id, String name, Locale hreflang, String subFolder, String suffixUrl, int sortOrder, boolean enabled) {
    public LanguageResponseDto(Language language) {
        this(
                language.getId(),
                language.getName(),
                language.getHreflang(),
                language.getSubFolder(),
                language.getUrlSuffix(),
                language.getSortOrder(),
                language.isEnabled());
    }
}
