package com.ecommerce.engine.service;

import com.ecommerce.engine.config.exception_handler.ApplicationException;
import com.ecommerce.engine.config.exception_handler.ErrorCode;
import com.ecommerce.engine.enums.FilterType;
import com.ecommerce.engine.enums.SearchEntity;
import com.ecommerce.engine.enums.SortDirection;
import com.ecommerce.engine.model.SearchField;
import com.ecommerce.engine.model.SearchRequest;
import com.ecommerce.engine.model.SearchResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class SearchService<E, D> {

    private final EntityManager entityManager;

    public SearchResponse<D> search(
            SearchRequest searchRequest, SearchEntity searchEntity, Class<E> entityClass, Function<E, D> mapper) {
        searchRequest.validateSearchFieldsExisting(searchEntity.getSearchFields());

        List<E> entities = fetchEntities(searchRequest, searchEntity, entityClass);
        int totalNumber = totalNumber(searchRequest, searchEntity, entityClass);

        List<D> mappedEntities = entities.stream().map(mapper).toList();

        return new SearchResponse<>(searchRequest.page(), entities.size(), totalNumber, mappedEntities);
    }

    public List<E> fetchEntities(SearchRequest searchRequest, SearchEntity searchEntity, Class<E> entityClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<E> root = criteriaQuery.from(entityClass);

        addFilters(searchEntity, searchRequest, criteriaBuilder, criteriaQuery, root);

        addSorts(searchRequest, criteriaBuilder, criteriaQuery, root);

        TypedQuery<E> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(searchRequest.page() * searchRequest.size());
        query.setMaxResults(searchRequest.size());

        return query.getResultList();
    }

    public int totalNumber(SearchRequest searchRequest, SearchEntity searchEntity, Class<E> entityClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<E> root = criteriaQuery.from(entityClass);

        criteriaQuery.select(criteriaBuilder.count(root));

        addFilters(searchEntity, searchRequest, criteriaBuilder, criteriaQuery, root);

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);

        return query.getSingleResult().intValue();
    }

    private void addFilters(
            SearchEntity searchEntity,
            SearchRequest searchRequest,
            CriteriaBuilder criteriaBuilder,
            CriteriaQuery<?> criteriaQuery,
            Root<?> root) {
        Predicate predicate = criteriaBuilder.conjunction();

        var filters = searchRequest.filters();
        if (CollectionUtils.isEmpty(filters)) {
            return;
        }

        var searchConsumer = new FilterQueryCriteriaConsumer(criteriaBuilder, root, predicate, searchEntity);
        filters.forEach(searchConsumer);

        criteriaQuery.where(searchConsumer.getPredicate());
    }

    private void addSorts(
            SearchRequest searchRequest,
            CriteriaBuilder criteriaBuilder,
            CriteriaQuery<?> criteriaQuery,
            Root<?> root) {
        var sorts = searchRequest.sorts();
        if (CollectionUtils.isEmpty(sorts)) {
            return;
        }

        List<Order> orders = sorts.stream()
                .map(sort -> sort.order() == SortDirection.DESCENDING
                        ? criteriaBuilder.desc(getPath(root, sort.field()))
                        : criteriaBuilder.asc(getPath(root, sort.field())))
                .toList();

        criteriaQuery.orderBy(orders);
    }

    private <Y> Path<Y> getPath(Root<?> root, String stringPath) {
        String[] fields = stringPath.split("\\.");

        Path<Y> path = root.get(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            path = path.get(fields[i]);
        }

        return path;
    }

    @Data
    @AllArgsConstructor
    private class FilterQueryCriteriaConsumer implements Consumer<SearchRequest.Filter> {

        private final CriteriaBuilder builder;
        private final Root<?> root;
        private Predicate predicate;
        private SearchEntity searchEntity;

        @Override
        public void accept(SearchRequest.Filter param) {
            if (param.value() == null) {
                return;
            }

            SearchField searchField = getSearchField(param);

            Object value = getConvertedValue(param, searchField);

            switch (param.type()) {
                case EQUAL -> predicate = builder.and(predicate, builder.equal(getPath(searchField), param.value()));
                case NOT_EQUAL -> predicate =
                        builder.and(predicate, builder.notEqual(getPath(searchField), param.value()));
                case IN -> predicate =
                        builder.and(predicate, getPath(searchField).in((Collection<?>) param.value()));
                case NOT_IN -> predicate = builder.and(
                        predicate,
                        getPath(searchField).in((Collection<?>) param.value()).not());
                case LIKE -> predicate =
                        builder.and(predicate, builder.like(getPath(searchField), "%" + param.value() + "%"));
                case NOT_LIKE -> predicate =
                        builder.and(predicate, builder.notLike(getPath(searchField), "%" + param.value() + "%"));
                case GRATER_THAN -> buildComparePredicate(FilterType.GRATER_THAN, searchField, value);
                case GRATER_THAN_OR_EQUAL -> buildComparePredicate(FilterType.GRATER_THAN_OR_EQUAL, searchField, value);
                case LESS_THAN -> buildComparePredicate(FilterType.LESS_THAN, searchField, value);
                case LESS_THAN_OR_EQUAL -> buildComparePredicate(FilterType.LESS_THAN_OR_EQUAL, searchField, value);
            }
        }

        private SearchField getSearchField(SearchRequest.Filter param) {
            SearchField searchField = searchEntity.getSearchFields().get(param.field());
            if (!searchField.allowedFilterTypes().contains(param.type())) {
                throw new ApplicationException(
                        ErrorCode.NOT_ALLOWED_FILTER, "Not allowed filter type for field %s!".formatted(param.field()));
            }

            return searchField;
        }

        private void buildComparePredicate(FilterType filterType, SearchField searchField, Object value) {
            if (value instanceof Integer castedValue) {
                buildComparePredicate(filterType, searchField, castedValue);
            }
            if (value instanceof Long castedValue) {
                buildComparePredicate(filterType, searchField, castedValue);
            }
            if (value instanceof BigDecimal castedValue) {
                buildComparePredicate(filterType, searchField, castedValue);
            }
            if (value instanceof Instant castedValue) {
                buildComparePredicate(filterType, searchField, castedValue);
            }
        }

        private <K extends Comparable<K>> void buildComparePredicate(
                FilterType filterType, SearchField searchField, K value) {
            switch (filterType) {
                case GRATER_THAN -> predicate =
                        builder.and(predicate, builder.greaterThan(getPath(searchField), value));
                case GRATER_THAN_OR_EQUAL -> predicate =
                        builder.and(predicate, builder.greaterThanOrEqualTo(getPath(searchField), value));
                case LESS_THAN -> predicate = builder.and(predicate, builder.lessThan(getPath(searchField), value));
                case LESS_THAN_OR_EQUAL -> predicate =
                        builder.and(predicate, builder.lessThanOrEqualTo(getPath(searchField), value));
                default -> throw new RuntimeException(
                        String.format("Can't build compare predicate for filter typ %s", filterType));
            }
        }

        private Object getConvertedValue(SearchRequest.Filter param, SearchField searchField) {
            return param.value() instanceof Collection<?> listParam
                    ? listParam.stream()
                            .map(originalValue -> getConvertedSimpleValue(searchField, originalValue))
                            .toList()
                    : getConvertedSimpleValue(searchField, param.value());
        }

        private Object getConvertedSimpleValue(SearchField searchField, Object originalValue) {
            return searchField.convertFunction().apply(originalValue);
        }

        private <Y> Path<Y> getPath(SearchField searchField) {
            return SearchService.this.getPath(root, searchField.path());
        }
    }
}
