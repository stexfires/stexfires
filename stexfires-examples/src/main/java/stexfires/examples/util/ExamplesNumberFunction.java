package stexfires.examples.util;

import stexfires.util.function.NumberPredicates.BigIntegerPredicates;
import stexfires.util.function.NumberPredicates.PrimitiveIntPredicates;
import stexfires.util.function.NumberPredicates.PrimitiveLongPredicates;
import stexfires.util.function.NumberToNumberFunctions;
import stexfires.util.function.NumberToStringFunctions.BigIntegerToStringFunctions;
import stexfires.util.function.NumberToStringFunctions.PrimitiveIntToStringFunctions;
import stexfires.util.function.NumberToStringFunctions.PrimitiveLongToStringFunctions;
import stexfires.util.function.NumberUnaryOperators.BigIntegerUnaryOperators;
import stexfires.util.function.NumberUnaryOperators.PrimitiveIntUnaryOperators;
import stexfires.util.function.NumberUnaryOperators.PrimitiveLongUnaryOperators;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesNumberFunction {

    private static final int[] PRIMITIVE_INT_VALUES = new int[]{
            Integer.MIN_VALUE,
            Integer.MIN_VALUE + 1,
            Short.MIN_VALUE - 1,
            Short.MIN_VALUE,
            Byte.MIN_VALUE - 1,
            Byte.MIN_VALUE,
            -12,
            -10,
            -4,
            -3,
            -2,
            -1,
            0,
            1,
            2,
            3,
            4,
            10,
            12,
            Byte.MAX_VALUE,
            Byte.MAX_VALUE + 1,
            1_000,
            10_000,
            Short.MAX_VALUE,
            Short.MAX_VALUE + 1,
            65_535,
            65_536,
            100_000,
            1_000_000,
            Integer.MAX_VALUE - 1,
            Integer.MAX_VALUE
    };

    private static final long[] PRIMITIVE_LONG_VALUES = new long[]{
            Long.MIN_VALUE,
            Long.MIN_VALUE + 1L,
            Integer.MIN_VALUE - 1L,
            Integer.MIN_VALUE,
            -12L,
            -10L,
            -4L,
            -3L,
            -2L,
            -1L,
            0L,
            1L,
            2L,
            3L,
            4L,
            10L,
            12L,
            1_000L,
            10_000L,
            100_000L,
            1_000_000L,
            Integer.MAX_VALUE,
            Integer.MAX_VALUE + 1L,
            Long.MAX_VALUE - 1L,
            Long.MAX_VALUE
    };

    @SuppressWarnings("StaticCollection")
    private static final List<BigInteger> BIG_INTEGER_VALUES;

    static {
        List<BigInteger> values = new ArrayList<>();
        values.add(new BigInteger("-9999999999999999999999"));
        values.add(BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.ONE));
        values.add(BigInteger.valueOf(Long.MIN_VALUE));
        values.add(BigInteger.valueOf(Integer.MIN_VALUE).subtract(BigInteger.ONE));
        values.add(BigInteger.valueOf(Integer.MIN_VALUE));
        values.add(BigInteger.valueOf(-12L));
        values.add(BigInteger.valueOf(-10L));
        values.add(BigInteger.valueOf(-4L));
        values.add(BigInteger.valueOf(-3L));
        values.add(BigInteger.valueOf(-2L));
        values.add(BigInteger.valueOf(-1L));
        values.add(BigInteger.valueOf(0L));
        values.add(new BigInteger("-0"));
        values.add(new BigInteger("0"));
        values.add(new BigInteger("+0"));
        values.add(BigInteger.ZERO);
        values.add(BigInteger.valueOf(1L));
        values.add(BigInteger.ONE);
        values.add(BigInteger.valueOf(2L));
        values.add(BigInteger.TWO);
        values.add(BigInteger.valueOf(1L).shiftLeft(1));
        values.add(BigInteger.valueOf(3L));
        values.add(BigInteger.valueOf(4L));
        values.add(BigInteger.valueOf(10L));
        values.add(BigInteger.TEN);
        values.add(BigInteger.valueOf(12L));
        values.add(BigInteger.valueOf(Byte.MAX_VALUE));
        values.add(BigInteger.valueOf(Byte.MAX_VALUE).add(BigInteger.ONE));
        values.add(BigInteger.valueOf(255L));
        values.add(BigInteger.valueOf(256L));
        values.add(BigInteger.valueOf(1_000L));
        values.add(BigInteger.valueOf(10_000L));
        values.add(BigInteger.valueOf(Short.MAX_VALUE));
        values.add(BigInteger.valueOf(Short.MAX_VALUE).add(BigInteger.ONE));
        values.add(BigInteger.valueOf(65535L));
        values.add(BigInteger.valueOf(65536L));
        values.add(BigInteger.valueOf(100_000L));
        values.add(BigInteger.valueOf(1_000_000L));
        values.add(BigInteger.valueOf(Integer.MAX_VALUE));
        values.add(BigInteger.valueOf(Integer.MAX_VALUE).add(BigInteger.ONE));
        values.add(BigInteger.valueOf(Long.MAX_VALUE));
        values.add(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE));
        values.add(new BigInteger("9999999999999999999999"));
        values.add(null);
        BIG_INTEGER_VALUES = Collections.unmodifiableList(values);
    }

    @SuppressWarnings("StaticCollection")
    private static final List<BigInteger> BIG_INTEGER_PARAMETER;

    static {
        List<BigInteger> values = new ArrayList<>();
        values.add(new BigInteger("-9999999999999999999999"));
        values.add(BigInteger.valueOf(-12L));
        values.add(BigInteger.valueOf(-10L));
        values.add(BigInteger.valueOf(-4L));
        values.add(BigInteger.valueOf(-3L));
        values.add(BigInteger.valueOf(-2L));
        values.add(BigInteger.valueOf(-1L));
        values.add(BigInteger.ZERO);
        values.add(BigInteger.ONE);
        values.add(BigInteger.TWO);
        values.add(BigInteger.valueOf(3L));
        values.add(BigInteger.valueOf(4L));
        values.add(BigInteger.TEN);
        values.add(BigInteger.valueOf(12L));
        values.add(new BigInteger("9999999999999999999999"));
        BIG_INTEGER_PARAMETER = Collections.unmodifiableList(values);
    }

    private ExamplesNumberFunction() {
    }

    private static void printMethodInfo(String className, String methodeName, String parameter) {
        StringBuilder b = new StringBuilder();
        b.append("---");
        b.append(className);
        b.append(" ");
        b.append(methodeName);
        if (parameter != null) {
            b.append(" (");
            b.append(parameter);
            b.append(")");
        }
        System.out.println(b);
    }

    private static String createResultString(Boolean result) {
        if (result == null) {
            return " <NULL> ";
        } else if (result) {
            return " TRUE   ";
        } else {
            return " false  ";
        }
    }

    private static void printResult(Boolean result, Number value, String exceptionMessage) {
        System.out.println(createResultString(result) + value + (exceptionMessage != null ? " " + exceptionMessage : ""));
    }

    private static void printResult(Number result, Number value, String exceptionMessage) {
        System.out.println(" " + (result != null ? result.toString() : "<NULL>") + " <-- " + value + (exceptionMessage != null ? " " + exceptionMessage : ""));
    }

    private static void printResult(String result, Number value, String exceptionMessage) {
        System.out.println(" " + (result != null ? result : "<NULL>") + " <-- " + value + (exceptionMessage != null ? " " + exceptionMessage : ""));
    }

    private static void testPrimitiveIntPredicate(String methodName, IntPredicate predicate, String parameter) {
        printMethodInfo("PrimitiveIntPredicates", methodName, parameter);

        for (int value : PRIMITIVE_INT_VALUES) {
            Boolean result = null;
            String exceptionMessage = null;
            try {
                result = predicate.test(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void testPrimitiveLongPredicate(String methodName, LongPredicate predicate, String parameter) {
        printMethodInfo("PrimitiveLongPredicates", methodName, parameter);

        for (long value : PRIMITIVE_LONG_VALUES) {
            Boolean result = null;
            String exceptionMessage = null;
            try {
                result = predicate.test(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void testBigIntegerPredicate(String methodName, Predicate<BigInteger> predicate, String parameter) {
        printMethodInfo("BigIntegerPredicates", methodName, parameter);

        for (BigInteger value : BIG_INTEGER_VALUES) {
            Boolean result = null;
            String exceptionMessage = null;
            try {
                result = predicate.test(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void applyPrimitiveIntUnaryOperator(String methodName, IntUnaryOperator operator, String parameter) {
        printMethodInfo("PrimitiveIntUnaryOperators", methodName, parameter);

        for (int value : PRIMITIVE_INT_VALUES) {
            Number result = null;
            String exceptionMessage = null;
            try {
                result = operator.applyAsInt(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void applyPrimitiveLongUnaryOperator(String methodName, LongUnaryOperator operator, String parameter) {
        printMethodInfo("PrimitiveLongUnaryOperators", methodName, parameter);

        for (long value : PRIMITIVE_LONG_VALUES) {
            Number result = null;
            String exceptionMessage = null;
            try {
                result = operator.applyAsLong(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void applyBigIntegerUnaryOperator(String methodName, UnaryOperator<BigInteger> operator, String parameter) {
        printMethodInfo("BigIntegerUnaryOperators", methodName, parameter);

        for (BigInteger value : BIG_INTEGER_VALUES) {
            Number result = null;
            String exceptionMessage = null;
            try {
                result = operator.apply(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void applyPrimitiveIntToStringFunction(String methodName, IntFunction<String> function, String parameter) {
        printMethodInfo("PrimitiveIntToStringFunctions", methodName, parameter);

        for (int value : PRIMITIVE_INT_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = function.apply(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void applyPrimitiveLongToStringFunction(String methodName, LongFunction<String> function, String parameter) {
        printMethodInfo("PrimitiveLongToStringFunctions", methodName, parameter);

        for (long value : PRIMITIVE_LONG_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = function.apply(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void applyBigIntegerToStringFunction(String methodName, Function<BigInteger, String> function, String parameter) {
        printMethodInfo("BigIntegerToStringFunctions", methodName, parameter);

        for (BigInteger value : BIG_INTEGER_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = function.apply(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void showPrimitiveIntPredicates() {
        System.out.println("-showPrimitiveIntPredicates---");

        testPrimitiveIntPredicate("zero", PrimitiveIntPredicates.zero(), null);

        testPrimitiveIntPredicate("rangeOfByte", PrimitiveIntPredicates.rangeOfByte(), null);
        testPrimitiveIntPredicate("rangeOfShort", PrimitiveIntPredicates.rangeOfShort(), null);
        testPrimitiveIntPredicate("rangeOfCharacter", PrimitiveIntPredicates.rangeOfCharacter(), null);

        for (int parameter : PRIMITIVE_INT_VALUES) {
            testPrimitiveIntPredicate("lessThan", PrimitiveIntPredicates.lessThan(parameter), String.valueOf(parameter));
        }
    }

    private static void showPrimitiveLongPredicates() {
        System.out.println("-showPrimitiveLongPredicates---");

        testPrimitiveLongPredicate("zero", PrimitiveLongPredicates.zero(), null);

        testPrimitiveLongPredicate("rangeOfByte", PrimitiveLongPredicates.rangeOfByte(), null);
        testPrimitiveLongPredicate("rangeOfShort", PrimitiveLongPredicates.rangeOfShort(), null);
        testPrimitiveLongPredicate("rangeOfCharacter", PrimitiveLongPredicates.rangeOfCharacter(), null);
        testPrimitiveLongPredicate("rangeOfInteger", PrimitiveLongPredicates.rangeOfInteger(), null);

        for (long parameter : PRIMITIVE_LONG_VALUES) {
            testPrimitiveLongPredicate("lessThan", PrimitiveLongPredicates.lessThan(parameter), String.valueOf(parameter));
        }
    }

    private static void showBigIntegerPredicates() {
        System.out.println("-showBigIntegerPredicates---");

        testBigIntegerPredicate("zero", BigIntegerPredicates.zero(), null);

        testBigIntegerPredicate("rangeOfByte", BigIntegerPredicates.rangeOfByte(), null);
        testBigIntegerPredicate("rangeOfShort", BigIntegerPredicates.rangeOfShort(), null);
        testBigIntegerPredicate("rangeOfCharacter", BigIntegerPredicates.rangeOfCharacter(), null);
        testBigIntegerPredicate("rangeOfInteger", BigIntegerPredicates.rangeOfInteger(), null);
        testBigIntegerPredicate("rangeOfLong", BigIntegerPredicates.rangeOfLong(), null);

        for (BigInteger parameter : BIG_INTEGER_PARAMETER) {
            testBigIntegerPredicate("lessThan", BigIntegerPredicates.lessThan(parameter), String.valueOf(parameter));
        }
    }

    private static void showPrimitiveIntUnaryOperators() {
        System.out.println("-showPrimitiveIntUnaryOperators---");

        applyPrimitiveIntUnaryOperator("toZero", PrimitiveIntUnaryOperators.toZero(), null);

        applyPrimitiveIntUnaryOperator("digitSum", PrimitiveIntUnaryOperators.digitSum(), null);

        for (int parameter : PRIMITIVE_INT_VALUES) {
            applyPrimitiveIntUnaryOperator("addExact", PrimitiveIntUnaryOperators.addExact(parameter), String.valueOf(parameter));
        }
    }

    private static void showPrimitiveLongUnaryOperators() {
        System.out.println("-showPrimitiveLongUnaryOperators---");

        applyPrimitiveLongUnaryOperator("toZero", PrimitiveLongUnaryOperators.toZero(), null);

        applyPrimitiveLongUnaryOperator("digitSum", PrimitiveLongUnaryOperators.digitSum(), null);

        for (long parameter : PRIMITIVE_LONG_VALUES) {
            applyPrimitiveLongUnaryOperator("addExact", PrimitiveLongUnaryOperators.addExact(parameter), String.valueOf(parameter));
        }
    }

    private static void showBigIntegerUnaryOperators() {
        System.out.println("-showBigIntegerUnaryOperators---");

        applyBigIntegerUnaryOperator("toZero", BigIntegerUnaryOperators.toZero(), null);

        applyBigIntegerUnaryOperator("digitSum", BigIntegerUnaryOperators.digitSum(), null);

        for (BigInteger parameter : BIG_INTEGER_PARAMETER) {
            applyBigIntegerUnaryOperator("add", BigIntegerUnaryOperators.add(parameter), String.valueOf(parameter));
        }
    }

    private static void showPrimitiveIntToStringFunctions() {
        System.out.println("-showPrimitiveIntToStringFunctions---");

        applyPrimitiveIntToStringFunction("binary", PrimitiveIntToStringFunctions.binary(), null);
        applyPrimitiveIntToStringFunction("decimal", PrimitiveIntToStringFunctions.decimal(), null);
        applyPrimitiveIntToStringFunction("hex", PrimitiveIntToStringFunctions.hex(), null);
        applyPrimitiveIntToStringFunction("hexFormat", PrimitiveIntToStringFunctions.hexFormat(HexFormat.ofDelimiter(" ").withPrefix("#").withUpperCase()), "space delimiter, prefix #, uppercase");
        applyPrimitiveIntToStringFunction("octal", PrimitiveIntToStringFunctions.octal(), null);

        applyPrimitiveIntToStringFunction("formatted", PrimitiveIntToStringFunctions.formatted(Locale.GERMANY, "%05d"), "%05d");
        applyPrimitiveIntToStringFunction("numberFormat", PrimitiveIntToStringFunctions.numberFormat(NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT)), "Compact US Short");
        applyPrimitiveIntToStringFunction("numberFormat", PrimitiveIntToStringFunctions.numberFormat(NumberFormat.getIntegerInstance(Locale.GERMANY)), "Integer GERMANY");
        applyPrimitiveIntToStringFunction("constant", PrimitiveIntToStringFunctions.constant("***"), "***");
        applyPrimitiveIntToStringFunction("supplier", PrimitiveIntToStringFunctions.supplier(() -> "#"), "-> #");
    }

    private static void showPrimitiveLongToStringFunctions() {
        System.out.println("-showPrimitiveLongToStringFunctions---");

        applyPrimitiveLongToStringFunction("binary", PrimitiveLongToStringFunctions.binary(), null);
        applyPrimitiveLongToStringFunction("decimal", PrimitiveLongToStringFunctions.decimal(), null);
        applyPrimitiveLongToStringFunction("hex", PrimitiveLongToStringFunctions.hex(), null);
        applyPrimitiveLongToStringFunction("hexFormat", PrimitiveLongToStringFunctions.hexFormat(HexFormat.ofDelimiter(" ").withPrefix("#").withUpperCase()), "space delimiter, prefix #, uppercase");
        applyPrimitiveLongToStringFunction("octal", PrimitiveLongToStringFunctions.octal(), null);

        applyPrimitiveLongToStringFunction("formatted", PrimitiveLongToStringFunctions.formatted(Locale.GERMANY, "%05d"), "%05d");
        applyPrimitiveLongToStringFunction("numberFormat", PrimitiveLongToStringFunctions.numberFormat(NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT)), "Compact US Short");
        applyPrimitiveLongToStringFunction("numberFormat", PrimitiveLongToStringFunctions.numberFormat(NumberFormat.getIntegerInstance(Locale.GERMANY)), "Integer GERMANY");
        applyPrimitiveLongToStringFunction("constant", PrimitiveLongToStringFunctions.constant("***"), "***");
        applyPrimitiveLongToStringFunction("supplier", PrimitiveLongToStringFunctions.supplier(() -> "#"), "-> #");
    }

    private static void showBigIntegerToStringFunctions() {
        System.out.println("-showBigIntegerToStringFunctions---");

        applyBigIntegerToStringFunction("binary", BigIntegerToStringFunctions.binary(), null);
        applyBigIntegerToStringFunction("decimal", BigIntegerToStringFunctions.decimal(), null);
        applyBigIntegerToStringFunction("hex", BigIntegerToStringFunctions.hex(), null);
        applyBigIntegerToStringFunction("hexFormat", BigIntegerToStringFunctions.hexFormat(HexFormat.ofDelimiter(" ").withPrefix("#").withUpperCase()), "space delimiter, prefix #, uppercase");
        applyBigIntegerToStringFunction("octal", BigIntegerToStringFunctions.octal(), null);

        applyBigIntegerToStringFunction("formatted", BigIntegerToStringFunctions.formatted(Locale.GERMANY, "%05d"), "%05d");
        applyBigIntegerToStringFunction("numberFormat", BigIntegerToStringFunctions.numberFormat(NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT)), "Compact US Short");
        applyBigIntegerToStringFunction("numberFormat", BigIntegerToStringFunctions.numberFormat(NumberFormat.getIntegerInstance(Locale.GERMANY)), "Integer GERMANY");
        applyBigIntegerToStringFunction("constant", BigIntegerToStringFunctions.constant("***"), "***");
        applyBigIntegerToStringFunction("supplier", BigIntegerToStringFunctions.supplier(() -> "#"), "-> #");
    }

    private static void showNumberToNumberFunctions() {
        System.out.println("-showNumberToNumberFunctions---");

        // Primitive int
        System.out.println("---primitiveIntToInteger");
        for (int value : PRIMITIVE_INT_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.primitiveIntToInteger().apply(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---primitiveIntToPrimitiveLong");
        for (int value : PRIMITIVE_INT_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.primitiveIntToPrimitiveLong().applyAsLong(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---primitiveIntToLong");
        for (int value : PRIMITIVE_INT_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.primitiveIntToLong().apply(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---primitiveIntToBigInteger");
        for (int value : PRIMITIVE_INT_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.primitiveIntToBigInteger().apply(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }

        // Integer
        System.out.println("---integerToPrimitiveInt");
        for (Integer value : PRIMITIVE_INT_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.integerToPrimitiveInt(0).applyAsInt(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---integerToPrimitiveLong");
        for (Integer value : PRIMITIVE_INT_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.integerToPrimitiveLong(0L).applyAsLong(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---integerToLong");
        for (Integer value : PRIMITIVE_INT_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.integerToLong(null).apply(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---integerToBigInteger");
        for (Integer value : PRIMITIVE_INT_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.integerToBigInteger(null).apply(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }

        // Primitive long
        System.out.println("---primitiveLongToPrimitiveInt");
        for (long value : PRIMITIVE_LONG_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.primitiveLongToPrimitiveInt().applyAsInt(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---primitiveLongToInteger");
        for (long value : PRIMITIVE_LONG_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.primitiveLongToInteger().apply(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---primitiveLongToLong");
        for (long value : PRIMITIVE_LONG_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.primitiveLongToLong().apply(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---primitiveLongToBigInteger");
        for (long value : PRIMITIVE_LONG_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.primitiveLongToBigInteger().apply(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }

        // Long
        System.out.println("---longToPrimitiveInt");
        for (Long value : PRIMITIVE_LONG_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.longToPrimitiveInt(0).applyAsInt(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---longToInteger");
        for (Long value : PRIMITIVE_LONG_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.longToInteger(null).apply(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---longToPrimitiveLong");
        for (Long value : PRIMITIVE_LONG_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.longToPrimitiveLong(0L).applyAsLong(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---longToBigInteger");
        for (Long value : PRIMITIVE_LONG_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.longToBigInteger(null).apply(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }

        // BigInteger
        System.out.println("---bigIntegerToPrimitiveInt");
        for (BigInteger value : BIG_INTEGER_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.bigIntegerToPrimitiveInt(0).applyAsInt(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---bigIntegerToPrimitiveLong");
        for (BigInteger value : BIG_INTEGER_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.bigIntegerToPrimitiveLong(0).applyAsLong(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---bigIntegerToInteger");
        for (BigInteger value : BIG_INTEGER_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.bigIntegerToInteger(null).apply(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
        System.out.println("---bigIntegerToLong");
        for (BigInteger value : BIG_INTEGER_VALUES) {
            String result = null;
            String exceptionMessage = null;
            try {
                result = String.valueOf(NumberToNumberFunctions.bigIntegerToLong(null).apply(value));
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }

    }

    public static void main(String... args) {
        // Predicates
        showPrimitiveIntPredicates();
        showPrimitiveLongPredicates();
        showBigIntegerPredicates();
        // UnaryOperators
        showPrimitiveIntUnaryOperators();
        showPrimitiveLongUnaryOperators();
        showBigIntegerUnaryOperators();
        // ToStringFunctions
        showPrimitiveIntToStringFunctions();
        showPrimitiveLongToStringFunctions();
        showBigIntegerToStringFunctions();
        // ToNumberFunctions
        showNumberToNumberFunctions();
    }

}
