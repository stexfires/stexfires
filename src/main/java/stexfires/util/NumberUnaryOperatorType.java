package stexfires.util;

import java.util.Objects;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum NumberUnaryOperatorType {
    INCREMENT,
    DECREMENT,
    TO_ZERO,
    ABS,
    NEGATE;

    public static IntUnaryOperator addExact(int secondValue) {
        return value -> Math.addExact(value, secondValue);
    }

    public static IntUnaryOperator subtractExact(int secondValue) {
        return value -> Math.subtractExact(value, secondValue);
    }

    public static IntUnaryOperator multiplyExact(int secondValue) {
        return value -> Math.multiplyExact(value, secondValue);
    }

    public static IntUnaryOperator min(int secondValue) {
        return value -> Math.min(value, secondValue);
    }

    public static IntUnaryOperator max(int secondValue) {
        return value -> Math.max(value, secondValue);
    }

    public static IntUnaryOperator floorDiv(int secondValue) {
        return value -> Math.floorDiv(value, secondValue);
    }

    public static IntUnaryOperator floorMod(int secondValue) {
        return value -> Math.floorMod(value, secondValue);
    }

    public static LongUnaryOperator addExact(long secondValue) {
        return value -> Math.addExact(value, secondValue);
    }

    public static LongUnaryOperator subtractExact(long secondValue) {
        return value -> Math.subtractExact(value, secondValue);
    }

    public static LongUnaryOperator multiplyExact(long secondValue) {
        return value -> Math.multiplyExact(value, secondValue);
    }

    public static LongUnaryOperator min(long secondValue) {
        return value -> Math.min(value, secondValue);
    }

    public static LongUnaryOperator max(long secondValue) {
        return value -> Math.max(value, secondValue);
    }

    public static LongUnaryOperator floorDiv(long secondValue) {
        return value -> Math.floorDiv(value, secondValue);
    }

    public static LongUnaryOperator floorMod(long secondValue) {
        return value -> Math.floorMod(value, secondValue);
    }

    public static IntUnaryOperator intUnaryOperator(NumberUnaryOperatorType numberUnaryOperatorType) {
        Objects.requireNonNull(numberUnaryOperatorType);
        return value -> operateInt(numberUnaryOperatorType, value);
    }

    public static LongUnaryOperator longUnaryOperator(NumberUnaryOperatorType numberUnaryOperatorType) {
        Objects.requireNonNull(numberUnaryOperatorType);
        return value -> operateLong(numberUnaryOperatorType, value);
    }

    private static int operateInt(NumberUnaryOperatorType numberUnaryOperatorType, int value) {
        Objects.requireNonNull(numberUnaryOperatorType);
        switch (numberUnaryOperatorType) {
            case INCREMENT:
                return Math.incrementExact(value);
            case DECREMENT:
                return Math.decrementExact(value);
            case TO_ZERO:
                return 0;
            case ABS:
                return Math.abs(value);
            case NEGATE:
                return Math.negateExact(value);
            default:
                return value;
        }
    }

    private static long operateLong(NumberUnaryOperatorType numberUnaryOperatorType, long value) {
        Objects.requireNonNull(numberUnaryOperatorType);
        switch (numberUnaryOperatorType) {
            case INCREMENT:
                return Math.incrementExact(value);
            case DECREMENT:
                return Math.decrementExact(value);
            case TO_ZERO:
                return 0L;
            case ABS:
                return Math.abs(value);
            case NEGATE:
                return Math.negateExact(value);
            default:
                return value;
        }
    }

    public IntUnaryOperator intUnaryOperator() {
        return value -> operateInt(this, value);
    }

    public LongUnaryOperator longUnaryOperator() {
        return value -> operateLong(this, value);
    }

    public int operate(int value) {
        return operateInt(this, value);
    }

    public long operate(long value) {
        return operateLong(this, value);
    }

}
