package stexfires.util.function;

import org.jspecify.annotations.Nullable;

import java.math.BigInteger;
import java.util.function.*;

/**
 * @see java.lang.Number
 * @see java.util.function.Function
 * @see java.util.function.IntFunction
 * @see java.util.function.LongFunction
 * @see java.util.function.ToIntFunction
 * @see java.util.function.ToLongFunction
 * @see java.util.function.IntToLongFunction
 * @see java.util.function.LongToIntFunction
 * @since 0.1
 */
public final class NumberToNumberFunctions {

    private NumberToNumberFunctions() {
    }

    public static IntToLongFunction primitiveIntToPrimitiveLong() {
        return n -> n;
    }

    public static IntFunction<Integer> primitiveIntToInteger() {
        return Integer::valueOf;
    }

    public static IntFunction<Long> primitiveIntToLong() {
        return Long::valueOf;
    }

    public static IntFunction<BigInteger> primitiveIntToBigInteger() {
        return BigInteger::valueOf;
    }

    public static LongToIntFunction primitiveLongToPrimitiveInt() {
        return n -> {
            if (n >= Integer.MIN_VALUE && n <= Integer.MAX_VALUE) {
                return (int) n;
            } else {
                throw new ArithmeticException("'long' out of 'int' range");
            }
        };
    }

    public static LongFunction<Integer> primitiveLongToInteger() {
        return n -> {
            if (n >= Integer.MIN_VALUE && n <= Integer.MAX_VALUE) {
                return (Integer) (int) n;
            } else {
                throw new ArithmeticException("'long' out of 'Integer' range");
            }
        };
    }

    public static LongFunction<Long> primitiveLongToLong() {
        return Long::valueOf;
    }

    public static LongFunction<BigInteger> primitiveLongToBigInteger() {
        return BigInteger::valueOf;
    }

    public static ToIntFunction<@Nullable Integer> integerToPrimitiveInt(int nullValue) {
        return n -> n == null ? nullValue : n;
    }

    public static ToLongFunction<@Nullable Integer> integerToPrimitiveLong(long nullValue) {
        return n -> n == null ? nullValue : n.longValue();
    }

    public static Function<@Nullable Integer, @Nullable Long> integerToLong(@Nullable Long nullValue) {
        return n -> n == null ? nullValue : (Long) n.longValue();
    }

    public static Function<@Nullable Integer, @Nullable BigInteger> integerToBigInteger(@Nullable BigInteger nullValue) {
        return n -> n == null ? nullValue : BigInteger.valueOf(n.longValue());
    }

    public static ToIntFunction<@Nullable Long> longToPrimitiveInt(int nullValue) {
        return n -> {
            if (n == null) {
                return nullValue;
            } else if (n >= Integer.MIN_VALUE && n <= Integer.MAX_VALUE) {
                return n.intValue();
            } else {
                throw new ArithmeticException("'Long' out of 'int' range");
            }
        };
    }

    public static ToLongFunction<@Nullable Long> longToPrimitiveLong(long nullValue) {
        return n -> n == null ? nullValue : n;
    }

    public static Function<@Nullable Long, @Nullable Integer> longToInteger(@Nullable Integer nullValue) {
        return n -> {
            if (n == null) {
                return nullValue;
            } else if (n >= Integer.MIN_VALUE && n <= Integer.MAX_VALUE) {
                return (Integer) n.intValue();
            } else {
                throw new ArithmeticException("'Long' out of 'Integer' range");
            }
        };
    }

    public static Function<@Nullable Long, @Nullable BigInteger> longToBigInteger(@Nullable BigInteger nullValue) {
        return n -> n == null ? nullValue : BigInteger.valueOf(n);
    }

    public static ToIntFunction<@Nullable BigInteger> bigIntegerToPrimitiveInt(int nullValue) {
        return n -> n == null ? nullValue : n.intValueExact();
    }

    public static ToLongFunction<@Nullable BigInteger> bigIntegerToPrimitiveLong(long nullValue) {
        return n -> n == null ? nullValue : n.longValueExact();
    }

    public static Function<@Nullable BigInteger, @Nullable Integer> bigIntegerToInteger(@Nullable Integer nullValue) {
        return n -> n == null ? nullValue : (Integer) n.intValueExact();
    }

    public static Function<@Nullable BigInteger, @Nullable Long> bigIntegerToLong(@Nullable Long nullValue) {
        return n -> n == null ? nullValue : (Long) n.longValueExact();
    }

}
