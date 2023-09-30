package com.ecommerce.engine.util;

import java.util.Locale;
import lombok.experimental.UtilityClass;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

@UtilityClass
public class TranslationUtils {
    public static final String TABLE_NAME_KEY = "tableName.";
    private static final ResourceBundleMessageSource MESSAGE_SOURCE;

    static {
        MESSAGE_SOURCE = new ResourceBundleMessageSource();
        MESSAGE_SOURCE.setBasenames("translation/messages");
        MESSAGE_SOURCE.setDefaultEncoding("UTF-8");
        MESSAGE_SOURCE.setDefaultLocale(Locale.ENGLISH);
    }

    public String getMessage(String key, Object... args) {
        return MESSAGE_SOURCE.getMessage(key, args, LocaleContextHolder.getLocale());
    }
}
