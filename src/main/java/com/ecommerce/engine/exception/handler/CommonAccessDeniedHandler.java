package com.ecommerce.engine.exception.handler;

import com.ecommerce.engine.config.TraceProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    private final TraceProvider traceProvider;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
            throws IOException {
        log.debug(ex.getMessage(), ex);

        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ProblemDetail problemDetail = new ProblemDetail(
                ErrorCode.FORBIDDEN, ex.getMessage(), Instant.now(), traceProvider.getTraceId(), null);

        var responseMessage = objectMapper.writeValueAsString(problemDetail);
        response.getOutputStream().println(responseMessage);
    }
}
