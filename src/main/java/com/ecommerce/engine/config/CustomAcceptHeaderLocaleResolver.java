package com.ecommerce.engine.config;

import com.ecommerce.engine.validation.LocaleDeserializer;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

public class CustomAcceptHeaderLocaleResolver extends AcceptHeaderLocaleResolver {

    private final Locale defaultLocale = Locale.ENGLISH;

    @Nonnull
    @Override
    public Locale resolveLocale(@Nonnull HttpServletRequest request) {
        Locale requestLocale = request.getLocale();
        if (requestLocale.getLanguage().isEmpty()) {
            requestLocale = defaultLocale;
        }
        String languageHeader = request.getHeader("Accept-Language");

        if (languageHeader == null) {
            return requestLocale;
        }

        if (languageHeader.length() >= 5) {
            String substring = languageHeader.substring(0, 5);
            return LocaleDeserializer.LOCALE_PATTERN.matcher(substring).matches()
                    ? LocaleUtils.toLocale(substring)
                    : requestLocale;
        } else {
            return requestLocale;
        }
    }

    @Override
    protected Locale getDefaultLocale() {
        return defaultLocale;
    }
}
