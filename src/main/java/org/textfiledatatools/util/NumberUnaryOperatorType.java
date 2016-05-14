package org.textfiledatatools.util;

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
