package com.ecommerce.engine.exception;

import com.ecommerce.engine.config.exception.ApplicationException;
import com.ecommerce.engine.config.exception.ErrorCode;
import com.ecommerce.engine.util.TranslationUtils;
import org.apache.commons.lang3.StringUtils;

public class NotUniqueException extends ApplicationException {

    public NotUniqueException(String entity, Object id, String field, Object value) {
        super(
                ErrorCode.NOT_UNIQUE,
                TranslationUtils.getMessage(
                        "exception.notUnique",
                        StringUtils.capitalize(TranslationUtils.getMessage(TranslationUtils.TABLE_NAME_KEY + entity)),
                        id,
                        field,
                        value));
    }
}
