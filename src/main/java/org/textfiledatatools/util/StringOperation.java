package org.textfiledatatools.util;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum StringOperation {
    LOWER_CASE,
    UPPER_CASE,
    TRIM_TO_NULL,
    TRIM_TO_EMPTY;

    private static final String EMPTY = "";

    public static Function<String, String> lowerCaseFunction() {
        return (String value) -> operate(LOWER_CASE, value);
    }

    public static Function<String, String> upperCaseFunction() {
        return (String value) -> operate(UPPER_CASE, value);
    }

    public static Function<String, String> trimToNullFunction() {
        return (String value) -> operate(TRIM_TO_NULL, value);
    }

    public static Function<String, String> trimToEmptyFunction() {
        return (String value) -> operate(TRIM_TO_EMPTY, value);
    }

    public static Function<String, String> lowerCaseFunction(Locale locale) {
        Objects.requireNonNull(locale);
        return (String value) -> operate(LOWER_CASE, value, locale);
    }

    public static Function<String, String> upperCaseFunction(Locale locale) {
        Objects.requireNonNull(locale);
        return (String value) -> operate(UPPER_CASE, value, locale);
    }

    public static Function<String, String> trimToNullFunction(Locale locale) {
        Objects.requireNonNull(locale);
        return (String value) -> operate(TRIM_TO_NULL, value, locale);
    }

    public static Function<String, String> trimToEmptyFunction(Locale locale) {
        Objects.requireNonNull(locale);
        return (String value) -> operate(TRIM_TO_EMPTY, value, locale);
    }

    public static Function<String, String> generateFunction(StringOperation stringOperation) {
        Objects.requireNonNull(stringOperation);
        return (String value) -> operate(stringOperation, value);
    }

    public static Function<String, String> generateFunction(StringOperation stringOperation, Locale locale) {
        Objects.requireNonNull(stringOperation);
        Objects.requireNonNull(locale);
        return (String value) -> operate(stringOperation, value, locale);
    }

    public static String operate(StringOperation stringOperation, String value) {
        return operate(stringOperation, value, null);
    }

    public static String operate(StringOperation stringOperation, String value, Locale locale) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(stringOperation);
        String result = null;
        switch (stringOperation) {
            case LOWER_CASE:
                if (value != null) {
                    if (locale != null) {
                        result = value.toLowerCase(locale);
                    } else {
                        result = value.toLowerCase();
                    }
                }
                break;
            case UPPER_CASE:
                if (value != null) {
                    if (locale != null) {
                        result = value.toUpperCase(locale);
                    } else {
                        result = value.toUpperCase();
                    }
                }
                break;
            case TRIM_TO_EMPTY:
                if (value != null) {
                    result = value.trim();
                } else {
                    result = EMPTY;
                }
                break;
            case TRIM_TO_NULL:
                if (value != null) {
                    result = value.trim();
                    if (result.isEmpty()) {
                        result = null;
                    }
                }
                break;
            default:
                result = value;
        }
        return result;
    }

}
