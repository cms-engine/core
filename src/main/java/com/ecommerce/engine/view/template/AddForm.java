package com.ecommerce.engine.view.template;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import org.springframework.data.repository.CrudRepository;

import java.util.function.Function;

public abstract class AddForm<T, ID> extends VerticalLayout {

    protected final Binder<T> binder;
    protected final CrudRepository<T, ID> crudRepository;
    protected final Function<T, ID> identifierGetter;
    private final Class<? extends NavigatedFormLayout<ID>> navigateAfterSaving;
    private final FormLayout inputLayout;

    public AddForm(CrudRepository<T, ID> crudRepository, Function<T, ID> identifierGetter, Class<T> aClass, Class<? extends NavigatedFormLayout<ID>> navigateAfterSaving) {
        binder = new Binder<>(aClass);
        inputLayout = new FormLayout();
        this.crudRepository = crudRepository;
        this.identifierGetter = identifierGetter;
        this.navigateAfterSaving = navigateAfterSaving;

        Button saveButton = new Button("Save", VaadinIcon.PLUS.create(), buttonClickEvent -> saveEntity());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout mainButtonsLayout = new HorizontalLayout(saveButton);
        setAlignSelf(Alignment.END, mainButtonsLayout);

        add(mainButtonsLayout, inputLayout);

        refreshForm();
    }

    public void addComponents(Component... components) {
        inputLayout.add(components);
    }

    public void refreshForm() {
        binder.readBean(getNewBean());
    }

    public abstract T getNewBean();

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

        T savedEntity = crudRepository.save(newBean);

        Notification.show("Successful saved");

        refreshForm();

        getUI().ifPresent(ui -> ui.navigate(
                navigateAfterSaving, identifierGetter.apply(savedEntity)));
    }
}
