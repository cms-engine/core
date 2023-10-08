package com.ecommerce.engine.search;

import java.util.List;
import java.util.UUID;

public record SearchResponse<D>(
        UUID id,
        int page,
        int size,
        int number,
        int totalNumber,
        List<Sort> sorts,
        List<Filter> filters,
        List<D> data) {

    public SearchResponse(UUID id, SearchRequest searchRequest, int number, int totalNumber, List<D> mappedEntities) {
        this(
                id,
                searchRequest.page(),
                searchRequest.size(),
                number,
                totalNumber,
                searchRequest.sorts(),
                searchRequest.filters(),
                mappedEntities);
    }
}
