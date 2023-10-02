package com.ecommerce.engine.config;

import com.ecommerce.engine.entity.Language;
import com.ecommerce.engine.util.StoreSettings;
import com.ecommerce.engine.validation.LocaleDeserializer;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

public class CustomAcceptHeaderLocaleResolver extends AcceptHeaderLocaleResolver {

    public static final List<Locale> SUPPORTED_ADMIN_LOCALES = List.of(Locale.ENGLISH, new Locale("uk"));

    @Nonnull
    @Override
    public Locale resolveLocale(@Nonnull HttpServletRequest request) {
        String languageHeader = request.getHeader("Accept-Language");
        Locale requestLocale;

        // Ability to accept locales written with underscore
        if (languageHeader != null && languageHeader.length() >= 5) {
            String substring = languageHeader.substring(0, 5);
            if (LocaleDeserializer.LOCALE_PATTERN.matcher(substring).matches()) {
                requestLocale = LocaleUtils.toLocale(substring.replace("-", "_"));
            } else {
                requestLocale = request.getLocale();
            }
        } else {
            requestLocale = request.getLocale();
        }

        return isAdminController(request) ? resolveAdminLocale(requestLocale) : resolveStoreLocale(requestLocale);
    }

    private Locale resolveAdminLocale(Locale requestLocale) {
        return SUPPORTED_ADMIN_LOCALES.stream()
                .filter(supportedLocale -> supportedLocale.getLanguage().equals(requestLocale.getLanguage()))
                .findFirst()
                .orElse(Locale.ENGLISH);
    }

    private static Locale resolveStoreLocale(Locale requestLocale) {
        return StoreSettings.storeLanguages.stream()
                .map(Language::getHreflang)
                .filter(supportedLocale -> supportedLocale.equals(requestLocale))
                .findFirst()
                .orElse(StoreSettings.defaultStoreLanguage.getHreflang());
    }

    private boolean isAdminController(HttpServletRequest request) {
        return request.getRequestURI().matches("/admin.*");
    }
}
