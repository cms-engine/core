package com.ecommerce.engine.controller.admin;

import com.ecommerce.engine.config.CustomAcceptHeaderLocaleResolver;
import java.util.List;
import java.util.Locale;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("SameReturnValue")
@RestController
@RequestMapping("/admin/catalog")
public class CatalogController {

    @GetMapping("/supported-locales")
    public List<Locale> getSupportedLocales() {
        return CustomAcceptHeaderLocaleResolver.SUPPORTED_ADMIN_LOCALES;
    }
}
