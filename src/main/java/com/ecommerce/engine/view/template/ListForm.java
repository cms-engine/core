package com.ecommerce.engine.view.template;

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
import org.springframework.data.repository.CrudRepository;

import java.util.function.Function;

public class ListForm<T, ID> extends VerticalLayout {

    public ListForm(CrudRepository<T, ID> crudRepository, Class<T> aClass, Function<T, ID> identifierGetter, EntityDataProvider<T> dataProvider, AddForm<T, ID> addForm, EditForm<T, ID> editForm, FilterDivComponent filterDivComponent) {
        setHeightFull();

        Grid<T> grid = new Grid<>(aClass);

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

        ConfirmDialog deleteConfirm = confirm(crudRepository, grid);
        Button delete = new Button("Delete", VaadinIcon.MINUS.create(), buttonClickEvent -> {
            deleteConfirm.setText(String.format("%s items will be deleted. Are you sure?", grid.getSelectedItems().size()));
            deleteConfirm.open();
        });
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

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

    private ConfirmDialog confirm(CrudRepository<T, ID> crudRepository, Grid<T> grid) {
        ConfirmDialog dialog = new ConfirmDialog();

        dialog.setHeader("Confirm delete");

        dialog.setCancelable(true);

        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");
        dialog.addConfirmListener(buttonClickEvent -> {
            crudRepository.deleteAll(grid.getSelectedItems());
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
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        dialog.getFooter().add(saveButton);

        userAdd.saveButtonActiveListener(saveButton);

        dialog.addDialogCloseActionListener(event -> {
            userAdd.refreshForm();
            dialog.close();
        });

        return dialog;
    }
}