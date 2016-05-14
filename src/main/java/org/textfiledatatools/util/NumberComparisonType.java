package org.textfiledatatools.util;

import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;

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

    public static IntPredicate intPredicate(NumberComparisonType numberComparisonType, int compareValue) {
        Objects.requireNonNull(numberComparisonType);
        return value -> compareInt(value, numberComparisonType, compareValue);
    }

    public static LongPredicate longPredicate(NumberComparisonType numberComparisonType, long compareValue) {
        Objects.requireNonNull(numberComparisonType);
        return value -> compareLong(value, numberComparisonType, compareValue);
    }

    private static boolean compareInt(int value, NumberComparisonType numberComparisonType, int compareValue) {
        Objects.requireNonNull(numberComparisonType);
        switch (numberComparisonType) {
            case EQUAL_TO:
                return value == compareValue;
            case NOT_EQUAL_TO:
                return value != compareValue;
            case LESS_THAN:
                return value < compareValue;
            case LESS_THAN_OR_EQUAL_TO:
                return value <= compareValue;
            case GREATER_THAN_OR_EQUAL_TO:
                return value >= compareValue;
            case GREATER_THAN:
                return value > compareValue;
            default:
                return false;
        }
    }

    private static boolean compareLong(long value, NumberComparisonType numberComparisonType, long compareValue) {
        Objects.requireNonNull(numberComparisonType);
        switch (numberComparisonType) {
            case EQUAL_TO:
                return value == compareValue;
            case NOT_EQUAL_TO:
                return value != compareValue;
            case LESS_THAN:
                return value < compareValue;
            case LESS_THAN_OR_EQUAL_TO:
                return value <= compareValue;
            case GREATER_THAN_OR_EQUAL_TO:
                return value >= compareValue;
            case GREATER_THAN:
                return value > compareValue;
            default:
                return false;
        }
    }

    public IntPredicate intPredicate(int compareValue) {
        return value -> compareInt(value, this, compareValue);
    }

    public LongPredicate longPredicate(long compareValue) {
        return value -> compareLong(value, this, compareValue);
    }

    public boolean compare(int value, int compareValue) {
        return compareInt(value, this, compareValue);
    }

    public boolean compare(long value, long compareValue) {
        return compareLong(value, this, compareValue);
    }

}