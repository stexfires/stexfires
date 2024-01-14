package stexfires.util.function;

import org.jspecify.annotations.Nullable;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.HexFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @see java.lang.Number
 * @see java.lang.String
 * @see java.util.Formatter
 * @see java.util.function.Function
 * @see java.util.function.IntFunction
 * @see java.util.function.LongFunction
 * @since 0.1
 */
public final class NumberToStringFunctions {

    public static final int RADIX_BINARY = 2;
    public static final int RADIX_OCTAL = 8;
    public static final int RADIX_HEX = 16;

    private NumberToStringFunctions() {
    }

    public static <T extends Number> Function<@Nullable T, String> constant(String constant) {
        return n -> constant;
    }

    public static <T extends Number> Function<@Nullable T, String> supplier(Supplier<String> supplier) {
        Objects.requireNonNull(supplier);
        return n -> supplier.get();
    }

    public static <T extends Number> Function<@Nullable T, String> nullHandler(String nullValue,
                                                                               Function<T, String> nonNullFunction) {
        Objects.requireNonNull(nullValue);
        Objects.requireNonNull(nonNullFunction);
        return n -> (n == null) ? nullValue : nonNullFunction.apply(n);
    }

    public static <T extends Number> Function<T, String> conditional(Predicate<T> condition,
                                                                     Function<T, String> trueFunction,
                                                                     Function<T, String> falseFunction) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueFunction);
        Objects.requireNonNull(falseFunction);
        return n -> condition.test(n) ? trueFunction.apply(n) : falseFunction.apply(n);
    }

    public static <T extends Number> Function<T, String> formatted(Locale locale, String format) {
        Objects.requireNonNull(locale);
        Objects.requireNonNull(format);
        return n -> String.format(locale, format, n);
    }

    public static <T extends Number> Function<T, String> numberFormat(NumberFormat numberFormat) {
        Objects.requireNonNull(numberFormat);
        return numberFormat::format;
    }

    public static final class PrimitiveIntToStringFunctions {

        private PrimitiveIntToStringFunctions() {
        }

        public static IntFunction<String> constant(String constant) {
            return n -> constant;
        }

        public static IntFunction<String> supplier(Supplier<String> supplier) {
            Objects.requireNonNull(supplier);
            return n -> supplier.get();
        }

        public static Function<@Nullable Integer, String> nullHandler(String nullValue,
                                                                      IntFunction<String> nonNullFunction) {
            Objects.requireNonNull(nullValue);
            Objects.requireNonNull(nonNullFunction);
            return n -> (n == null) ? nullValue : nonNullFunction.apply(n);
        }

        public static IntFunction<String> conditional(IntPredicate condition,
                                                      IntFunction<String> trueFunction,
                                                      IntFunction<String> falseFunction) {
            Objects.requireNonNull(condition);
            Objects.requireNonNull(trueFunction);
            Objects.requireNonNull(falseFunction);
            return n -> condition.test(n) ? trueFunction.apply(n) : falseFunction.apply(n);
        }

        public static IntFunction<String> formatted(Locale locale, String format) {
            Objects.requireNonNull(locale);
            Objects.requireNonNull(format);
            return n -> String.format(locale, format, n);
        }

        public static IntFunction<String> numberFormat(NumberFormat numberFormat) {
            Objects.requireNonNull(numberFormat);
            return numberFormat::format;
        }

        public static IntFunction<String> binary() {
            return Integer::toBinaryString;
        }

        public static IntFunction<String> decimal() {
            return Integer::toString;
        }

        public static IntFunction<String> hex() {
            return Integer::toHexString;
        }

        /**
         * @see java.util.HexFormat
         */
        public static IntFunction<String> hexFormat(HexFormat hexFormat) {
            Objects.requireNonNull(hexFormat);
            return n -> hexFormat.formatHex(BigInteger.valueOf(n).toByteArray());
        }

        public static IntFunction<String> octal() {
            return Integer::toOctalString;
        }

    }

    public static final class PrimitiveLongToStringFunctions {

        private PrimitiveLongToStringFunctions() {
        }

        public static LongFunction<String> constant(String constant) {
            return n -> constant;
        }

        public static LongFunction<String> supplier(Supplier<String> supplier) {
            Objects.requireNonNull(supplier);
            return n -> supplier.get();
        }

        public static Function<@Nullable Long, String> nullHandler(String nullValue,
                                                                   LongFunction<String> nonNullFunction) {
            Objects.requireNonNull(nullValue);
            Objects.requireNonNull(nonNullFunction);
            return n -> (n == null) ? nullValue : nonNullFunction.apply(n);
        }

        public static LongFunction<String> conditional(LongPredicate condition,
                                                       LongFunction<String> trueFunction,
                                                       LongFunction<String> falseFunction) {
            Objects.requireNonNull(condition);
            Objects.requireNonNull(trueFunction);
            Objects.requireNonNull(falseFunction);
            return n -> condition.test(n) ? trueFunction.apply(n) : falseFunction.apply(n);
        }

        public static LongFunction<String> formatted(Locale locale, String format) {
            Objects.requireNonNull(locale);
            Objects.requireNonNull(format);
            return n -> String.format(locale, format, n);
        }

        public static LongFunction<String> numberFormat(NumberFormat numberFormat) {
            Objects.requireNonNull(numberFormat);
            return numberFormat::format;
        }

        public static LongFunction<String> binary() {
            return Long::toBinaryString;
        }

        public static LongFunction<String> decimal() {
            return Long::toString;
        }

        public static LongFunction<String> hex() {
            return Long::toHexString;
        }

        /**
         * @see java.util.HexFormat
         */
        public static LongFunction<String> hexFormat(HexFormat hexFormat) {
            Objects.requireNonNull(hexFormat);
            return n -> hexFormat.formatHex(BigInteger.valueOf(n).toByteArray());
        }

        public static LongFunction<String> octal() {
            return Long::toOctalString;
        }

    }

    public static final class BigIntegerToStringFunctions {

        private BigIntegerToStringFunctions() {
        }

        public static Function<BigInteger, String> binary() {
            return n -> n.toString(RADIX_BINARY);
        }

        public static Function<BigInteger, String> decimal() {
            return BigInteger::toString;
        }

        public static Function<BigInteger, String> hex() {
            return n -> n.toString(RADIX_HEX);
        }

        /**
         * @see java.util.HexFormat
         */
        public static Function<BigInteger, String> hexFormat(HexFormat hexFormat) {
            Objects.requireNonNull(hexFormat);
            return n -> hexFormat.formatHex(n.toByteArray());
        }

        public static Function<BigInteger, String> octal() {
            return n -> n.toString(RADIX_OCTAL);
        }

    }

}
