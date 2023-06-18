package com.ecommerce.engine.service;

import com.ecommerce.engine.model.FilterType;
import com.ecommerce.engine.model.SearchRequest;
import com.vaadin.flow.data.provider.SortDirection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl<T> implements SearchService<T> {

    private final EntityManager entityManager;

    @Override
    public List<T> search(SearchRequest searchRequest, Class<T> entityClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);

        addFilters(searchRequest, criteriaBuilder, criteriaQuery, root);

        addSorts(searchRequest, criteriaBuilder, criteriaQuery, root);

        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(searchRequest.getPage() * searchRequest.getSize());
        query.setMaxResults(searchRequest.getSize());

        return query.getResultList();
    }

    @Override
    public int totalCount(SearchRequest searchRequest, Class<T> entityClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(entityClass);

        criteriaQuery.select(criteriaBuilder.count(root));

        addFilters(searchRequest, criteriaBuilder, criteriaQuery, root);

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);

        return query.getSingleResult().intValue();
    }

    private void addFilters(
            SearchRequest searchRequest,
            CriteriaBuilder criteriaBuilder,
            CriteriaQuery<?> criteriaQuery,
            Root<T> root) {
        Predicate predicate = criteriaBuilder.conjunction();

        var filters = searchRequest.getFilters();
        if (filters == null || filters.isEmpty()) {
            return;
        }

        var searchConsumer = new FilterQueryCriteriaConsumer(criteriaBuilder, root, predicate);
        filters.forEach(searchConsumer);

        criteriaQuery.where(searchConsumer.getPredicate());
    }

    private void addSorts(
            SearchRequest searchRequest,
            CriteriaBuilder criteriaBuilder,
            CriteriaQuery<T> criteriaQuery,
            Root<T> root) {
        var sorts = searchRequest.getSorts();
        if (sorts == null || sorts.isEmpty()) {
            return;
        }

        List<Order> orders = sorts.stream()
                .map(sort -> sort.getOrder() == SortDirection.DESCENDING
                        ? criteriaBuilder.desc(
                                getPath(root, sort.getField()))
                        : criteriaBuilder.asc(
                                getPath(root, sort.getField())))
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
        private final Root<T> root;
        private Predicate predicate;

        @Override
        public void accept(SearchRequest.Filter param) {
            if (param.getValue() == null) {
                return;
            }

            Object value = getConvertedValue(param);

            switch (param.getType()) {
                case EQUAL -> predicate = builder.and(predicate, builder.equal(getPath(param), param.getValue()));
                case NOT_EQUAL -> predicate =
                        builder.and(predicate, builder.notEqual(getPath(param), param.getValue()));
                case IN -> predicate = builder.and(predicate, getPath(param).in((Collection<?>) param.getValue()));
                case NOT_IN -> predicate =
                        builder.and(predicate, getPath(param).in((Collection<?>) param.getValue()).not());
                case LIKE -> predicate =
                        builder.and(predicate, builder.like(getPath(param), "%" + param.getValue() + "%"));
                case NOT_LIKE -> predicate =
                        builder.and(predicate, builder.notLike(getPath(param), "%" + param.getValue() + "%"));
                case GRATER_THAN -> buildComparePredicate(FilterType.GRATER_THAN, param, value);
                case GRATER_THAN_OR_EQUAL -> buildComparePredicate(FilterType.GRATER_THAN_OR_EQUAL, param, value);
                case LESS_THAN -> buildComparePredicate(FilterType.LESS_THAN, param, value);
                case LESS_THAN_OR_EQUAL -> buildComparePredicate(FilterType.LESS_THAN_OR_EQUAL, param, value);
            }
        }

        private void buildComparePredicate(
                FilterType filterType, SearchRequest.Filter param, Object value) {
            if (value instanceof Integer castedValue) {
                buildComparePredicate(filterType, param, castedValue);
            }
            if (value instanceof Long castedValue) {
                buildComparePredicate(filterType, param, castedValue);
            }
            if (value instanceof BigDecimal castedValue) {
                buildComparePredicate(filterType, param, castedValue);
            }
            if (value instanceof Instant castedValue) {
                buildComparePredicate(filterType, param, castedValue);
            }
        }

        private <K extends Comparable<K>> void buildComparePredicate(
                FilterType filterType, SearchRequest.Filter param, K value) {
            switch (filterType) {
                case GRATER_THAN -> predicate = builder.and(predicate, builder.greaterThan(getPath(param), value));
                case GRATER_THAN_OR_EQUAL -> predicate =
                        builder.and(predicate, builder.greaterThanOrEqualTo(getPath(param), value));
                case LESS_THAN -> predicate = builder.and(predicate, builder.lessThan(getPath(param), value));
                case LESS_THAN_OR_EQUAL -> predicate =
                        builder.and(predicate, builder.lessThanOrEqualTo(getPath(param), value));
                default -> throw new RuntimeException(
                        String.format("Can't build compare predicate for filter typ %s", filterType));
            }
        }

        private Object getConvertedValue(SearchRequest.Filter param) {
            return param.getValue() instanceof Collection<?> listParam
                    ? listParam.stream()
                            .map(originalValue -> getConvertedSimpleValue(param, originalValue))
                            .toList()
                    : getConvertedSimpleValue(param, param.getValue());
        }

        private Object getConvertedSimpleValue(SearchRequest.Filter param, Object originalValue) {
            return param.getConvertFunction().apply(originalValue);
        }

        private <Y> Path<Y> getPath(SearchRequest.Filter param) {
            return SearchServiceImpl.this.getPath(root, param.getField());
        }
    }
}
