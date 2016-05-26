package stexfires.util;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum StringComparisonType {
    EQUALS,
    EQUALS_IGNORE_CASE,
    CONTENT_EQUALS,
    CONTAINS,
    STARTS_WITH,
    ENDS_WITH,
    MATCHES;

    public static Predicate<String> stringPredicate(StringComparisonType stringComparisonType, String compareValue) {
        Objects.requireNonNull(stringComparisonType);
        Objects.requireNonNull(compareValue);
        return value -> compare(value, stringComparisonType, compareValue);
    }

    private static boolean compare(String value, StringComparisonType stringComparisonType, String compareValue) {
        Objects.requireNonNull(stringComparisonType);
        Objects.requireNonNull(compareValue);
        if (value != null) {
            switch (stringComparisonType) {
                case EQUALS:
                    return value.equals(compareValue);
                case EQUALS_IGNORE_CASE:
                    return value.equalsIgnoreCase(compareValue);
                case CONTENT_EQUALS:
                    return value.contentEquals(compareValue);
                case CONTAINS:
                    return value.contains(compareValue);
                case STARTS_WITH:
                    return value.startsWith(compareValue);
                case ENDS_WITH:
                    return value.endsWith(compareValue);
                case MATCHES:
                    return value.matches(compareValue);
                default:
                    return false;
            }
        }
        return false;
    }

    public Predicate<String> stringPredicate(String compareValue) {
        Objects.requireNonNull(compareValue);
        return value -> compare(value, this, compareValue);
    }

    public boolean compare(String value, String compareValue) {
        Objects.requireNonNull(compareValue);
        return compare(value, this, compareValue);
    }

}