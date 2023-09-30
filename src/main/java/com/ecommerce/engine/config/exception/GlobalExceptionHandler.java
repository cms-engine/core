package com.ecommerce.engine.config.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /*@ExceptionHandler
    public ResponseEntity<ErrorDto> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        final String regex = "Detail: (.*?)\\.]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ex.getMessage());

        String convertedMessage = matcher.find() ? matcher.group(1) : ex.getMessage();
        var applicationException = new ApplicationException(ErrorCode.INTEGRITY_VIOLATION, convertedMessage, ex);
        log.error(convertedMessage, applicationException);

        return buildErrorResponse(applicationException);
    }*/

    private ResponseEntity<ErrorDto> buildErrorResponse(ApplicationException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorDto(ex));
    }
}
