package com.ecommerce.engine.view.template;

import com.ecommerce.engine.util.ReflectionUtils;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.ListCrudRepository;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ListForm<T, ID> extends VerticalLayout {

    @Getter
    String tableName;

    public ListForm(ListCrudRepository<T, ID> listCrudRepository,
                    EntityDataProvider<T> dataProvider,
                    AddForm<T, ID> addForm,
                    FilterDivComponent filterDivComponent,
                    Class<T> entityClass,
                    Class<ID> idClass, String tableName) {
        this.tableName = tableName;

        setHeightFull();

        Grid<T> grid = new Grid<>(entityClass);

        ((GridMultiSelectionModel<?>) grid
                .setSelectionMode(Grid.SelectionMode.MULTI))
                .setSelectionColumnFrozen(true);
        grid.setColumnReorderingAllowed(true);

        grid.addComponentColumn(entity -> new Button(VaadinIcon.EDIT.create(), event ->
                getUI().ifPresent(ui -> ui.navigate(tableName + "/" + ReflectionUtils.getEntityId(entity, idClass))))).setFrozenToEnd(true).setTextAlign(ColumnTextAlign.CENTER);

        grid.getColumns().forEach(column -> column.setResizable(true).setAutoWidth(true));

        var configurableFilterDataProvider = dataProvider.withConfigurableFilter();
        grid.setItems(configurableFilterDataProvider);

        FilterDiv<T> filterDiv = new FilterDiv<>(configurableFilterDataProvider, filterDivComponent);

        filterDiv.setVisible(false);
        Button filter = new Button("Filter", VaadinIcon.FILTER.create(), event -> filterDiv.setVisible(!filterDiv.isVisible()));

        ConfirmDialog deleteConfirm = confirm(listCrudRepository, grid);
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

    private Dialog addNew(AddForm<T, ID> addForm, Grid<T> grid) {
        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("New item");

        dialog.add(addForm);

        Button closeButton = new Button(VaadinIcon.CLOSE.create(), event -> {
            addForm.refreshForm();
            dialog.close();
        });
        dialog.getHeader().add(closeButton);

        Button saveButton = new Button("Save", buttonClickEvent -> {
            addForm.saveEntity();

            addForm.refreshForm();

            dialog.close();
            grid.getDataProvider().refreshAll();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialog.getFooter().add(saveButton);

        addForm.saveButtonActiveListener(saveButton);

        dialog.addDialogCloseActionListener(event -> {
            addForm.refreshForm();
            dialog.close();
        });

        return dialog;
    }
}
