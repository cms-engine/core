package com.ecommerce.engine.exception;

import com.ecommerce.engine.exception.handler.ErrorCode;
import com.ecommerce.engine.util.TranslationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException {

    public NotFoundException(String entity, Object id) {
        super(
                ErrorCode.NOT_FOUND,
                HttpStatus.NOT_FOUND,
                TranslationUtils.getMessage(
                        "exception.notFound",
                        StringUtils.capitalize(TranslationUtils.getMessage(TranslationUtils.TABLE_NAME_KEY + entity)),
                        id));
    }
}
