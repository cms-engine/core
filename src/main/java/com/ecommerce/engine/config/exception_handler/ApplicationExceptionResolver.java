package com.ecommerce.engine.config.exception_handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

@Slf4j
public class ApplicationExceptionResolver extends ResponseStatusExceptionResolver {

    @Override
    protected ModelAndView doResolveException(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        if (ex instanceof ApplicationException applicationException) {
            String exceptionMessage = applicationException.getMessage();

            log.error(exceptionMessage, applicationException);

            return super.doResolveException(
                    request,
                    response,
                    handler,
                    new ResponseStatusException(
                            applicationException.getHttpStatus(), exceptionMessage, applicationException.getCause()));
        }

        return null;
    }
}
