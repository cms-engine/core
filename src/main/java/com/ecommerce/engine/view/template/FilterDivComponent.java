package com.ecommerce.engine.view.template;

import com.ecommerce.engine.model.SearchRequest;
import com.vaadin.flow.component.Component;

import java.util.Collection;
import java.util.List;

public interface FilterDivComponent {

    Collection<Component> getComponents();
    List<SearchRequest.Filter> createSearchFilters();
}
