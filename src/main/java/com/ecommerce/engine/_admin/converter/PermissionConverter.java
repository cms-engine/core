package com.ecommerce.engine._admin.converter;

import com.ecommerce.engine._admin.enumeration.Permission;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PermissionConverter implements AttributeConverter<Permission, String> {

    @Override
    public String convertToDatabaseColumn(Permission attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    @Override
    public Permission convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Permission.valueOf(dbData.toUpperCase());
    }
}
