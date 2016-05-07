package org.textfiledatatools.util;

import java.util.Objects;

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