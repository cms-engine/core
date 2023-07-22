package com.ecommerce.engine.util;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import jakarta.validation.constraints.*;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@UtilityClass
public class VaadinUtils {

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static Binder.BindingBuilder<?, ?> applyValidationToInput(Binder.BindingBuilder<?, ?> bindingBuilder, Annotation declaredAnnotation, Class<? extends Annotation> annotationClass, String message) {
        if (annotationClass.equals(NotNull.class) || annotationClass.equals(NotEmpty.class)) {
            bindingBuilder = bindingBuilder.asRequired(message);
        }

        if (annotationClass.equals(NotBlank.class)) {
            Binder.BindingBuilder<?, String> bindingBuilderString = (Binder.BindingBuilder<?, String>) bindingBuilder;

            bindingBuilder = bindingBuilderString.asRequired(message).withValidator(new AbstractValidator<>(message) {
                @Override
                public ValidationResult apply(String value, ValueContext context) {
                    return toResult(value, StringUtils.hasText(value));
                }
            });
        }

        if (annotationClass.equals(Pattern.class) || annotationClass.equals(Email.class)) {
            Binder.BindingBuilder<?, String> bindingBuilderString = (Binder.BindingBuilder<?, String>) bindingBuilder;

            Method declaredMethod = annotationClass.getDeclaredMethod("regexp");
            String regexp = declaredMethod.invoke(declaredAnnotation).toString();

            bindingBuilder = bindingBuilderString.withValidator(new RegexpValidator(message, regexp));
        }

        if (annotationClass.equals(Size.class)) {
            Binder.BindingBuilder<?, String> bindingBuilderString = (Binder.BindingBuilder<?, String>) bindingBuilder;

            int min = (int) annotationClass.getDeclaredMethod("min").invoke(declaredAnnotation);
            int max = (int) annotationClass.getDeclaredMethod("max").invoke(declaredAnnotation);

            bindingBuilder = bindingBuilderString.withValidator(new StringLengthValidator(message, min, max));
        }

        return bindingBuilder;
    }
}
