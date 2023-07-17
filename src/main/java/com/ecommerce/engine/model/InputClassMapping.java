package com.ecommerce.engine.model;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class InputClassMapping {
    private static final Map<Class<?>, Class<? extends HasValue<?, ?>>> INPUT_CLASSES = new HashMap<>();

    static {
        INPUT_CLASSES.put(
                String.class, TextField.class
                );
        INPUT_CLASSES.put(
                Integer.class, IntegerField.class
                );
        INPUT_CLASSES.put(
                LocalDate.class, DatePicker.class
                );
    }

    public static Class<? extends HasValue<?, ?>> getInputClass(Class<?> fieldClass) {
        return INPUT_CLASSES.get(fieldClass);
    }
}
