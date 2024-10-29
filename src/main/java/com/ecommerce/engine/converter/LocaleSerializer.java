package com.ecommerce.engine.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Locale;

public class LocaleSerializer extends JsonSerializer<Locale> {
    @Override
    public void serialize(Locale locale, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        if (locale == null) {
            jgen.writeNull();
        } else {
            jgen.writeString(locale.toString().replace("_", "-"));
        }
    }
}
