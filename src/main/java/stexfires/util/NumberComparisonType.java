package stexfires.util;

import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

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
    GREATER_THAN,
    MULTIPLE_OF,
    SAME_SIGN,
    SAME_ABSOLUTE_VALUE;

    public static IntPredicate intPredicate(NumberComparisonType numberComparisonType, int compareValue) {
        Objects.requireNonNull(numberComparisonType);
        return value -> compareIntInternal(value, numberComparisonType, compareValue);
    }

    public static LongPredicate longPredicate(NumberComparisonType numberComparisonType, long compareValue) {
        Objects.requireNonNull(numberComparisonType);
        return value -> compareLongInternal(value, numberComparisonType, compareValue);
    }

    public static Predicate<BigInteger> bigIntegerPredicate(NumberComparisonType numberComparisonType, BigInteger compareValue) {
        Objects.requireNonNull(numberComparisonType);
        Objects.requireNonNull(compareValue);
        return value -> compareBigIntegerInternal(value, numberComparisonType, compareValue);
    }

    private static boolean compareIntInternal(int value, NumberComparisonType numberComparisonType, int compareValue) {
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
            case MULTIPLE_OF:
                return (compareValue != 0) && (value % compareValue == 0);
            case SAME_SIGN:
                return (value > 0 && compareValue > 0) || (value == 0 && compareValue == 0) || (value < 0 && compareValue < 0);
            case SAME_ABSOLUTE_VALUE:
                return Math.abs(value) == Math.abs(compareValue);
        }
        return false;
    }

    private static boolean compareLongInternal(long value, NumberComparisonType numberComparisonType, long compareValue) {
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
            case MULTIPLE_OF:
                return (compareValue != 0L) && (value % compareValue == 0L);
            case SAME_SIGN:
                return (value > 0L && compareValue > 0L) || (value == 0L && compareValue == 0L) || (value < 0L && compareValue < 0L);
            case SAME_ABSOLUTE_VALUE:
                return Math.abs(value) == Math.abs(compareValue);
        }
        return false;
    }

    private static boolean compareBigIntegerInternal(@Nullable BigInteger value, NumberComparisonType numberComparisonType, BigInteger compareValue) {
        Objects.requireNonNull(numberComparisonType);
        Objects.requireNonNull(compareValue);
        if (value != null) {
            switch (numberComparisonType) {
                case EQUAL_TO:
                    return value.compareTo(compareValue) == 0;
                case NOT_EQUAL_TO:
                    return value.compareTo(compareValue) != 0;
                case LESS_THAN:
                    return value.compareTo(compareValue) < 0;
                case LESS_THAN_OR_EQUAL_TO:
                    return value.compareTo(compareValue) <= 0;
                case GREATER_THAN_OR_EQUAL_TO:
                    return value.compareTo(compareValue) >= 0;
                case GREATER_THAN:
                    return value.compareTo(compareValue) > 0;
                case MULTIPLE_OF:
                    return (compareValue.signum() != 0) && (value.mod(compareValue).compareTo(BigInteger.ZERO) == 0);
                case SAME_SIGN:
                    return value.signum() == compareValue.signum();
                case SAME_ABSOLUTE_VALUE:
                    return value.abs().compareTo(compareValue.abs()) == 0;
            }
        }
        return false;
    }

    public final IntPredicate intPredicate(int compareValue) {
        return value -> compareIntInternal(value, this, compareValue);
    }

    public final LongPredicate longPredicate(long compareValue) {
        return value -> compareLongInternal(value, this, compareValue);
    }

    public final Predicate<BigInteger> bigIntegerPredicate(@Nullable BigInteger compareValue) {
        Objects.requireNonNull(compareValue);
        return value -> compareBigIntegerInternal(value, this, compareValue);
    }

    public final boolean compareInt(int value, int compareValue) {
        return compareIntInternal(value, this, compareValue);
    }

    public final boolean compareLong(long value, long compareValue) {
        return compareLongInternal(value, this, compareValue);
    }

    public final boolean compareBigInteger(@Nullable BigInteger value, BigInteger compareValue) {
        Objects.requireNonNull(compareValue);
        return compareBigIntegerInternal(value, this, compareValue);
    }

}
