package org.textfiledatatools.util;

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

    public static Predicate<String> equalsPredicate(String compareValue) {
        Objects.requireNonNull(compareValue);
        return (String value) -> compare(value, EQUALS, compareValue);
    }

    public static Predicate<String> equalsIgnoreCasePredicate(String compareValue) {
        Objects.requireNonNull(compareValue);
        return (String value) -> compare(value, EQUALS_IGNORE_CASE, compareValue);
    }

    public static Predicate<String> contentEqualsPredicate(String compareValue) {
        Objects.requireNonNull(compareValue);
        return (String value) -> compare(value, CONTENT_EQUALS, compareValue);
    }

    public static Predicate<String> containsPredicate(String substring) {
        Objects.requireNonNull(substring);
        return (String value) -> compare(value, CONTAINS, substring);
    }

    public static Predicate<String> startsWithPredicate(String prefix) {
        Objects.requireNonNull(prefix);
        return (String value) -> compare(value, STARTS_WITH, prefix);
    }

    public static Predicate<String> endsWithPredicate(String suffix) {
        Objects.requireNonNull(suffix);
        return (String value) -> compare(value, ENDS_WITH, suffix);
    }

    public static Predicate<String> matchesPredicate(String regex) {
        Objects.requireNonNull(regex);
        return (String value) -> compare(value, MATCHES, regex);
    }

    public static Predicate<String> generatePredicate(StringComparisonType stringComparisonType, String compareValue) {
        Objects.requireNonNull(stringComparisonType);
        Objects.requireNonNull(compareValue);
        return (String value) -> compare(value, stringComparisonType, compareValue);
    }

    public static boolean compare(String a, StringComparisonType stringComparisonType, String b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        switch (stringComparisonType) {
            case EQUALS:
                return a.equals(b);
            case EQUALS_IGNORE_CASE:
                return a.equalsIgnoreCase(b);
            case CONTENT_EQUALS:
                return a.contentEquals(b);
            case CONTAINS:
                return a.contains(b);
            case STARTS_WITH:
                return a.startsWith(b);
            case ENDS_WITH:
                return a.endsWith(b);
            case MATCHES:
                return a.matches(b);
            default:
                return false;
        }
    }

}