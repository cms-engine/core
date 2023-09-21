package com.ecommerce.engine.config.exception;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationException extends RuntimeException {

    final ErrorCode errorCode;
    final HttpStatusCode httpStatus;

    public ApplicationException(ErrorCode errorCode) {
        this(errorCode, UNPROCESSABLE_ENTITY, null, null);
    }

    public ApplicationException(ErrorCode errorCode, Throwable cause) {
        this(errorCode, UNPROCESSABLE_ENTITY, null, cause);
    }

    public ApplicationException(ErrorCode errorCode, String message) {
        this(errorCode, UNPROCESSABLE_ENTITY, message, null);
    }

    public ApplicationException(ErrorCode errorCode, String message, Throwable cause) {
        this(errorCode, UNPROCESSABLE_ENTITY, message, cause);
    }

    public ApplicationException(ErrorCode errorCode, HttpStatusCode httpStatus) {
        this(errorCode, httpStatus, null, null);
    }

    public ApplicationException(ErrorCode errorCode, HttpStatusCode httpStatus, Throwable cause) {
        this(errorCode, httpStatus, null, cause);
    }

    public ApplicationException(ErrorCode errorCode, HttpStatusCode httpStatus, String message) {
        this(errorCode, httpStatus, message, null);
    }

    public ApplicationException(ErrorCode errorCode, HttpStatusCode httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
