package stexfires.util;

import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.Objects;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("OverloadedMethodsWithSameNumberOfParameters")
public enum NumberUnaryOperatorType {

    IDENTITY,
    INCREMENT,
    DECREMENT,
    TO_ZERO,
    ABS,
    NEGATE,
    SQUARE;

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

    public static UnaryOperator<BigInteger> add(BigInteger secondValue) {
        Objects.requireNonNull(secondValue);
        return value -> value.add(secondValue);
    }

    public static UnaryOperator<BigInteger> subtract(BigInteger secondValue) {
        Objects.requireNonNull(secondValue);
        return value -> value.subtract(secondValue);
    }

    public static UnaryOperator<BigInteger> multiply(BigInteger secondValue) {
        Objects.requireNonNull(secondValue);
        return value -> value.multiply(secondValue);
    }

    public static UnaryOperator<BigInteger> min(BigInteger secondValue) {
        Objects.requireNonNull(secondValue);
        return value -> value.min(secondValue);
    }

    public static UnaryOperator<BigInteger> max(BigInteger secondValue) {
        Objects.requireNonNull(secondValue);
        return value -> value.max(secondValue);
    }

    public static UnaryOperator<BigInteger> divide(BigInteger secondValue) {
        Objects.requireNonNull(secondValue);
        return value -> value.divide(secondValue);
    }

    public static UnaryOperator<BigInteger> mod(BigInteger secondValue) {
        Objects.requireNonNull(secondValue);
        return value -> value.mod(secondValue);
    }

    public static UnaryOperator<BigInteger> modInverse(BigInteger secondValue) {
        Objects.requireNonNull(secondValue);
        return value -> value.modInverse(secondValue);
    }

    public static UnaryOperator<BigInteger> remainder(BigInteger secondValue) {
        Objects.requireNonNull(secondValue);
        return value -> value.remainder(secondValue);
    }

    public static UnaryOperator<BigInteger> gcd(BigInteger secondValue) {
        Objects.requireNonNull(secondValue);
        return value -> value.gcd(secondValue);
    }

    public static IntUnaryOperator intUnaryOperator(NumberUnaryOperatorType numberUnaryOperatorType) {
        Objects.requireNonNull(numberUnaryOperatorType);
        return value -> operateIntInternal(numberUnaryOperatorType, value);
    }

    public static LongUnaryOperator longUnaryOperator(NumberUnaryOperatorType numberUnaryOperatorType) {
        Objects.requireNonNull(numberUnaryOperatorType);
        return value -> operateLongInternal(numberUnaryOperatorType, value);
    }

    public static UnaryOperator<BigInteger> bigIntegerUnaryOperator(NumberUnaryOperatorType numberUnaryOperatorType) {
        Objects.requireNonNull(numberUnaryOperatorType);
        return value -> operateBigIntegerInternal(numberUnaryOperatorType, value);
    }

    private static int operateIntInternal(NumberUnaryOperatorType numberUnaryOperatorType,
                                          int value) {
        Objects.requireNonNull(numberUnaryOperatorType);
        switch (numberUnaryOperatorType) {
            case IDENTITY:
                return value;
            case INCREMENT:
                return Math.incrementExact(value);
            case DECREMENT:
                return Math.decrementExact(value);
            case TO_ZERO:
                return 0;
            case ABS:
                if (value == Integer.MIN_VALUE) {
                    throw new ArithmeticException("integer overflow");
                }
                return Math.abs(value);
            case NEGATE:
                return Math.negateExact(value);
            case SQUARE:
                return Math.multiplyExact(value, value);
        }
        return value;
    }

    private static long operateLongInternal(NumberUnaryOperatorType numberUnaryOperatorType,
                                            long value) {
        Objects.requireNonNull(numberUnaryOperatorType);
        switch (numberUnaryOperatorType) {
            case IDENTITY:
                return value;
            case INCREMENT:
                return Math.incrementExact(value);
            case DECREMENT:
                return Math.decrementExact(value);
            case TO_ZERO:
                return 0L;
            case ABS:
                if (value == Long.MIN_VALUE) {
                    throw new ArithmeticException("long overflow");
                }
                return Math.abs(value);
            case NEGATE:
                return Math.negateExact(value);
            case SQUARE:
                return Math.multiplyExact(value, value);
        }
        return value;
    }

    private static @Nullable BigInteger operateBigIntegerInternal(NumberUnaryOperatorType numberUnaryOperatorType,
                                                                  @Nullable BigInteger value) {
        Objects.requireNonNull(numberUnaryOperatorType);
        BigInteger result = null;
        switch (numberUnaryOperatorType) {
            case IDENTITY:
                result = value;
                break;
            case INCREMENT:
                if (value != null) {
                    result = value.add(BigInteger.ONE);
                }
                break;
            case DECREMENT:
                if (value != null) {
                    result = value.subtract(BigInteger.ONE);
                }
                break;
            case TO_ZERO:
                result = BigInteger.ZERO;
                break;
            case ABS:
                if (value != null) {
                    result = value.abs();
                }
                break;
            case NEGATE:
                if (value != null) {
                    result = value.negate();
                }
                break;
            case SQUARE:
                if (value != null) {
                    result = value.multiply(value);
                }
                break;
        }
        return result;
    }

    public final IntUnaryOperator intUnaryOperator() {
        return value -> operateIntInternal(this, value);
    }

    public final LongUnaryOperator longUnaryOperator() {
        return value -> operateLongInternal(this, value);
    }

    public final UnaryOperator<BigInteger> bigIntegerUnaryOperator() {
        return value -> operateBigIntegerInternal(this, value);
    }

    public final int operateInt(int value) {
        return operateIntInternal(this, value);
    }

    public final long operateLong(long value) {
        return operateLongInternal(this, value);
    }

    public final @Nullable BigInteger operateBigInteger(@Nullable BigInteger value) {
        return operateBigIntegerInternal(this, value);
    }

}
