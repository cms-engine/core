package com.ecommerce.engine.search;

import com.ecommerce.engine.config.exception.ApplicationException;
import com.ecommerce.engine.config.exception.ErrorCode;
import com.ecommerce.engine.entity.SearchRequestCache;
import com.ecommerce.engine.enums.FilterType;
import com.ecommerce.engine.enums.SortDirection;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.SearchRequestCacheRepository;
import com.ecommerce.engine.util.TranslationUtils;
import jakarta.annotation.Nullable;
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
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService<E, D> {

    private final EntityManager entityManager;
    private final SearchRequestCacheRepository searchRequestCacheRepository;

    public SearchResponse<D> search(
            @Nullable UUID id,
            @Nullable SearchRequest searchRequest,
            SearchEntity searchEntity,
            Class<E> entityClass,
            Function<E, D> mapper) {
        if (id == null && searchRequest == null) {
            throw new NullPointerException();
        }
        return id == null
                ? search(searchRequest, searchEntity, entityClass, mapper)
                : search(id, searchEntity, entityClass, mapper);
    }

    public SearchResponse<D> search(UUID id, SearchEntity searchEntity, Class<E> entityClass, Function<E, D> mapper) {
        SearchRequest searchRequest = restoreSearchRequest(id);

        try {
            searchRequest.validateSearchFieldsExisting(searchEntity.getSearchFields());

            List<E> entities = fetchEntities(searchRequest, searchEntity, entityClass);
            int totalNumber = totalNumber(searchRequest, searchEntity, entityClass);

            List<D> mappedEntities = entities.stream().map(mapper).toList();

            return new SearchResponse<>(id, searchRequest, entities.size(), totalNumber, mappedEntities);
        } catch (Exception e) {
            deleteSearchRequest(id);
            throw new ApplicationException(
                    ErrorCode.INVALID_SEARCH_REQUEST,
                    TranslationUtils.getMessage("exception.invalidSearchRequest")
                            .formatted(id),
                    e);
        }
    }

    public SearchResponse<D> search(
            SearchRequest searchRequest, SearchEntity searchEntity, Class<E> entityClass, Function<E, D> mapper) {
        searchRequest.validateSearchFieldsExisting(searchEntity.getSearchFields());

        List<E> entities = fetchEntities(searchRequest, searchEntity, entityClass);
        int totalNumber = totalNumber(searchRequest, searchEntity, entityClass);

        List<D> mappedEntities = entities.stream().map(mapper).toList();

        UUID id = UUID.randomUUID();
        saveSearchRequest(id, searchRequest);

        return new SearchResponse<>(id, searchRequest, entities.size(), totalNumber, mappedEntities);
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
        if (filters.isEmpty()) {
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
        if (sorts.isEmpty()) {
            return;
        }

        List<Order> orders = sorts.stream()
                .map(sort -> sort.order() == SortDirection.DESC
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

    private SearchRequest restoreSearchRequest(UUID id) {
        SearchRequestCache searchRequestCache = searchRequestCacheRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("search request", id));
        return searchRequestCache.getSearchRequest();
    }

    private void saveSearchRequest(UUID id, SearchRequest searchRequest) {
        SearchRequestCache searchRequestCache = new SearchRequestCache(id, searchRequest);
        searchRequestCacheRepository.save(searchRequestCache);
    }

    private void deleteSearchRequest(UUID id) {
        searchRequestCacheRepository.deleteById(id);
    }

    @Data
    @AllArgsConstructor
    private class FilterQueryCriteriaConsumer implements Consumer<Filter> {

        private final CriteriaBuilder builder;
        private final Root<?> root;
        private Predicate predicate;
        private SearchEntity searchEntity;

        @Override
        public void accept(Filter param) {
            SearchField searchField = getSearchField(param);

            List<?> valueList = getConvertedValue(param, searchField);
            Object singleValue = valueList.get(1);

            switch (param.type()) {
                case EQUAL -> predicate = builder.and(predicate, builder.equal(getPath(searchField), singleValue));
                case NOT_EQUAL -> predicate =
                        builder.and(predicate, builder.notEqual(getPath(searchField), singleValue));
                case IN -> predicate =
                        builder.and(predicate, getPath(searchField).in(valueList));
                case NOT_IN -> predicate = builder.and(
                        predicate, getPath(searchField).in(valueList).not());
                case LIKE -> predicate =
                        builder.and(predicate, builder.like(getPath(searchField), "%" + singleValue + "%"));
                case NOT_LIKE -> predicate =
                        builder.and(predicate, builder.notLike(getPath(searchField), "%" + singleValue + "%"));
                case GRATER_THAN -> buildComparePredicate(FilterType.GRATER_THAN, searchField, singleValue);
                case GRATER_THAN_OR_EQUAL -> buildComparePredicate(
                        FilterType.GRATER_THAN_OR_EQUAL, searchField, singleValue);
                case LESS_THAN -> buildComparePredicate(FilterType.LESS_THAN, searchField, singleValue);
                case LESS_THAN_OR_EQUAL -> buildComparePredicate(
                        FilterType.LESS_THAN_OR_EQUAL, searchField, singleValue);
            }
        }

        private SearchField getSearchField(Filter param) {
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

        private List<Object> getConvertedValue(Filter param, SearchField searchField) {
            return param.value().stream()
                    .map(originalValue -> searchField.convertFunction().apply(originalValue))
                    .toList();
        }

        private <Y> Path<Y> getPath(SearchField searchField) {
            return SearchService.this.getPath(root, searchField.path());
        }
    }
}
