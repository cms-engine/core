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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UnauthorizedHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final TraceProvider traceProvider;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
            throws IOException {
        log.debug(ex.getMessage(), ex);

        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ProblemDetail problemDetail = new ProblemDetail(
                ErrorCode.UNAUTHORIZED, ex.getMessage(), Instant.now(), traceProvider.getTraceId(), null);

        var responseMessage = objectMapper.writeValueAsString(problemDetail);
        response.getOutputStream().println(responseMessage);
    }
}
