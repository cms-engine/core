package com.ecommerce.engine.validation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.commons.lang3.LocaleUtils;

public class LocaleDeserializer extends JsonDeserializer<Locale> {

    private static final String LOCALE_PATTERN = "[a-z]{2}_[A-Z]{2}";

    @Override
    public Locale deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode node = mapper.readTree(jp);
        String nodeText = node.asText();

        Pattern pattern = Pattern.compile(LOCALE_PATTERN);
        if (pattern.matcher(nodeText).matches()) {
            return LocaleUtils.toLocale(nodeText);
        } else {
            throw new InvalidFormatException(
                    jp, "Locale doesn't match the pattern %s".formatted(LOCALE_PATTERN), nodeText, Locale.class);
        }
    }
}
