package com.ecommerce.engine.view.template;

import com.ecommerce.engine.model.SearchRequest;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class FilterForm<T> extends Div {

    VerticalLayout inputLayout;

    public FilterForm(ConfigurableFilterDataProvider<T, Void, List<SearchRequest.Filter>> dataProvider) {
        VerticalLayout verticalLayout = new VerticalLayout();
        inputLayout = new VerticalLayout();

        Button filterButton = new Button("Apply", buttonClickEvent -> dataProvider.setFilter(createSearchFilters()));
        Button clearButton = new Button("Clear", buttonClickEvent -> dataProvider.setFilter(null));

        HorizontalLayout mainButtonsLayout = new HorizontalLayout(filterButton, clearButton);

        verticalLayout.setAlignSelf(FlexComponent.Alignment.END, mainButtonsLayout);

        verticalLayout.add(inputLayout, mainButtonsLayout);

        add(verticalLayout);
    }

    public abstract List<SearchRequest.Filter> createSearchFilters();

    public void addComponents(Component... components) {
        inputLayout.add(components);
    }
}
