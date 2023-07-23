package com.ecommerce.engine.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TextUtils {

    public static String camelToSpaceCase(String camelCase) {
        StringBuilder normalText = new StringBuilder();

        for (int i = 0; i < camelCase.length(); i++) {
            char currentChar = camelCase.charAt(i);

            // Check if the current character is uppercase
            if (Character.isUpperCase(currentChar)) {
                // Add a space before the uppercase character
                normalText.append(' ');
            }

            // Append the current character in lowercase
            normalText.append(Character.toLowerCase(currentChar));
        }

        // Capitalize the first letter of the normal text
        normalText.setCharAt(0, Character.toUpperCase(normalText.charAt(0)));

        return normalText.toString();
    }

    public static String camelToSnakeCase(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
