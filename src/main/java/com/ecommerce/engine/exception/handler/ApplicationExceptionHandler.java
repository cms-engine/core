package com.ecommerce.engine.exception.handler;

import static com.ecommerce.engine.exception.handler.ErrorCode.INTERNAL_ERROR;
import static com.ecommerce.engine.exception.handler.ErrorCode.JSON_PARSE_ERROR;
import static com.ecommerce.engine.exception.handler.ErrorCode.VALIDATION_ERROR;

import com.ecommerce.engine.config.TraceProvider;
import com.ecommerce.engine.exception.ApplicationException;
import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Payload;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INTERNAL_ERROR_MESSAGE = "A system error has occurred. Please contact the support.";
    private static final String VALIDATION_ERROR_MESSAGE = "Validation failed. Please check errors for details.";

    private final TraceProvider traceProvider;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status,
            @Nonnull WebRequest request) {
        var fieldErrorRecords = ex.getBindingResult().getFieldErrors().stream()
                .map(this::createFromFieldError)
                .flatMap(List::stream)
                .toList();

        ProblemDetail problemDetail = new ProblemDetail(
                VALIDATION_ERROR,
                VALIDATION_ERROR_MESSAGE,
                Instant.now(),
                traceProvider.getTraceId(),
                fieldErrorRecords);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @Nonnull HttpMessageNotReadableException ex,
            @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status,
            @Nonnull WebRequest request) {
        ProblemDetail problemDetail =
                new ProblemDetail(JSON_PARSE_ERROR, ex.getMessage(), Instant.now(), traceProvider.getTraceId(), null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ProblemDetail globalExceptionHandling(Exception ex) {
        log.error(ex.getMessage(), ex);

        return new ProblemDetail(
                INTERNAL_ERROR, INTERNAL_ERROR_MESSAGE, Instant.now(), traceProvider.getTraceId(), null);
    }

    @ExceptionHandler
    protected ResponseEntity<ProblemDetail> handleApplicationException(ApplicationException ex) {
        String exceptionMessage = ex.getMessage();
        HttpStatus exceptionHttpStatus = ex.getHttpStatus();

        log.error(exceptionMessage, ex);

        ProblemDetail problemDetail =
                new ProblemDetail(ex.getErrorCode(), exceptionMessage, Instant.now(), traceProvider.getTraceId(), null);

        return ResponseEntity.status(exceptionHttpStatus).body(problemDetail);
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException ex) {
        log.debug(ex.getMessage(), ex);

        return new ProblemDetail(ErrorCode.FORBIDDEN, ex.getMessage(), Instant.now(), traceProvider.getTraceId(), null);
    }

    private List<ErrorRecord> createFromFieldError(FieldError error) {
        ConstraintViolation<?> constraintViolation = error.unwrap(ConstraintViolation.class);
        Set<Class<? extends Payload>> payloads =
                constraintViolation.getConstraintDescriptor().getPayload();

        return payloads.isEmpty()
                ? List.of(new ErrorRecord(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
                : payloads.stream()
                        .map(this::createValidationPayloadInstance)
                        .flatMap(Optional::stream)
                        .map(payload -> new ErrorRecord(payload.get(), error.getField(), error.getRejectedValue()))
                        .toList();
    }

    private Optional<ValidationPayload> createValidationPayloadInstance(Class<? extends Payload> clazz) {
        return ValidationPayload.class.isAssignableFrom(clazz)
                ? Optional.of(BeanUtils.instantiateClass(clazz, ValidationPayload.class))
                : Optional.empty();
    }
}
