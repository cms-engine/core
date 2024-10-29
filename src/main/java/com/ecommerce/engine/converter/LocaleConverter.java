package com.ecommerce.engine.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Locale;
import org.apache.commons.lang3.LocaleUtils;

@Converter(autoApply = true)
public class LocaleConverter implements AttributeConverter<Locale, String> {
    @Override
    public String convertToDatabaseColumn(Locale attribute) {
        return attribute == null ? null : attribute.toString().replace("_", "-");
    }

    @Override
    public Locale convertToEntityAttribute(String dbData) {
        return dbData == null ? null : LocaleUtils.toLocale(dbData);
    }
}
