package com.ecommerce.engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

    private static final String TEMPLATE = "Recourse '%s' with id '%s' was not found!";

    public NotFoundException(String entity, Object id) {
        super(HttpStatus.NOT_FOUND, TEMPLATE.formatted(entity, id));
    }
}
