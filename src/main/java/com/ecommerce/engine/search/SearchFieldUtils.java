package com.ecommerce.engine.search;

import static com.ecommerce.engine.enums.FilterType.EQUAL;
import static com.ecommerce.engine.enums.FilterType.GRATER_THAN;
import static com.ecommerce.engine.enums.FilterType.GRATER_THAN_OR_EQUAL;
import static com.ecommerce.engine.enums.FilterType.IN;
import static com.ecommerce.engine.enums.FilterType.LESS_THAN;
import static com.ecommerce.engine.enums.FilterType.LESS_THAN_OR_EQUAL;
import static com.ecommerce.engine.enums.FilterType.LIKE;
import static com.ecommerce.engine.enums.FilterType.NOT_EQUAL;
import static com.ecommerce.engine.enums.FilterType.NOT_IN;
import static com.ecommerce.engine.enums.FilterType.NOT_LIKE;

import com.ecommerce.engine.enums.FilterType;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SearchFieldUtils {
    public final Collection<FilterType> IN_FILTERS = List.of(NOT_IN, IN);
    public final Collection<FilterType> STRICT_FILTERS = List.of(EQUAL, NOT_EQUAL, IN, NOT_IN);
    public final Collection<FilterType> STRING_FILTERS = List.of(EQUAL, NOT_EQUAL, IN, NOT_IN, LIKE, NOT_LIKE);
    public final Collection<FilterType> NUMBER_FILTERS =
            List.of(EQUAL, NOT_EQUAL, IN, NOT_IN, GRATER_THAN, LESS_THAN, GRATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL);

    public <T extends Enum<T>> Function<String, Object> toEnumFunction(Class<T> enumClass) {
        return value -> Enum.valueOf(enumClass, value.toUpperCase());
    }

    public Function<String, Object> toInstantFunction() {
        return value -> Instant.ofEpochMilli(Long.parseLong(value));
    }
}
