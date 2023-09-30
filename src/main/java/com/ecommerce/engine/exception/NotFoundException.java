package com.ecommerce.engine.exception;

import com.ecommerce.engine.util.TranslationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

    public NotFoundException(String entity, Object id) {
        super(
                HttpStatus.NOT_FOUND,
                TranslationUtils.getMessage(
                        "exception.notFound",
                        StringUtils.capitalize(TranslationUtils.getMessage(TranslationUtils.TABLE_NAME_KEY + entity)),
                        id));
    }
}
