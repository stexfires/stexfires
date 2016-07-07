package stexfires.util;

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
        return value -> compareInt(value, numberComparisonType, compareValue);
    }

    public static LongPredicate longPredicate(NumberComparisonType numberComparisonType, long compareValue) {
        Objects.requireNonNull(numberComparisonType);
        return value -> compareLong(value, numberComparisonType, compareValue);
    }

    public static Predicate<BigInteger> bigIntegerPredicate(NumberComparisonType numberComparisonType, BigInteger compareValue) {
        Objects.requireNonNull(numberComparisonType);
        Objects.requireNonNull(compareValue);
        return value -> compareBigInteger(value, numberComparisonType, compareValue);
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
            case MULTIPLE_OF:
                return value % compareValue == 0;
            case SAME_SIGN:
                return (value > 0 && compareValue > 0) || (value == 0 && compareValue == 0) || (value < 0 && compareValue < 0);
            case SAME_ABSOLUTE_VALUE:
                return Math.abs(value) == Math.abs(compareValue);
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
            case MULTIPLE_OF:
                return value % compareValue == 0;
            case SAME_SIGN:
                return (value > 0 && compareValue > 0) || (value == 0 && compareValue == 0) || (value < 0 && compareValue < 0);
            case SAME_ABSOLUTE_VALUE:
                return Math.abs(value) == Math.abs(compareValue);
            default:
                return false;
        }
    }

    private static boolean compareBigInteger(BigInteger value, NumberComparisonType numberComparisonType, BigInteger compareValue) {
        Objects.requireNonNull(numberComparisonType);
        Objects.requireNonNull(compareValue);
        if (value != null) {
            switch (numberComparisonType) {
                case EQUAL_TO:
                    return value.equals(compareValue);
                case NOT_EQUAL_TO:
                    return !value.equals(compareValue);
                case LESS_THAN:
                    return value.compareTo(compareValue) == -1;
                case LESS_THAN_OR_EQUAL_TO:
                    return value.compareTo(compareValue) <= 0;
                case GREATER_THAN_OR_EQUAL_TO:
                    return value.compareTo(compareValue) >= 0;
                case GREATER_THAN:
                    return value.compareTo(compareValue) == 1;
                case MULTIPLE_OF:
                    return value.mod(compareValue).equals(BigInteger.ZERO);
                case SAME_SIGN:
                    return value.signum() == compareValue.signum();
                case SAME_ABSOLUTE_VALUE:
                    return value.abs().equals(compareValue.abs());
                default:
                    return false;
            }
        } else {
            if (numberComparisonType == NOT_EQUAL_TO) {
                return true;
            }
            return false;
        }
    }

    public IntPredicate intPredicate(int compareValue) {
        return value -> compareInt(value, this, compareValue);
    }

    public LongPredicate longPredicate(long compareValue) {
        return value -> compareLong(value, this, compareValue);
    }

    public Predicate<BigInteger> bigIntegerPredicate(BigInteger compareValue) {
        Objects.requireNonNull(compareValue);
        return value -> compareBigInteger(value, this, compareValue);
    }

    public boolean compare(int value, int compareValue) {
        return compareInt(value, this, compareValue);
    }

    public boolean compare(long value, long compareValue) {
        return compareLong(value, this, compareValue);
    }

    public boolean compare(BigInteger value, BigInteger compareValue) {
        Objects.requireNonNull(compareValue);
        return compareBigInteger(value, this, compareValue);
    }

}