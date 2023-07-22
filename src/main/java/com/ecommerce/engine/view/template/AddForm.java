package com.ecommerce.engine.view.template;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.SneakyThrows;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.function.Function;

public abstract class AddForm<T, ID> extends VerticalLayout {

    protected final Binder<T> binder;
    protected final ListCrudRepository<T, ID> ListCrudRepository;
    protected final Function<T, ID> identifierGetter;
    private final Class<? extends EditForm<T, ID>> navigateAfterSaving;
    private final FormLayout inputLayout;
    private final Class<T> entityClass;

    public AddForm(ListCrudRepository<T, ID> ListCrudRepository, Function<T, ID> identifierGetter, Class<T> entityClass, Class<? extends EditForm<T, ID>> navigateAfterSaving) {
        binder = new Binder<>(entityClass);
        inputLayout = new FormLayout();
        this.ListCrudRepository = ListCrudRepository;
        this.identifierGetter = identifierGetter;
        this.navigateAfterSaving = navigateAfterSaving;
        this.entityClass = entityClass;

        add(inputLayout);

        refreshForm();
    }

    public void addComponents(List<Component> components) {
        inputLayout.add(components);
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

        T savedEntity = ListCrudRepository.save(newBean);

        Notification.show("Successful saved");

        refreshForm();

        getUI().ifPresent(ui -> ui.navigate(
                navigateAfterSaving, identifierGetter.apply(savedEntity)));
    }
}
