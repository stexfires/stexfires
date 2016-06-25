package stexfires.util;

import java.util.Locale;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum StringUnaryOperatorType {
    LOWER_CASE,
    UPPER_CASE,
    TRIM_TO_NULL,
    TRIM_TO_EMPTY,
    REMOVE_SPACE,
    REVERSE;

    private static final String EMPTY = "";

    public static UnaryOperator<String> prefix(String prefix) {
        Objects.requireNonNull(prefix);
        return value -> value == null ? prefix : prefix + value;
    }

    public static UnaryOperator<String> postfix(String postfix) {
        Objects.requireNonNull(postfix);
        return value -> value == null ? postfix : value + postfix;
    }

    public static UnaryOperator<String> stringUnaryOperator(StringUnaryOperatorType stringUnaryOperatorType) {
        Objects.requireNonNull(stringUnaryOperatorType);
        return value -> operate(stringUnaryOperatorType, value, null);
    }

    public static UnaryOperator<String> stringUnaryOperator(StringUnaryOperatorType stringUnaryOperatorType, Locale locale) {
        Objects.requireNonNull(stringUnaryOperatorType);
        Objects.requireNonNull(locale);
        return value -> operate(stringUnaryOperatorType, value, locale);
    }

    private static String operate(StringUnaryOperatorType stringUnaryOperatorType, String value) {
        return operate(stringUnaryOperatorType, value, null);
    }

    private static String operate(StringUnaryOperatorType stringUnaryOperatorType, String value, Locale locale) {
        Objects.requireNonNull(stringUnaryOperatorType);
        String result = null;
        switch (stringUnaryOperatorType) {
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
            case REMOVE_SPACE:
                if (value != null) {
                    result = result.replaceAll("\\s", "");
                }
                break;
            case REVERSE:
                if (value != null) {
                    result = new StringBuilder(value).reverse().toString();
                }
                break;
            default:
                result = value;
        }
        return result;
    }

    public UnaryOperator<String> stringUnaryOperator() {
        return value -> operate(this, value, null);
    }

    public UnaryOperator<String> stringUnaryOperator(Locale locale) {
        Objects.requireNonNull(locale);
        return value -> operate(this, value, locale);
    }

    public String operate(String value) {
        return operate(this, value);
    }

    public String operate(String value, Locale locale) {
        Objects.requireNonNull(locale);
        return operate(this, value, locale);
    }

}
