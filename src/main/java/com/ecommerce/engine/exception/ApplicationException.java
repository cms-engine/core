package com.ecommerce.engine.exception;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import com.ecommerce.engine.exception.handler.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;

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

    public ApplicationException(ErrorCode errorCode, HttpStatus httpStatus) {
        this(errorCode, httpStatus, null, null);
    }

    public ApplicationException(ErrorCode errorCode, HttpStatus httpStatus, Throwable cause) {
        this(errorCode, httpStatus, null, cause);
    }

    public ApplicationException(ErrorCode errorCode, HttpStatus httpStatus, String message) {
        this(errorCode, httpStatus, message, null);
    }

    public ApplicationException(ErrorCode errorCode, HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
