package com.ecommerce.engine._admin.controller;

import com.ecommerce.engine.config.CustomAcceptHeaderLocaleResolver;
import com.ecommerce.engine.dto.SelectDto;
import com.ecommerce.engine.enums.InputType;
import com.ecommerce.engine.enums.SeoUrlType;
import com.ecommerce.engine.util.TranslationUtils;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/catalog")
public class CatalogController {

    @GetMapping("/supported-locales")
    public Collection<SelectDto> getSupportedLocales() {
        return CustomAcceptHeaderLocaleResolver.SUPPORTED_ADMIN_LOCALES.stream()
                .map(locale -> new SelectDto(locale.toLanguageTag(), locale.getDisplayName()))
                .toList();
    }

    @GetMapping("/input-types")
    public Collection<SelectDto> getInputTypes() {
        return Arrays.stream(InputType.values())
                .map(enumValue -> new SelectDto(enumValue.name()))
                .toList();
    }

    @GetMapping("/seo-url-types")
    public Collection<SelectDto> getSeoUrlTypes() {
        return Arrays.stream(SeoUrlType.values())
                .map(enumValue ->
                        new SelectDto(enumValue.name(), TranslationUtils.getMessage(enumValue.getTranslationKey())))
                .toList();
    }
}
