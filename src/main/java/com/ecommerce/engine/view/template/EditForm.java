package com.ecommerce.engine.view.template;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import org.springframework.data.repository.CrudRepository;

public abstract class EditForm<T, ID> extends NavigatedFormLayout<ID> {

    protected final Binder<T> binder;
    protected final CrudRepository<T, ID> saveDeleteService;
    private final FormLayout inputLayout;
    private final Paragraph idLabel;

    public EditForm(CrudRepository<T, ID> saveDeleteService, Class<T> aClass, Class<? extends Component> gridNavigation) {
        binder = new Binder<>(aClass);
        inputLayout = new FormLayout();
        idLabel = new Paragraph();
        this.saveDeleteService = saveDeleteService;
        Button saveButton = new Button("Save", VaadinIcon.PLUS.create(), buttonClickEvent -> saveEntity());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        ConfirmDialog deleteConfirm = getConfirmDeleteDialog(saveDeleteService, gridNavigation);

        Button deleteButton = new Button("Delete", VaadinIcon.MINUS.create(), buttonClickEvent -> deleteConfirm.open());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        Button returnButton = new Button("Return", VaadinIcon.ANGLE_LEFT.create(), buttonClickEvent -> getUI().ifPresent(ui -> ui.navigate(gridNavigation)));

        HorizontalLayout returnIdLayout =  new HorizontalLayout(returnButton, idLabel);
        returnIdLayout.setWidthFull();
        HorizontalLayout mainButtonsLayout = new HorizontalLayout(saveButton, deleteButton);
        mainButtonsLayout.setWidthFull();
        mainButtonsLayout.setJustifyContentMode(JustifyContentMode.END);
        HorizontalLayout menuTop = new HorizontalLayout(returnIdLayout, mainButtonsLayout);
        menuTop.setWidthFull();

        add(menuTop, inputLayout);
    }

    private ConfirmDialog getConfirmDeleteDialog(CrudRepository<T, ID> saveDeleteService, Class<? extends Component> gridNavigation) {
        ConfirmDialog deleteConfirm = new ConfirmDialog();

        deleteConfirm.setHeader("Confirm delete");

        deleteConfirm.setCancelable(true);

        deleteConfirm.setConfirmText("Delete");
        deleteConfirm.setConfirmButtonTheme("error primary");
        deleteConfirm.addConfirmListener(buttonClickEvent -> {
            saveDeleteService.delete(binder.getBean());
            Notification.show("Successful deleted");
            getUI().ifPresent(ui -> ui.navigate(gridNavigation));
        });

        return deleteConfirm;
    }

    @Override
    public void setParameter(BeforeEvent event, ID parameter) {
        binder.setBean(saveDeleteService.findById(parameter).orElseThrow());
        idLabel.setText("Id: %s".formatted(parameter));
    }

    public void addComponents(Component... components) {
        inputLayout.add(components);
    }

    public void saveEntity() {
        T savedEntity = saveDeleteService.save(binder.getBean());
        binder.readBean(savedEntity);

        Notification.show("Successful saved");
    }
}
