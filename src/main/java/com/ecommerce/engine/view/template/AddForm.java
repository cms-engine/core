package com.ecommerce.engine.view.template;

import com.ecommerce.engine.service.SaveDeleteService;
import com.ecommerce.engine.view.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

import java.util.function.Function;

@Route(layout = MainLayout.class)
public abstract class AddForm<T> extends VerticalLayout {

    protected final Binder<T> binder;
    protected final SaveDeleteService<T> saveDeleteService;
    protected final Function<T, Integer> identifierGetter;
    private final Class<? extends NavigatedFormLayout> navigateAfterSaving;
    private final FormLayout inputLayout;

    public AddForm(SaveDeleteService<T> saveDeleteService, Function<T, Integer> identifierGetter, Class<T> aClass, Class<? extends NavigatedFormLayout> navigateAfterSaving) {
        binder = new Binder<>(aClass);
        inputLayout = new FormLayout();
        this.saveDeleteService = saveDeleteService;
        this.identifierGetter = identifierGetter;
        this.navigateAfterSaving = navigateAfterSaving;

        Button saveButton = new Button("Save", VaadinIcon.PLUS.create(), buttonClickEvent -> saveEntity());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout mainButtonsLayout = new HorizontalLayout(saveButton);
        setAlignSelf(Alignment.END, mainButtonsLayout);

        add(mainButtonsLayout, inputLayout);
    }

    public void addComponents(Component... components) {
        inputLayout.add(components);
    }

    public abstract T getNewBean();

    public void saveEntity() {
        T newBean = getNewBean();

        try {
            binder.writeBean(newBean);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }

        T savedEntity = saveDeleteService.save(newBean);

        Notification.show("Successful saved");

        getUI().ifPresent(ui -> ui.navigate(
                navigateAfterSaving, identifierGetter.apply(savedEntity)));
    }
}
