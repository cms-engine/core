package com.ecommerce.engine.model;

import com.ecommerce.engine.config.exception_handler.ApplicationException;
import com.ecommerce.engine.config.exception_handler.ErrorCode;
import com.ecommerce.engine.enums.FilterType;
import com.ecommerce.engine.enums.SortDirection;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Map;

public record SearchRequest(@Min(0) int page, @Min(0) @Max(100) int size, List<Sort> sorts, List<Filter> filters) {

    public void validateSearchFieldsExisting(Map<String, SearchField> searchFields) {
        if (sorts != null) {
            sorts().forEach(sort -> getSearchFieldOrThrow(searchFields, sort.field));
        }
        if (filters != null) {
            filters().forEach(filter -> getSearchFieldOrThrow(searchFields, filter.field));
        }
    }

    private void getSearchFieldOrThrow(Map<String, SearchField> searchFields, String field) {
        SearchField searchField = searchFields.get(field);
        if (searchField == null) {
            throw new ApplicationException(
                    ErrorCode.SEARCH_FIELD_NOT_FOUND, "Search field '%s' was not found!".formatted(field));
        }
    }

    public record Sort(String field, SortDirection order) {}

    public record Filter(String field, FilterType type, Object value) {}
}
