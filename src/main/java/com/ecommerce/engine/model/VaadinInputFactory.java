package com.ecommerce.engine.model;

import com.ecommerce.engine.annotation.VaadinEmail;
import com.ecommerce.engine.annotation.VaadinPassword;
import com.ecommerce.engine.view.TextUtils;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ecommerce.engine.model.FilterType.*;

public class VaadinInputFactory {

    private static final Collection<FilterType> STRING_FILTERS = List.of(EQUAL, NOT_EQUAL, IN, NOT_IN, LIKE, NOT_LIKE);
    private static final Collection<FilterType> NUMBER_FILTERS =
            List.of(EQUAL, NOT_EQUAL, IN, NOT_IN, GRATER_THAN, LESS_THAN, GRATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL);

    private static final Map<Class<?>, JavaClassData> INPUT_CLASSES_DATA = new HashMap<>();

    static {
        INPUT_CLASSES_DATA.put(String.class, new JavaClassData(TextField.class, STRING_FILTERS));
        INPUT_CLASSES_DATA.put(Integer.class, new JavaClassData(IntegerField.class, NUMBER_FILTERS));
        INPUT_CLASSES_DATA.put(BigDecimal.class, new JavaClassData(BigDecimalField.class, NUMBER_FILTERS));
        INPUT_CLASSES_DATA.put(LocalDate.class, new JavaClassData(DatePicker.class, NUMBER_FILTERS));
    }

    @SneakyThrows
    public static HasValue<?, ?> createVaadinInput(Field field) {
        Class<? extends HasValue<?, ?>> inputClass = getInputClass(field);
        if (inputClass == null) {
            return null;
        }
        Constructor<? extends HasValue<?, ?>> constructor = inputClass.getConstructor(String.class);

        String inputLabel = TextUtils.convertCamelCaseToNormalText(field.getName());
        return constructor.newInstance(inputLabel);
    }

    private static Class<? extends HasValue<?, ?>> getInputClass(Field field) {
        Class<?> type = field.getType();

        type = getWrapperIfPrimitive(type);

        if (String.class.isAssignableFrom(type)) {
            if (field.isAnnotationPresent(VaadinEmail.class)) {
                return EmailField.class;
            }
            if (field.isAnnotationPresent(VaadinPassword.class)) {
                return PasswordField.class;
            }
        }

        JavaClassData javaClassData = INPUT_CLASSES_DATA.get(type);
        return javaClassData == null ? null : javaClassData.getInputClass();
    }

    private static Class<?> getWrapperIfPrimitive(Class<?> fieldType) {
        if (!fieldType.isPrimitive()) {
            return fieldType;
        }

        String typeName = fieldType.getTypeName();

        return switch (typeName) {
            case "boolean" -> Boolean.class;
            case "byte" -> Byte.class;
            case "char" -> Character.class;
            case "short" -> Short.class;
            case "int" -> Integer.class;
            case "long" -> Long.class;
            case "float" -> Float.class;
            case "double" -> Double.class;
            default -> null;
        };
    }

    @Data
    @AllArgsConstructor
    public static class JavaClassData {
        private Class<? extends HasValue<?, ?>> inputClass;
        private Collection<FilterType> filterTypes;
    }
}
