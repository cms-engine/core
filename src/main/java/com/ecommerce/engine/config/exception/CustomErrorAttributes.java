package com.ecommerce.engine.config.exception;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.BINDING_ERRORS;
import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;
import static org.springframework.boot.web.error.ErrorAttributeOptions.of;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

@Slf4j
public class CustomErrorAttributes extends DefaultErrorAttributes {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

        options = of(BINDING_ERRORS, MESSAGE);

        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        errorAttributes.remove("status");
        errorAttributes.remove("path");
        errorAttributes.remove("error");

        checkMessage(errorAttributes);

        convertDateToInstant(errorAttributes);

        processBindingErrors(errorAttributes);

        var throwable = getError(webRequest);
        addErrorCodeToAttributes(errorAttributes, throwable);

        log.debug(objectMapper.writeValueAsString(errorAttributes), throwable);
        return errorAttributes;
    }

    private void checkMessage(Map<String, Object> errorAttributes) {
        final String MESSAGE_FIELD = "message";

        if ("No message available".equals(errorAttributes.get(MESSAGE_FIELD))) {
            errorAttributes.remove(MESSAGE_FIELD);
        }
    }

    private void convertDateToInstant(Map<String, Object> errorAttributes) {
        final String TIMESTAMP_FIELD = "timestamp";

        Date timestamp = (Date) errorAttributes.get(TIMESTAMP_FIELD);
        errorAttributes.put(TIMESTAMP_FIELD, timestamp.toInstant().toEpochMilli());
    }

    private void addErrorCodeToAttributes(Map<String, Object> errorAttributes, Throwable throwable) {
        if (throwable instanceof ApplicationException exception) {
            errorAttributes.put("code", exception.getErrorCode());
        }
    }

    @SneakyThrows
    private void processBindingErrors(Map<String, Object> errorAttributes) {
        final String ERRORS_FIELD = "errors";

        if (!errorAttributes.containsKey(ERRORS_FIELD)) {
            return;
        }

        Object errors = errorAttributes.get(ERRORS_FIELD);
        errorAttributes.remove(ERRORS_FIELD);

        String jsonErrors = objectMapper.writeValueAsString(errors);

        List<?> errorList = objectMapper.readValue(jsonErrors, List.class);
        Set<String> convertedErrors = errorList.stream()
                .map(error -> objectMapper.convertValue(error, Map.class))
                .map(attributes -> String.format("%s %s", attributes.get("field"), attributes.get("defaultMessage")))
                .collect(Collectors.toSet());

        errorAttributes.put("binding", convertedErrors);
    }
}
