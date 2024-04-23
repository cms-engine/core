package com.ecommerce.engine.controller.admin;

import com.ecommerce.engine.config.CustomAcceptHeaderLocaleResolver;
import com.ecommerce.engine.dto.common.SelectDto;
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
}
