package org.textfiledatatools.util;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum NumberComparisonType {
    EQUAL_TO,
    NOT_EQUAL_TO,
    LESS_THAN,
    LESS_THAN_OR_EQUAL_TO,
    GREATER_THAN_OR_EQUAL_TO,
    GREATER_THAN;

    public static boolean compareInt(int a, NumberComparisonType numberComparisonType, int b) {
        switch (numberComparisonType) {
            case EQUAL_TO:
                return a == b;
            case NOT_EQUAL_TO:
                return a != b;
            case LESS_THAN:
                return a < b;
            case LESS_THAN_OR_EQUAL_TO:
                return a <= b;
            case GREATER_THAN_OR_EQUAL_TO:
                return a >= b;
            case GREATER_THAN:
                return a > b;
            default:
                return false;
        }
    }

    public static boolean compareLong(long a, NumberComparisonType numberComparisonType, long b) {
        switch (numberComparisonType) {
            case EQUAL_TO:
                return a == b;
            case NOT_EQUAL_TO:
                return a != b;
            case LESS_THAN:
                return a < b;
            case LESS_THAN_OR_EQUAL_TO:
                return a <= b;
            case GREATER_THAN_OR_EQUAL_TO:
                return a >= b;
            case GREATER_THAN:
                return a > b;
            default:
                return false;
        }
    }

}