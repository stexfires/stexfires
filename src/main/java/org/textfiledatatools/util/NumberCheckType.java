package org.textfiledatatools.util;

import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum NumberCheckType {
    NEGATIVE,
    POSITIVE,
    ZERO,
    NOT_ZERO,
    ODD,
    EVEN,
    MAX_VALUE,
    MIN_VALUE;

    public static IntPredicate intPredicate(NumberCheckType numberCheckType) {
        Objects.requireNonNull(numberCheckType);
        return value -> checkInt(value, numberCheckType);
    }

    public static LongPredicate longPredicate(NumberCheckType numberCheckType) {
        Objects.requireNonNull(numberCheckType);
        return value -> checkLong(value, numberCheckType);
    }

    private static boolean checkInt(int value, NumberCheckType numberCheckType) {
        Objects.requireNonNull(numberCheckType);
        switch (numberCheckType) {
            case NEGATIVE:
                return value < 0;
            case POSITIVE:
                return value > 0;
            case ZERO:
                return value == 0;
            case NOT_ZERO:
                return value != 0;
            case ODD:
                return (value & 1) == 1;
            case EVEN:
                return (value & 1) == 0;
            case MAX_VALUE:
                return value == Integer.MAX_VALUE;
            case MIN_VALUE:
                return value == Integer.MIN_VALUE;
            default:
                return false;
        }
    }

    private static boolean checkLong(long value, NumberCheckType numberCheckType) {
        Objects.requireNonNull(numberCheckType);
        switch (numberCheckType) {
            case NEGATIVE:
                return value < 0;
            case POSITIVE:
                return value > 0;
            case ZERO:
                return value == 0;
            case NOT_ZERO:
                return value != 0;
            case ODD:
                return (value & 1) == 1;
            case EVEN:
                return (value & 1) == 0;
            case MAX_VALUE:
                return value == Long.MAX_VALUE;
            case MIN_VALUE:
                return value == Long.MIN_VALUE;
            default:
                return false;
        }
    }

    public IntPredicate intPredicate() {
        return value -> checkInt(value, this);
    }

    public LongPredicate longPredicate() {
        return value -> checkLong(value, this);
    }

    public boolean check(int value) {
        return checkInt(value, this);
    }

    public boolean check(long value) {
        return checkLong(value, this);
    }

}
