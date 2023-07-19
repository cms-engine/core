package com.ecommerce.engine.view.user;

import com.ecommerce.engine.model.VaadinInputFactory;
import com.ecommerce.engine.repository.entity.User;
import com.ecommerce.engine.util.ReflectionUtils;
import com.ecommerce.engine.util.TextUtils;
import com.ecommerce.engine.view.template.AddForm;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
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
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserAdd extends AddForm<User, Integer> {


    public UserAdd(ListCrudRepository<User, Integer> userRepository) {
        super(userRepository, User::getId, User.class, UserEdit.class);

        List<HasValue<?, ?>> createdComponents = new ArrayList<>();

        Field[] declaredFields = User.class.getDeclaredFields();

        for (Field field : declaredFields) {
            resolveAndAddVaadinComponent(createdComponents, field);
        }

        addComponents(createdComponents.stream().map(hasValue -> (com.vaadin.flow.component.Component) hasValue).toList());
        /*
        binder.forField(username).asRequired()
                .withValidator(new RegexpValidator("Username can contain only word character [a-zA-Z0-9_]", "^\\w+$")).bind("username");
        binder.forField(email)
                .withValidator(new EmailValidator(
                        "This doesn't look like a valid email address", true)).bind("email");
        binder.forField(age).withValidator(new IntegerRangeValidator("0-100", 0, 100)).bind("age");
        */
    }

    @SneakyThrows
    private static Binder.BindingBuilder<?, ?> bindJakartaValidations(Field field, Binder.BindingBuilder<?, ?> bindingBuilder) {
        Annotation[] declaredAnnotations = field.getDeclaredAnnotations();

        for (Annotation declaredAnnotation : declaredAnnotations) {
            Class<? extends Annotation> annotationClass = declaredAnnotation.annotationType();
            if (!annotationClass.getPackageName().equals("jakarta.validation.constraints")) {
                continue;
            }

            Method declaredMethod = annotationClass.getDeclaredMethod("message");
            String message = declaredMethod.invoke(declaredAnnotation).toString();

            bindingBuilder = applyValidationToInput(bindingBuilder, declaredAnnotation, annotationClass, message);
        }

        return bindingBuilder;
    }

    @SneakyThrows
    private static Binder.BindingBuilder<?, ?> applyValidationToInput(Binder.BindingBuilder<?, ?> bindingBuilder, Annotation declaredAnnotation, Class<? extends Annotation> annotationClass, String message) {
        if (annotationClass.equals(NotNull.class) || annotationClass.equals(NotEmpty.class)) {
            bindingBuilder = bindingBuilder.asRequired(message);
        }

        if (annotationClass.equals(NotBlank.class)) {
            Binder.BindingBuilder<?, String> bindingBuilderString = (Binder.BindingBuilder<?, String>) bindingBuilder;

            bindingBuilder = bindingBuilderString.asRequired().withValidator(new AbstractValidator<>(message) {
                @Override
                public ValidationResult apply(String value, ValueContext context) {
                    return toResult(value, StringUtils.hasText(value));
                }
            });
        }

        if (annotationClass.equals(Pattern.class)) {
            Binder.BindingBuilder<?, String> bindingBuilderString = (Binder.BindingBuilder<?, String>) bindingBuilder;

            Method declaredMethod = annotationClass.getDeclaredMethod("regexp");
            String regexp = declaredMethod.invoke(declaredAnnotation).toString();

            bindingBuilder = bindingBuilderString.asRequired().withValidator(new RegexpValidator(message, regexp));
        }

        if (annotationClass.equals(Size.class)) {
            Binder.BindingBuilder<?, String> bindingBuilderString = (Binder.BindingBuilder<?, String>) bindingBuilder;

            int min = (int) annotationClass.getDeclaredMethod("min").invoke(declaredAnnotation);
            int max = (int) annotationClass.getDeclaredMethod("max").invoke(declaredAnnotation);

            bindingBuilder = bindingBuilderString.asRequired().withValidator(new StringLengthValidator(message, min, max));
        }

        return bindingBuilder;
    }

    private void resolveAndAddVaadinComponent(List<HasValue<?, ?>> createdComponents, Field field) {
        final String jakartaPackage = "jakarta.validation.constraints";

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
            createdComponents.add(inputFromClass);
            return;
        }

        if (!ReflectionUtils.isToOneColumn(field)) {
            return;
        }

        var listCrudRepositoryByGenericType = ReflectionUtils.findListCrudRepositoryByGenericType(field.getType());
        if (listCrudRepositoryByGenericType == null) {
            return;
        }

        createComboBoxInput(createdComponents, field, listCrudRepositoryByGenericType);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void createComboBoxInput(List<HasValue<?, ?>> createdFields, Field field, org.springframework.data.repository.ListCrudRepository<?, ?> listCrudRepositoryByGenericType) {
        ComboBox comboBox = new ComboBox<>();
        comboBox.setLabel(TextUtils.convertCamelCaseToNormalText(field.getName()));
        comboBox.setItems(listCrudRepositoryByGenericType.findAll());
        comboBox.setItemLabelGenerator(Object::toString);

        binder.bind(comboBox, field.getName());
        createdFields.add(comboBox);
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
