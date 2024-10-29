package com.ecommerce.engine._admin.controller;

import com.ecommerce.engine.config.CustomAcceptHeaderLocaleResolver;
import com.ecommerce.engine.dto.SelectDto;
import com.ecommerce.engine.entity.projection.SelectProjection;
import com.ecommerce.engine.enums.InputType;
import com.ecommerce.engine.enums.SeoUrlType;
import com.ecommerce.engine.service.SelectOptionCollector;
import com.ecommerce.engine.util.TranslationUtils;
import com.ecommerce.engine.validation.EntityType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/catalog")
public class CatalogController {

    private final Map<EntityType, SelectOptionCollector> typeSelectOptionCollectorMapMap;

    public CatalogController(List<SelectOptionCollector> entityPresenceServices) {
        typeSelectOptionCollectorMapMap = entityPresenceServices.stream()
                .collect(Collectors.toMap(SelectOptionCollector::getEntityType, Function.identity()));
    }

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

    @GetMapping("/brands")
    public Collection<SelectProjection> brands(
            @RequestParam int page, @RequestParam int size, @RequestParam(required = false) String search) {
        return typeSelectOptionCollectorMapMap
                .get(EntityType.BRAND)
                .findSelectOptions(PageRequest.of(page, size), 3, search);
    }

    @GetMapping("/categories")
    public Collection<SelectProjection> categories(
            @RequestParam int page, @RequestParam int size, @RequestParam(required = false) String search) {
        return typeSelectOptionCollectorMapMap
                .get(EntityType.CATEGORY)
                .findSelectOptions(PageRequest.of(page, size), 3, search);
    }

    @GetMapping("/customer-groups")
    public Collection<SelectProjection> customerGroups(
            @RequestParam int page, @RequestParam int size, @RequestParam(required = false) String search) {
        return typeSelectOptionCollectorMapMap
                .get(EntityType.CUSTOMER_GROUP)
                .findSelectOptions(PageRequest.of(page, size), 3, search);
    }

    @GetMapping("/languages")
    public Collection<SelectProjection> languages(
            @RequestParam int page, @RequestParam int size, @RequestParam(required = false) String search) {
        return typeSelectOptionCollectorMapMap
                .get(EntityType.LANGUAGE)
                .findSelectOptions(PageRequest.of(page, size), 3, search);
    }

    @GetMapping("/attributes")
    public Collection<SelectProjection> attributes(
            @RequestParam int page, @RequestParam int size, @RequestParam(required = false) String search) {
        return typeSelectOptionCollectorMapMap
                .get(EntityType.ATTRIBUTE)
                .findSelectOptions(PageRequest.of(page, size), 3, search);
    }

    @GetMapping("/images")
    public Collection<SelectProjection> images(
            @RequestParam int page, @RequestParam int size, @RequestParam(required = false) String search) {
        return typeSelectOptionCollectorMapMap
                .get(EntityType.IMAGE)
                .findSelectOptions(PageRequest.of(page, size), 3, search);
    }
}
