package com.ecommerce.engine.view.user;

import com.ecommerce.engine.model.SearchRequest;
import com.ecommerce.engine.repository.User;
import com.ecommerce.engine.service.UserService;
import com.ecommerce.engine.view.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridMultiSelectionModel;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "users", layout = MainLayout.class)
@PageTitle("Users")
public class UserList extends VerticalLayout {

    public UserList(UserService userService, UserDataProvider userDataProvider) {
        setHeightFull();

        Grid<User> grid = new Grid<>(User.class);

        ((GridMultiSelectionModel<?>) grid
                .setSelectionMode(Grid.SelectionMode.MULTI))
                .setSelectionColumnFrozen(true);
        grid.setColumnReorderingAllowed(true);

        grid.setColumns("id", "username", "password", "email", "age", "dateOfBirth");
        //grid.addColumn(User::getDateOfBirth).setHeader("Date of birth").setSortable(true);

        grid.addComponentColumn(user -> new Button(VaadinIcon.EDIT.create(), event ->
                        getUI().ifPresent(ui -> ui.navigate(UserEdit.class, user.getId()))))
                .setKey("edit").setFrozenToEnd(true).setTextAlign(ColumnTextAlign.CENTER);

        grid.getColumns().forEach(userColumn -> userColumn.setResizable(true).setAutoWidth(true));

        ConfigurableFilterDataProvider<User, Void, List<SearchRequest.Filter>> userVoidSearchRequestConfigurableFilterDataProvider = userDataProvider.withConfigurableFilter();
        grid.setItems(userVoidSearchRequestConfigurableFilterDataProvider);

        UserFilter userFilter = new UserFilter(userVoidSearchRequestConfigurableFilterDataProvider);
        userFilter.setVisible(false);
        Button filter = new Button("Filter", VaadinIcon.FILTER.create(), event -> userFilter.setVisible(!userFilter.isVisible()));

        //header filter
        /*HeaderRow headerRow = grid.appendHeaderRow();

        grid.getColumns().stream().filter(column -> !Objects.equals(column.getKey(), "edit"))
                .forEach(column -> headerRow.getCell(column).setComponent(createFilterHeader()));*/

        //grid.setHeightFull();
        //grid.setAllRowsVisible(true);

        ConfirmDialog deleteConfirm = confirm(userService, grid);
        Button delete = new Button("Delete", VaadinIcon.MINUS.create(), buttonClickEvent -> {
            deleteConfirm.setText(String.format("%s items will be deleted. Are you sure?", grid.getSelectedItems().size()));
            deleteConfirm.open();
        });
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        //Dialog addNew = addNew(userAdd, userService, grid);
        Button create = new Button("Add", VaadinIcon.PLUS.create(), buttonClickEvent -> getUI().ifPresent(ui -> ui.navigate(
                UserAdd.class)));
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout horizontalLayout = new HorizontalLayout(delete, create, filter);
        //horizontalLayout.setWidthFull();
        //horizontalLayout.setJustifyContentMode(JustifyContentMode.END);
        setAlignSelf(Alignment.END, horizontalLayout);

        grid.setSizeFull();
        HorizontalLayout gridLayout = new HorizontalLayout(grid, userFilter);
        gridLayout.setSizeFull();
        add(horizontalLayout, gridLayout);
    }

    private static Component createFilterHeader() {
        TextField textField = new TextField();
        textField.setClearButtonVisible(true);
        textField.setPlaceholder("Filter");
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        //textField.setWidthFull();

        return textField;
    }

    private ConfirmDialog confirm(UserService userService, Grid<User> grid) {
        ConfirmDialog dialog = new ConfirmDialog();

        dialog.setHeader("Confirm delete");

        dialog.setCancelable(true);

        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");
        dialog.addConfirmListener(buttonClickEvent -> {
            userService.deleteAll(grid.getSelectedItems());
            grid.getDataProvider().refreshAll();
            grid.deselectAll();
        });

        return dialog;
    }

    private Dialog addNew(UserAdd userAdd, UserService userService, Grid<User> grid) {
        Dialog dialog = new Dialog();

        /*dialog.setHeaderTitle("New item");

        User user = new User();

        dialog.add(userAdd);

        Button closeButton = new Button(VaadinIcon.CLOSE.create(), event -> {
            userAdd.refreshForm();
            dialog.close();
        });
        dialog.getHeader().add(closeButton);

        Button saveButton = new Button("Save", buttonClickEvent -> {
            userAdd.writeBean(user);

            userService.save(user);
            userAdd.refreshForm();

            dialog.close();
            grid.getDataProvider().refreshAll();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        dialog.getFooter().add(saveButton);

        dialog.addDialogCloseActionListener(event -> {
            userAdd.refreshForm();
            dialog.close();
        });*/

        return dialog;
    }

    private Dialog edit(UserService userService, Grid<User> grid, User user) {
        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("New item");

        /*UserEditor dialogLayout = new UserEditor(userService);
        dialogLayout.readBean(user);
        dialog.add(dialogLayout);

        Button closeButton = new Button(VaadinIcon.CLOSE.create(), event -> {
            dialogLayout.refreshForm();
            dialog.close();
        });
        dialog.getHeader().add(closeButton);

        Button saveButton = new Button("Save", buttonClickEvent -> {
            dialogLayout.writeBean(user);

            userService.save(user);
            dialogLayout.refreshForm();

            dialog.close();
            grid.getDataProvider().refreshAll();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        dialog.getFooter().add(saveButton);

        dialog.addDialogCloseActionListener(event -> {
            dialogLayout.refreshForm();
            dialog.close();
        });*/

        return dialog;
    }
}
