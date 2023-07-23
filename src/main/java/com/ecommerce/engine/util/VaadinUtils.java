package com.ecommerce.engine.util;

import com.ecommerce.engine.model.VaadinInputFactory;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@UtilityClass
public class VaadinUtils {

    public static void resolveAndAddVaadinComponent(Field field, Binder<?> binder, FormLayout inputLayout) {
        if (field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(GeneratedValue.class)) {
            return;
        }

        HasValue<?, ?> inputFromClass = VaadinInputFactory.createVaadinInput(field);
        if (inputFromClass != null) {
            Binder.BindingBuilder<?, ?> bindingBuilder = binder.forField(inputFromClass);

            if (field.getType().isPrimitive()) {
                bindingBuilder.asRequired().bind(field.getName());
            } else {
                bindingBuilder = bindJakartaValidations(field, bindingBuilder);
                bindingBuilder.bind(field.getName());
            }
            inputLayout.add((Component) inputFromClass);
            return;
        }

        if (!ReflectionUtils.isToOneColumn(field)) {
            return;
        }

        var listCrudRepositoryByGenericType = ReflectionUtils.findListCrudRepositoryByGenericType(field.getType());
        if (listCrudRepositoryByGenericType == null) {
            return;
        }

        createComboBoxInput(field, listCrudRepositoryByGenericType, binder, inputLayout);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void createComboBoxInput(Field field, org.springframework.data.repository.ListCrudRepository<?, ?> listCrudRepositoryByGenericType, Binder<?> binder, FormLayout inputLayout) {
        ComboBox comboBox = new ComboBox<>();
        comboBox.setLabel(TextUtils.convertCamelCaseToNormalText(field.getName()));
        comboBox.setItems(listCrudRepositoryByGenericType.findAll());
        comboBox.setItemLabelGenerator(Object::toString);

        binder.bind(comboBox, field.getName());
        inputLayout.add(comboBox);
    }

    private static Binder.BindingBuilder<?, ?> bindJakartaValidations(Field field, Binder.BindingBuilder<?, ?> bindingBuilder) {
        Annotation[] declaredAnnotations = field.getDeclaredAnnotations();

        for (Annotation declaredAnnotation : declaredAnnotations) {
            Class<? extends Annotation> annotationClass = declaredAnnotation.annotationType();
            if (!annotationClass.getPackageName().equals("jakarta.validation.constraints")) {
                continue;
            }

            String message = ValidationUtils.getValidationMessage(declaredAnnotation, annotationClass);

            bindingBuilder = VaadinUtils.applyValidationToInput(bindingBuilder, declaredAnnotation, annotationClass, message);
        }

        return bindingBuilder;
    }

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
