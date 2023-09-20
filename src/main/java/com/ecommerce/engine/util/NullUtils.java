package com.ecommerce.engine.util;

import jakarta.annotation.Nullable;
import java.util.function.Function;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NullUtils {

    public static <T, V> V nullable(@Nullable T object, Function<T, V> function) {
        return object == null ? null : function.apply(object);
    }
}
