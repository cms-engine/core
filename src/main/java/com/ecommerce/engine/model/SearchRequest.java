package com.ecommerce.engine.model;

import com.ecommerce.engine.config.exception_handler.ApplicationException;
import com.ecommerce.engine.config.exception_handler.ErrorCode;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.Map;
import java.util.Set;

public record SearchRequest(
        @Min(0) int page,
        @Min(0) @Max(100) int size,
        @JsonSetter(nulls = Nulls.AS_EMPTY) Set<@Valid Sort> sorts,
        @JsonSetter(nulls = Nulls.AS_EMPTY) Set<@Valid Filter> filters) {

    public void validateSearchFieldsExisting(Map<String, SearchField> searchFields) {
        sorts().forEach(sort -> getSearchFieldOrThrow(searchFields, sort.field()));
        filters().forEach(filter -> getSearchFieldOrThrow(searchFields, filter.field()));
    }

    private void getSearchFieldOrThrow(Map<String, SearchField> searchFields, String field) {
        SearchField searchField = searchFields.get(field);
        if (searchField == null) {
            throw new ApplicationException(
                    ErrorCode.SEARCH_FIELD_NOT_FOUND, "Search field '%s' was not found!".formatted(field));
        }
    }
}
