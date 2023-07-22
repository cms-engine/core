package com.ecommerce.engine.view.template;

import com.ecommerce.engine.repository.entity.User;
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
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.function.Function;

public class ListForm<T, ID> extends VerticalLayout {

    public ListForm(ListCrudRepository<T, ID> ListCrudRepository, Class<T> entityClass, Function<T, ID> identifierGetter, EntityDataProvider<T> dataProvider, List<AddForm<User, Integer>> addForms, EditForm<T, ID> editForm, FilterDivComponent filterDivComponent) {
        setHeightFull();

        Grid<T> grid = new Grid<>(entityClass);

        ((GridMultiSelectionModel<?>) grid
                .setSelectionMode(Grid.SelectionMode.MULTI))
                .setSelectionColumnFrozen(true);
        grid.setColumnReorderingAllowed(true);

        grid.addComponentColumn(entity -> new Button(VaadinIcon.EDIT.create(), event ->
                getUI().ifPresent(ui -> ui.navigate(editForm.getClass(), identifierGetter.apply(entity))))).setFrozenToEnd(true).setTextAlign(ColumnTextAlign.CENTER);

        grid.getColumns().forEach(column -> column.setResizable(true).setAutoWidth(true));

        var configurableFilterDataProvider = dataProvider.withConfigurableFilter();
        grid.setItems(configurableFilterDataProvider);

        FilterDiv<T> filterDiv = new FilterDiv<>(configurableFilterDataProvider, filterDivComponent);

        filterDiv.setVisible(false);
        Button filter = new Button("Filter", VaadinIcon.FILTER.create(), event -> filterDiv.setVisible(!filterDiv.isVisible()));

        ConfirmDialog deleteConfirm = confirm(ListCrudRepository, grid);
        Button delete = new Button("Delete", VaadinIcon.MINUS.create(), buttonClickEvent -> {
            deleteConfirm.setText(String.format("%s items will be deleted. Are you sure?", grid.getSelectedItems().size()));
            deleteConfirm.open();
        });
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        AddForm<T, ID> addForm = resolveAddForm(addForms, entityClass);

        Dialog addNew = addNew(addForm, grid);
        Button create = new Button("Add", VaadinIcon.PLUS.create(), buttonClickEvent -> addNew.open());
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout horizontalLayout = new HorizontalLayout(delete, create, filter);
        //horizontalLayout.setWidthFull();
        //horizontalLayout.setJustifyContentMode(JustifyContentMode.END);
        setAlignSelf(Alignment.END, horizontalLayout);

        grid.setSizeFull();
        HorizontalLayout gridLayout = new HorizontalLayout(grid, filterDiv);
        gridLayout.setSizeFull();
        add(horizontalLayout, gridLayout);
    }

    @SuppressWarnings("unchecked")
    private AddForm<T, ID> resolveAddForm(List<AddForm<User, Integer>> addForms, Class<T> aClass) {
        for (AddForm<User, Integer> addForm : addForms) {
            if (aClass.equals(addForm.getEntityClass())) {
                return (AddForm<T, ID>) addForm;
            }
        }

        return null;
    }

    private ConfirmDialog confirm(ListCrudRepository<T, ID> ListCrudRepository, Grid<T> grid) {
        ConfirmDialog dialog = new ConfirmDialog();

        dialog.setHeader("Confirm delete");

        dialog.setCancelable(true);

        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");
        dialog.addConfirmListener(buttonClickEvent -> {
            ListCrudRepository.deleteAll(grid.getSelectedItems());
            grid.getDataProvider().refreshAll();
            grid.deselectAll();
        });

        return dialog;
    }

    private Dialog addNew(AddForm<T, ID> userAdd, Grid<T> grid) {
        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("New item");

        dialog.add(userAdd);

        Button closeButton = new Button(VaadinIcon.CLOSE.create(), event -> {
            userAdd.refreshForm();
            dialog.close();
        });
        dialog.getHeader().add(closeButton);

        Button saveButton = new Button("Save", buttonClickEvent -> {
            userAdd.saveEntity();

            userAdd.refreshForm();

            dialog.close();
            grid.getDataProvider().refreshAll();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialog.getFooter().add(saveButton);

        userAdd.saveButtonActiveListener(saveButton);

        dialog.addDialogCloseActionListener(event -> {
            userAdd.refreshForm();
            dialog.close();
        });

        return dialog;
    }
}
