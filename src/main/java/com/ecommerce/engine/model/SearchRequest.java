package com.ecommerce.engine.model;

import com.vaadin.flow.data.provider.SortDirection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.function.Function;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchRequest {

    int page;
    int size;
    List<Sort> sorts;
    List<Filter> filters;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class Sort {
        String field;
        SortDirection order;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class Filter {
        String field;
        FilterType type;
        Object value;
        Function<Object, Object> convertFunction = Function.identity();
        public Filter(String field, FilterType type, Object value) {
            this.field = field;
            this.type = type;
            this.value = value;
        }
    }
}
