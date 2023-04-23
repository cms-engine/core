package com.ecommerce.engine.service;


import com.ecommerce.engine.model.SearchRequest;

import java.util.List;

public interface SearchService<T> {

    List<T> search(SearchRequest searchRequest, Class<T> entityClass);

    int totalCount(SearchRequest searchRequest, Class<T> entityClass);
}
