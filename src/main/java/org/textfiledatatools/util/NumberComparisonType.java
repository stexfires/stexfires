package org.textfiledatatools.util;

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

    public static IntPredicate equalToIntPredicate(int compareValue) {
        return (int value) -> compareInt(value, EQUAL_TO, compareValue);
    }

    public static IntPredicate notEqualToIntPredicate(int compareValue) {
        return (int value) -> compareInt(value, NOT_EQUAL_TO, compareValue);
    }

    public static IntPredicate lessThanIntPredicate(int compareValue) {
        return (int value) -> compareInt(value, LESS_THAN, compareValue);
    }

    public static IntPredicate lessThanOrEqualToIntPredicate(int compareValue) {
        return (int value) -> compareInt(value, LESS_THAN_OR_EQUAL_TO, compareValue);
    }

    public static IntPredicate greaterThanOrEqualToIntPredicate(int compareValue) {
        return (int value) -> compareInt(value, GREATER_THAN_OR_EQUAL_TO, compareValue);
    }

    public static IntPredicate greaterThanIntPredicate(int compareValue) {
        return (int value) -> compareInt(value, GREATER_THAN, compareValue);
    }

    public static LongPredicate equalToLongPredicate(long compareValue) {
        return (long value) -> compareLong(value, EQUAL_TO, compareValue);
    }

    public static LongPredicate notEqualToLongPredicate(long compareValue) {
        return (long value) -> compareLong(value, NOT_EQUAL_TO, compareValue);
    }

    public static LongPredicate lessThanLongPredicate(long compareValue) {
        return (long value) -> compareLong(value, LESS_THAN, compareValue);
    }

    public static LongPredicate lessThanOrEqualToLongPredicate(long compareValue) {
        return (long value) -> compareLong(value, LESS_THAN_OR_EQUAL_TO, compareValue);
    }

    public static LongPredicate greaterThanOrEqualToLongPredicate(long compareValue) {
        return (long value) -> compareLong(value, GREATER_THAN_OR_EQUAL_TO, compareValue);
    }

    public static LongPredicate greaterThanLongPredicate(long compareValue) {
        return (long value) -> compareLong(value, GREATER_THAN, compareValue);
    }

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