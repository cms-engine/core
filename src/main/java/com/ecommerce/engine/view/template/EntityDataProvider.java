package com.ecommerce.engine.view.template;

import com.ecommerce.engine.model.SearchRequest;
import com.ecommerce.engine.service.SearchService;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.function.ValueProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Stream;

public abstract class EntityDataProvider<T> extends CallbackDataProvider<T, List<SearchRequest.Filter>> {

    public EntityDataProvider(SearchService<T> searchService, ValueProvider<T, Object> identifierGetter, Class<T> aClass) {
        super(new EntityFetchCallback<>(searchService, aClass), new EntityCountCallback<>(searchService, aClass), identifierGetter);
    }

    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class EntityFetchCallback<T> implements FetchCallback<T, List<SearchRequest.Filter>> {

        SearchService<T> searchService;
        Class<T> aClass;

        @Override
        public Stream<T> fetch(Query<T, List<SearchRequest.Filter>> query) {
            var sorts = query.getSortOrders().stream().map(so -> new SearchRequest.Sort(so.getSorted(), so.getDirection())).toList();

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setPage(query.getPage());
            searchRequest.setSize(query.getPageSize());
            searchRequest.setSorts(sorts);
            query.getFilter().ifPresent(searchRequest::setFilters);

            return searchService.search(searchRequest, aClass).stream();
        }
    }

    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class EntityCountCallback<T> implements CountCallback<T, List<SearchRequest.Filter>> {

        SearchService<T> searchService;
        Class<T> aClass;

        @Override
        public int count(Query<T, List<SearchRequest.Filter>> query) {
            SearchRequest searchRequest = new SearchRequest();
            query.getFilter().ifPresent(searchRequest::setFilters);

            return searchService.totalCount(searchRequest, aClass);
        }
    }
}
