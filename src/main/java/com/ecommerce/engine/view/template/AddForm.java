package com.ecommerce.engine.view.template;

import com.ecommerce.engine.util.ReflectionUtils;
import com.ecommerce.engine.util.VaadinUtils;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.ListCrudRepository;

import java.lang.reflect.Field;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddForm<T, ID> extends VerticalLayout {

    Binder<T> binder;
    ListCrudRepository<T, ID> listCrudRepository;
    EditForm<T, ID> navigateAfterSaving;
    Class<T> entityClass;
    Class<ID> idClass;

    public AddForm(ListCrudRepository<T, ID> listCrudRepository,
                   EditForm<T, ID> navigateAfterSaving,
                   Class<T> entityClass,
                   Class<ID> idClass) {
        this.listCrudRepository = listCrudRepository;
        this.navigateAfterSaving = navigateAfterSaving;
        this.entityClass = entityClass;
        this.idClass = idClass;
        this.binder = new Binder<>(this.entityClass);

        FormLayout inputLayout = new FormLayout();
        add(inputLayout);

        refreshForm();

        Field[] declaredFields = this.entityClass.getDeclaredFields();

        for (Field field : declaredFields) {
            VaadinUtils.resolveAndAddVaadinComponent(field, binder, inputLayout);
        }
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

        getUI().ifPresent(ui -> ui.navigate(
                navigateAfterSaving.getClass(), ReflectionUtils.getEntityId(savedEntity, idClass)));
    }
}
