package com.ecommerce.engine.view.user;

import com.ecommerce.engine.model.VaadinInputFactory;
import com.ecommerce.engine.repository.entity.User;
import com.ecommerce.engine.util.ReflectionUtils;
import com.ecommerce.engine.util.TextUtils;
import com.ecommerce.engine.util.VaadinUtils;
import com.ecommerce.engine.util.ValidationUtils;
import com.ecommerce.engine.view.template.AddForm;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.binder.Binder;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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

    private void resolveAndAddVaadinComponent(List<HasValue<?, ?>> createdComponents, Field field) {
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
}
