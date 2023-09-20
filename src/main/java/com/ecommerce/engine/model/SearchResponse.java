package com.ecommerce.engine.model;

import java.util.List;
import java.util.Set;

public record SearchResponse<D>(
        int page, int size, int number, int totalNumber, Set<Sort> sorts, Set<Filter> filters, List<D> data) {

    public SearchResponse(SearchRequest searchRequest, int number, int totalNumber, List<D> mappedEntities) {
        this(
                searchRequest.page(),
                searchRequest.size(),
                number,
                totalNumber,
                searchRequest.sorts(),
                searchRequest.filters(),
                mappedEntities);
    }
}
