package com.ecommerce.engine.util;

import lombok.experimental.UtilityClass;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

@UtilityClass
public class TranslationUtils {
    private static final ResourceBundleMessageSource MESSAGE_SOURCE;

    static {
        MESSAGE_SOURCE = new ResourceBundleMessageSource();
        MESSAGE_SOURCE.setBasenames("translation/messages");
        MESSAGE_SOURCE.setDefaultEncoding("UTF-8");
    }

    public String getMessage(String key, Object... args) {
        return MESSAGE_SOURCE.getMessage(key, args, LocaleContextHolder.getLocale());
    }
}
