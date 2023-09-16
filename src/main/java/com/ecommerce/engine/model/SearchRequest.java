package com.ecommerce.engine.model;

import com.ecommerce.engine.enums.FilterType;
import com.ecommerce.engine.enums.SortDirection;

import java.util.List;

public record SearchRequest(int page, int size, List<Sort> sorts, List<Filter> filters) {

    public record Sort(String field, SortDirection order) {}

    public record Filter(String field, FilterType type, Object value) {}
}
