package org.textfiledatatools.util;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum StringUnaryOperator {
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

    public static Function<String, String> generateFunction(StringUnaryOperator stringUnaryOperator) {
        Objects.requireNonNull(stringUnaryOperator);
        return (String value) -> operate(stringUnaryOperator, value, null);
    }

    public static Function<String, String> generateFunction(StringUnaryOperator stringUnaryOperator, Locale locale) {
        Objects.requireNonNull(stringUnaryOperator);
        Objects.requireNonNull(locale);
        return (String value) -> operate(stringUnaryOperator, value, locale);
    }

    public static String operate(StringUnaryOperator stringUnaryOperator, String value) {
        return operate(stringUnaryOperator, value, null);
    }

    public static String operate(StringUnaryOperator stringUnaryOperator, String value, Locale locale) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(stringUnaryOperator);
        String result = null;
        switch (stringUnaryOperator) {
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
