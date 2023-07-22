package com.ecommerce.engine.view.template;

import com.ecommerce.engine.model.VaadinInputFactory;
import com.ecommerce.engine.util.ReflectionUtils;
import com.ecommerce.engine.util.TextUtils;
import com.ecommerce.engine.util.VaadinUtils;
import com.ecommerce.engine.util.ValidationUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.ListCrudRepository;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddForm<T, ID> extends VerticalLayout {

    Binder<T> binder;
    ListCrudRepository<T, ID> listCrudRepository;
    Class<? extends EditForm<T, ID>> navigateAfterSaving;
    @Getter
    Class<T> entityClass;
    Class<ID> idClass;
    FormLayout inputLayout = new FormLayout();

    public AddForm(ListCrudRepository<T, ID> listCrudRepository, Class<? extends EditForm<T, ID>> navigateAfterSaving, List<ListCrudRepository<?, ?>> allRepositories) {
        this.listCrudRepository = listCrudRepository;
        this.navigateAfterSaving = navigateAfterSaving;
        this.entityClass = ReflectionUtils.findEntityTypeByRepository(listCrudRepository);
        this.idClass = ReflectionUtils.findIdTypeByAddForm(listCrudRepository);

        binder = new Binder<>(entityClass);

        add(inputLayout);

        refreshForm();

        Field[] declaredFields = entityClass.getDeclaredFields();

        for (Field field : declaredFields) {
            resolveAndAddVaadinComponent(field, allRepositories);
        }
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

    private void resolveAndAddVaadinComponent(Field field, List<ListCrudRepository<?, ?>> allRepositories) {
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

        var listCrudRepositoryByGenericType = ReflectionUtils.findListCrudRepositoryByGenericType(field.getType(), allRepositories);
        if (listCrudRepositoryByGenericType == null) {
            return;
        }

        createComboBoxInput(field, listCrudRepositoryByGenericType);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void createComboBoxInput(Field field, org.springframework.data.repository.ListCrudRepository<?, ?> listCrudRepositoryByGenericType) {
        ComboBox comboBox = new ComboBox<>();
        comboBox.setLabel(TextUtils.convertCamelCaseToNormalText(field.getName()));
        comboBox.setItems(listCrudRepositoryByGenericType.findAll());
        comboBox.setItemLabelGenerator(Object::toString);

        binder.bind(comboBox, field.getName());
        inputLayout.add(comboBox);
    }

    public void refreshForm() {
        binder.readBean(getNewBean());
    }

    @SneakyThrows
    public T getNewBean() {
        return entityClass.getConstructor().newInstance();
    }

    public void saveButtonActiveListener(Button saveButton) {
        saveButton.setEnabled(binder.isValid());
        binder.addValueChangeListener((HasValue.ValueChangeListener<HasValue.ValueChangeEvent<?>>) event -> {
            saveButton.setEnabled(binder.isValid());
        });
    }

    public void saveEntity() {
        T newBean = getNewBean();

        try {
            binder.writeBean(newBean);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }

        T savedEntity = listCrudRepository.save(newBean);

        Notification.show("Successful saved");

        refreshForm();

        /*getUI().ifPresent(ui -> ui.navigate(
                navigateAfterSaving, ReflectionUtils.getEntityId(savedEntity, idClass)));*/
    }
}
