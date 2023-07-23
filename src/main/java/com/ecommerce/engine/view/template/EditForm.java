package com.ecommerce.engine.view.template;

import com.ecommerce.engine.util.VaadinUtils;
import com.ecommerce.engine.view.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.ListCrudRepository;

import java.lang.reflect.Field;

@Route(value = "users", layout = MainLayout.class)
@PageTitle("User edit")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EditForm<T, ID> extends VerticalLayout implements HasUrlParameter<ID> {

    Binder<T> binder;
    ListCrudRepository<T, ID> listCrudRepository;
    Paragraph idLabel;
    @Getter
    Class<T> entityClass;

    public EditForm(ListCrudRepository<T, ID> listCrudRepository,
                    Class<? extends Component> gridNavigation,
                    Class<T> entityClass) {

        this.listCrudRepository = listCrudRepository;
        this.entityClass = entityClass;
        this.binder = new Binder<>(entityClass);
        this.idLabel = new Paragraph();

        FormLayout inputLayout = new FormLayout();

        Button saveButton = new Button("Save", VaadinIcon.PLUS.create(), buttonClickEvent -> saveEntity());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        ConfirmDialog deleteConfirm = getConfirmDeleteDialog(listCrudRepository, gridNavigation);

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

        Field[] declaredFields = entityClass.getDeclaredFields();

        for (Field field : declaredFields) {
            VaadinUtils.resolveAndAddVaadinComponent(field, binder, inputLayout);
        }
    }

    private ConfirmDialog getConfirmDeleteDialog(ListCrudRepository<T, ID> saveDeleteService, Class<? extends Component> gridNavigation) {
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
        binder.setBean(listCrudRepository.findById(parameter).orElseThrow());
        idLabel.setText("Id: %s".formatted(parameter));
    }

    public void saveEntity() {
        T savedEntity = listCrudRepository.save(binder.getBean());
        binder.readBean(savedEntity);

        Notification.show("Successful saved");
    }
}
