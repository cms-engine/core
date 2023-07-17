package com.ecommerce.engine.model;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ecommerce.engine.model.FilterType.*;

public class InputClassMapping {

    private static final Collection<FilterType> STRING_FILTERS = List.of(EQUAL, NOT_EQUAL, IN, NOT_IN, LIKE, NOT_LIKE);
    private static final Collection<FilterType> NUMBER_FILTERS =
            List.of(EQUAL, NOT_EQUAL, IN, NOT_IN, GRATER_THAN, LESS_THAN, GRATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL);

    private static final Map<Class<?>, JavaClassData> INPUT_CLASSES = new HashMap<>();

    static {
        INPUT_CLASSES.put(String.class, new JavaClassData(TextField.class, STRING_FILTERS));
        INPUT_CLASSES.put(Integer.class, new JavaClassData(IntegerField.class, NUMBER_FILTERS));
        INPUT_CLASSES.put(BigDecimal.class, new JavaClassData(BigDecimalField.class, NUMBER_FILTERS));
        INPUT_CLASSES.put(LocalDate.class, new JavaClassData(DatePicker.class, NUMBER_FILTERS));
    }

    public static Class<? extends HasValue<?, ?>> getInputClass(Class<?> fieldClass) {
        JavaClassData javaClassData = INPUT_CLASSES.get(fieldClass);
        return javaClassData == null ? null : javaClassData.getInputClass();
    }

    @Data
    @AllArgsConstructor
    public static class JavaClassData {
        private Class<? extends HasValue<?, ?>> inputClass;
        private Collection<FilterType> filterTypes;
    }
}
