package com.ecommerce.engine.view.template;

import com.ecommerce.engine.model.SearchRequest;
import com.ecommerce.engine.service.SearchService;
import com.ecommerce.engine.util.ReflectionUtils;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.Query;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Stream;

public class EntityDataProvider<T> extends CallbackDataProvider<T, List<SearchRequest.Filter>> {

    public EntityDataProvider(SearchService<T> searchService, Class<T> entityClass, Class<?> idClass) {
        super(new EntityFetchCallback<>(searchService, entityClass), new EntityCountCallback<>(searchService, entityClass), o -> ReflectionUtils.getEntityId(o, idClass));
    }

    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class EntityFetchCallback<T> implements FetchCallback<T, List<SearchRequest.Filter>> {

        SearchService<T> searchService;
        Class<T> entityClass;

        @Override
        public Stream<T> fetch(Query<T, List<SearchRequest.Filter>> query) {
            var sorts = query.getSortOrders().stream().map(so -> new SearchRequest.Sort(so.getSorted(), so.getDirection())).toList();

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setPage(query.getPage());
            searchRequest.setSize(query.getPageSize());
            searchRequest.setSorts(sorts);
            query.getFilter().ifPresent(searchRequest::setFilters);

            return searchService.search(searchRequest, entityClass).stream();
        }
    }

    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class EntityCountCallback<T> implements CountCallback<T, List<SearchRequest.Filter>> {

        SearchService<T> searchService;
        Class<T> entityClass;

        @Override
        public int count(Query<T, List<SearchRequest.Filter>> query) {
            SearchRequest searchRequest = new SearchRequest();
            query.getFilter().ifPresent(searchRequest::setFilters);

            return searchService.totalCount(searchRequest, entityClass);
        }
    }
}
