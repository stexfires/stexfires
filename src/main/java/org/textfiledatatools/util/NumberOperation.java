package org.textfiledatatools.util;

import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.LongFunction;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum NumberOperation {
    INCREMENT,
    DECREMENT,
    TO_ZERO,
    TO_NULL,
    ABS,
    NEGATE;

    public static IntFunction<Integer> incrementIntFunction() {
        return (int value) -> operateInt(INCREMENT, value);
    }

    public static IntFunction<Integer> decrementIntFunction() {
        return (int value) -> operateInt(DECREMENT, value);
    }

    public static IntFunction<Integer> toZeroIntFunction() {
        return (int value) -> operateInt(TO_ZERO, value);
    }

    public static IntFunction<Integer> toNullIntFunction() {
        return (int value) -> operateInt(TO_NULL, value);
    }

    public static IntFunction<Integer> absIntFunction() {
        return (int value) -> operateInt(ABS, value);
    }

    public static IntFunction<Integer> negateIntFunction() {
        return (int value) -> operateInt(NEGATE, value);
    }

    public static LongFunction<Long> incrementLongFunction() {
        return (long value) -> operateLong(INCREMENT, value);
    }

    public static LongFunction<Long> decrementLongFunction() {
        return (long value) -> operateLong(DECREMENT, value);
    }

    public static LongFunction<Long> toZeroLongFunction() {
        return (long value) -> operateLong(TO_ZERO, value);
    }

    public static LongFunction<Long> toNullLongFunction() {
        return (long value) -> operateLong(TO_NULL, value);
    }

    public static LongFunction<Long> absLongFunction() {
        return (long value) -> operateLong(ABS, value);
    }

    public static LongFunction<Long> negateLongFunction() {
        return (long value) -> operateLong(NEGATE, value);
    }

    public static Integer operateInt(NumberOperation numberOperation, int value) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(numberOperation);
        Integer result;
        switch (numberOperation) {
            case INCREMENT:
                result = Math.incrementExact(value);
                break;
            case DECREMENT:
                result = Math.decrementExact(value);
                break;
            case TO_ZERO:
                result = 0;
                break;
            case TO_NULL:
                result = null;
                break;
            case ABS:
                result = Math.abs(value);
                break;
            case NEGATE:
                result = Math.negateExact(value);
                break;
            default:
                result = value;
        }
        return result;
    }

    public static Long operateLong(NumberOperation numberOperation, long value) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(numberOperation);
        Long result;
        switch (numberOperation) {
            case INCREMENT:
                result = Math.incrementExact(value);
                break;
            case DECREMENT:
                result = Math.decrementExact(value);
                break;
            case TO_ZERO:
                result = 0L;
                break;
            case TO_NULL:
                result = null;
                break;
            case ABS:
                result = Math.abs(value);
                break;
            case NEGATE:
                result = Math.negateExact(value);
                break;
            default:
                result = value;
        }
        return result;
    }

}
