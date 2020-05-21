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
        return value -> checkIntInternal(value, numberCheckType);
    }

    public static LongPredicate longPredicate(NumberCheckType numberCheckType) {
        Objects.requireNonNull(numberCheckType);
        return value -> checkLongInternal(value, numberCheckType);
    }

    public static Predicate<BigInteger> bigIntegerPredicate(NumberCheckType numberCheckType) {
        Objects.requireNonNull(numberCheckType);
        return value -> checkBigIntegerInternal(value, numberCheckType);
    }

    private static boolean checkIntInternal(int value, NumberCheckType numberCheckType) {
        Objects.requireNonNull(numberCheckType);
        return switch (numberCheckType) {
            case NEGATIVE -> value < 0;
            case POSITIVE -> value > 0;
            case ZERO -> value == 0;
            case NOT_ZERO -> value != 0;
            case ODD -> (value & 1) == 1;
            case EVEN -> (value & 1) == 0;
            case MAX_VALUE -> value == Integer.MAX_VALUE;
            case MIN_VALUE -> value == Integer.MIN_VALUE;
        };
    }

    private static boolean checkLongInternal(long value, NumberCheckType numberCheckType) {
        Objects.requireNonNull(numberCheckType);
        return switch (numberCheckType) {
            case NEGATIVE -> value < 0L;
            case POSITIVE -> value > 0L;
            case ZERO -> value == 0L;
            case NOT_ZERO -> value != 0L;
            case ODD -> (value & 1L) == 1L;
            case EVEN -> (value & 1L) == 0L;
            case MAX_VALUE -> value == Long.MAX_VALUE;
            case MIN_VALUE -> value == Long.MIN_VALUE;
        };
    }

    private static boolean checkBigIntegerInternal(@Nullable BigInteger value, NumberCheckType numberCheckType) {
        Objects.requireNonNull(numberCheckType);
        if (value != null) {
            return switch (numberCheckType) {
                case NEGATIVE -> value.signum() == -1;
                case POSITIVE -> value.signum() == 1;
                case ZERO -> value.signum() == 0;
                case NOT_ZERO -> value.signum() != 0;
                case ODD -> value.and(BigInteger.ONE).compareTo(BigInteger.ONE) == 0;
                case EVEN -> value.and(BigInteger.ONE).compareTo(BigInteger.ZERO) == 0;
                case MAX_VALUE, MIN_VALUE -> false;
            };
        }
        return false;
    }

    public final IntPredicate intPredicate() {
        return value -> checkIntInternal(value, this);
    }

    public final LongPredicate longPredicate() {
        return value -> checkLongInternal(value, this);
    }

    public final Predicate<BigInteger> bigIntegerPredicate() {
        return value -> checkBigIntegerInternal(value, this);
    }

    public final boolean checkInt(int value) {
        return checkIntInternal(value, this);
    }

    public final boolean checkLong(long value) {
        return checkLongInternal(value, this);
    }

    public final boolean checkBigInteger(@Nullable BigInteger value) {
        return checkBigIntegerInternal(value, this);
    }

}
