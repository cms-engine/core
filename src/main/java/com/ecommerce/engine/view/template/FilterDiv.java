package com.ecommerce.engine.view.template;

import com.ecommerce.engine.model.SearchRequest;
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
public class FilterDiv<T> extends Div {

    public FilterDiv(ConfigurableFilterDataProvider<T, Void, List<SearchRequest.Filter>> dataProvider, FilterDivComponent filterDivComponent) {
        VerticalLayout verticalLayout = new VerticalLayout();
        VerticalLayout inputLayout = new VerticalLayout();

        Button filterButton = new Button("Apply", buttonClickEvent -> dataProvider.setFilter(filterDivComponent.createSearchFilters()));
        Button clearButton = new Button("Clear", buttonClickEvent -> dataProvider.setFilter(null));

        HorizontalLayout mainButtonsLayout = new HorizontalLayout(filterButton, clearButton);

        verticalLayout.setAlignSelf(FlexComponent.Alignment.END, mainButtonsLayout);

        verticalLayout.add(inputLayout, mainButtonsLayout);

        add(verticalLayout);

        inputLayout.add(filterDivComponent.getComponents());
    }
}
