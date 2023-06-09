package stexfires.util.function;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.HexFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongFunction;
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

    private static final int RADIX_BINARY = 2;
    private static final int RADIX_OCTAL = 8;
    private static final int RADIX_HEX = 16;

    private NumberToStringFunctions() {
    }

    public static final class PrimitiveIntToStringFunctions {

        private PrimitiveIntToStringFunctions() {
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

        public static IntFunction<String> formatted(Locale locale, String format) {
            Objects.requireNonNull(locale);
            Objects.requireNonNull(format);
            return n -> String.format(locale, format, n);
        }

        public static IntFunction<String> numberFormat(NumberFormat numberFormat) {
            Objects.requireNonNull(numberFormat);
            return numberFormat::format;
        }

        public static IntFunction<String> constant(String constant) {
            return n -> constant;
        }

        public static IntFunction<String> supplier(Supplier<String> supplier) {
            Objects.requireNonNull(supplier);
            return n -> supplier.get();
        }

    }

    public static final class PrimitiveLongToStringFunctions {

        private PrimitiveLongToStringFunctions() {
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

        public static LongFunction<String> formatted(Locale locale, String format) {
            Objects.requireNonNull(locale);
            Objects.requireNonNull(format);
            return n -> String.format(locale, format, n);
        }

        public static LongFunction<String> numberFormat(NumberFormat numberFormat) {
            Objects.requireNonNull(numberFormat);
            return numberFormat::format;
        }

        public static LongFunction<String> constant(String constant) {
            return n -> constant;
        }

        public static LongFunction<String> supplier(Supplier<String> supplier) {
            Objects.requireNonNull(supplier);
            return n -> supplier.get();
        }

    }

    public static final class BigIntegerToStringFunctions {

        private BigIntegerToStringFunctions() {
        }

        @SuppressWarnings("ReturnOfNull")
        public static Function<BigInteger, String> binary() {
            return n -> n == null ? null :
                    n.toString(RADIX_BINARY);
        }

        @SuppressWarnings("ReturnOfNull")
        public static Function<BigInteger, String> decimal() {
            return n -> n == null ? null :
                    n.toString();
        }

        @SuppressWarnings("ReturnOfNull")
        public static Function<BigInteger, String> hex() {
            return n -> n == null ? null :
                    n.toString(RADIX_HEX);
        }

        /**
         * @see java.util.HexFormat
         */
        @SuppressWarnings("ReturnOfNull")
        public static Function<BigInteger, String> hexFormat(HexFormat hexFormat) {
            Objects.requireNonNull(hexFormat);
            return n -> n == null ? null :
                    hexFormat.formatHex(n.toByteArray());
        }

        @SuppressWarnings("ReturnOfNull")
        public static Function<BigInteger, String> octal() {
            return n -> n == null ? null :
                    n.toString(RADIX_OCTAL);
        }

        @SuppressWarnings("ReturnOfNull")
        public static Function<BigInteger, String> formatted(Locale locale, String format) {
            Objects.requireNonNull(locale);
            Objects.requireNonNull(format);
            return n -> n == null ? null :
                    String.format(locale, format, n);
        }

        @SuppressWarnings("ReturnOfNull")
        public static Function<BigInteger, String> numberFormat(NumberFormat numberFormat) {
            Objects.requireNonNull(numberFormat);
            return n -> n == null ? null :
                    numberFormat.format(n);
        }

        public static Function<BigInteger, String> constant(String constant) {
            return n -> constant;
        }

        public static Function<BigInteger, String> supplier(Supplier<String> supplier) {
            Objects.requireNonNull(supplier);
            return n -> supplier.get();
        }

    }

}
