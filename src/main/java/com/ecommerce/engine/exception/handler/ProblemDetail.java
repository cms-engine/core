package com.ecommerce.engine.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ProblemDetail(
        ErrorCode code, String message, Instant timestamp, String traceId, List<ErrorRecord> errors) {}
