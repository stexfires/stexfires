package stexfires.util;

import java.math.BigInteger;
import java.util.Objects;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

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
        return value -> operateInt(numberUnaryOperatorType, value);
    }

    public static LongUnaryOperator longUnaryOperator(NumberUnaryOperatorType numberUnaryOperatorType) {
        Objects.requireNonNull(numberUnaryOperatorType);
        return value -> operateLong(numberUnaryOperatorType, value);
    }

    public static UnaryOperator<BigInteger> bigIntegerUnaryOperator(NumberUnaryOperatorType numberUnaryOperatorType) {
        Objects.requireNonNull(numberUnaryOperatorType);
        return value -> operateBigInteger(numberUnaryOperatorType, value);
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

    private static BigInteger operateBigInteger(NumberUnaryOperatorType numberUnaryOperatorType, BigInteger value) {
        Objects.requireNonNull(numberUnaryOperatorType);
        BigInteger result = null;
        switch (numberUnaryOperatorType) {
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
            default:
                result = value;
        }
        return result;
    }

    public IntUnaryOperator intUnaryOperator() {
        return value -> operateInt(this, value);
    }

    public LongUnaryOperator longUnaryOperator() {
        return value -> operateLong(this, value);
    }

    public UnaryOperator<BigInteger> bigIntegerUnaryOperator() {
        return value -> operateBigInteger(this, value);
    }

    public int operate(int value) {
        return operateInt(this, value);
    }

    public long operate(long value) {
        return operateLong(this, value);
    }

    public BigInteger operate(BigInteger value) {
        return operateBigInteger(this, value);
    }

}
