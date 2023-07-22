package com.ecommerce.engine.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

@UtilityClass
public class ValidationUtils {

    private static final ResourceBundle defaultValidationMessages = ResourceBundle.getBundle("org.hibernate.validator.ValidationMessages");

    @SneakyThrows
    public static String getValidationMessage(Annotation declaredAnnotation, Class<? extends Annotation> annotationClass) {
        Method declaredMethod = annotationClass.getDeclaredMethod("message");
        String message = declaredMethod.invoke(declaredAnnotation).toString();

        if (message.matches("\\{jakarta\\.validation\\.constraints\\..+message}")) {
            message = message.replaceAll("\\{", "").replaceAll("}", "");
            return defaultValidationMessages.getString(message);
        }

        return message;
    }
}
