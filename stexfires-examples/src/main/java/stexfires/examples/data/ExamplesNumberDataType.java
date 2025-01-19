package stexfires.examples.data;

import org.jspecify.annotations.Nullable;
import stexfires.data.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr", "SpellCheckingInspection"})
public final class ExamplesNumberDataType {

    private ExamplesNumberDataType() {
    }

    private static <T extends @Nullable Number> void testFormat(@Nullable T source, DataTypeFormatter<T> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static <T extends @Nullable Number> void testParse(@Nullable String source, DataTypeParser<T> parser, NumberFormat formatter) {
        try {
            T parseResult = parser.parse(source);
            String formattedResult = null;
            if (parseResult != null) {
                try {
                    formattedResult = "\"" + formatter.format(parseResult) + "\" (" + parseResult.getClass().getSimpleName() + ")";
                } catch (IllegalArgumentException e) {
                    formattedResult = e.getMessage();
                }
            }
            System.out.println("Parse: \"" + source + "\". Result: " + formattedResult + " toString: " + parseResult);
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {
        NumberFormat numberFormat0 = NumberFormat.getIntegerInstance(Locale.GERMANY);
        if (!(numberFormat0 instanceof DecimalFormat integerFormat)) {
            throw new RuntimeException("NumberFormat is not a DecimalFormat");
        }

        NumberFormat numberFormat1 = NumberFormat.getNumberInstance(Locale.GERMANY);
        if (!(numberFormat1 instanceof DecimalFormat decimalFormat)) {
            throw new RuntimeException("NumberFormat is not a DecimalFormat");
        }
        decimalFormat.setMaximumFractionDigits(5);

        NumberFormat numberFormat2 = NumberFormat.getNumberInstance(Locale.GERMANY);
        if (!(numberFormat2 instanceof DecimalFormat bigDecimalFormat)) {
            throw new RuntimeException("NumberFormat is not a DecimalFormat");
        }
        bigDecimalFormat.setMaximumFractionDigits(10);
        bigDecimalFormat.setParseBigDecimal(true);

        System.out.println("---NumberDataTypeFormatter");
        testFormat(null, new NumberDataTypeFormatter<Integer>(integerFormat, null));
        testFormat(null, new NumberDataTypeFormatter<Integer>(integerFormat, () -> "NULL"));
        testFormat(1, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(-1, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(Integer.MAX_VALUE, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(Integer.MIN_VALUE, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(Integer.MIN_VALUE, new NumberDataTypeFormatter<>(NumberFormat.getPercentInstance(Locale.GERMANY), () -> "NULL"));

        testFormat(Long.MAX_VALUE, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(1234.567d, new NumberDataTypeFormatter<>(decimalFormat, () -> "NULL"));
        testFormat(Double.NaN, new NumberDataTypeFormatter<>(decimalFormat, () -> "NULL"));
        testFormat(Double.MAX_VALUE, new NumberDataTypeFormatter<>(decimalFormat, () -> "NULL"));
        testFormat(Double.MIN_VALUE, new NumberDataTypeFormatter<>(decimalFormat, () -> "NULL"));
        testFormat(Double.POSITIVE_INFINITY, new NumberDataTypeFormatter<>(decimalFormat, () -> "NULL"));
        testFormat(Double.NEGATIVE_INFINITY, new NumberDataTypeFormatter<>(decimalFormat, () -> "NULL"));

        testFormat(Short.MAX_VALUE, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(Byte.MAX_VALUE, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(Float.MAX_VALUE, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));

        testFormat(new BigInteger("99999999999999999999999999999999999999"), new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(new BigDecimal("99999999999999999999999999999999999999.99999"), new NumberDataTypeFormatter<>(bigDecimalFormat, () -> "NULL"));

        System.out.println("---NumberDataTypeParser");
        testParse(null, new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toInteger, null, null), integerFormat);
        testParse(null, new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toInteger, () -> -1, null), integerFormat);
        testParse("", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toInteger, null, null), integerFormat);
        testParse("", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toInteger, null, () -> -1), integerFormat);
        testParse("123", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toInteger, null, null), integerFormat);
        testParse("12.345", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toInteger, null, null), integerFormat);
        testParse("a123", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toInteger, null, null), integerFormat);
        testParse("12a3", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toInteger, null, null), integerFormat);
        testParse("1,23", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toInteger, null, null), integerFormat);
        testParse("1,23", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser::toInteger, null, null), decimalFormat);
        testParse("1.23", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toInteger, null, null), integerFormat);
        testParse("-123.456", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toInteger, null, null), integerFormat);
        testParse(String.valueOf(Integer.MAX_VALUE), new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toInteger, null, null), integerFormat);
        testParse(String.valueOf(10L + Integer.MAX_VALUE), new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toInteger, null, null), integerFormat);
        testParse(String.valueOf(Long.MAX_VALUE), new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toInteger, null, null), integerFormat);

        testParse(String.valueOf(Long.MAX_VALUE), new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toLong, null, null), integerFormat);
        testParse("9.223.372.036.854.775.807", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toLong, null, null), integerFormat);
        testParse("9.223.372.036.854.775.808", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toLong, null, null), integerFormat);
        testParse("1.234,567", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser::toDouble, null, null), decimalFormat);
        testParse("1", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser::toDouble, null, null), decimalFormat);
        testParse("NaN", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser::toDouble, null, null), decimalFormat);
        testParse("1.7976931348623157E308", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser::toDouble, null, null), decimalFormat);
        testParse("179.769.313.486.231.570.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser::toDouble, null, null), decimalFormat);
        testParse("∞", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser::toDouble, null, null), decimalFormat);
        testParse("-∞", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser::toDouble, null, null), decimalFormat);

        testParse("99.999.999.999.999.999.999.999.999.999.999.999.999", new NumberDataTypeParser<>(bigDecimalFormat, NumberDataTypeParser::toBigInteger, null, null), bigDecimalFormat);
        testParse("9,99", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser::toBigInteger, null, null), decimalFormat);
        testParse("99.999.999.999.999.999.999.999.999.999.999.999.999,99999", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser::toBigInteger, null, null), decimalFormat);
        testParse("99.999.999.999.999.999.999.999.999.999.999.999.999,99999", new NumberDataTypeParser<>(bigDecimalFormat, NumberDataTypeParser::toBigInteger, null, null), bigDecimalFormat);

        testParse("99.999", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser::toBigDecimal, null, null), bigDecimalFormat);
        testParse("99.999.999.999.999.999.999.999.999.999.999.999.999,99999", new NumberDataTypeParser<>(bigDecimalFormat, NumberDataTypeParser::toBigDecimal, null, null), bigDecimalFormat);
        testParse("∞", new NumberDataTypeParser<>(bigDecimalFormat, NumberDataTypeParser::toBigDecimal, null, null), bigDecimalFormat);

        System.out.println("---GenericDataTypeFormatter");
        testFormat(Long.MAX_VALUE, GenericDataTypeFormatter.forLong(16, null));
        testFormat(Long.MIN_VALUE, GenericDataTypeFormatter.forLong(16, null));
        testFormat(Integer.MAX_VALUE, GenericDataTypeFormatter.forInteger(16, null));
        testFormat(Integer.MIN_VALUE, GenericDataTypeFormatter.forInteger(16, null));
        testFormat(Short.MAX_VALUE, GenericDataTypeFormatter.forShort(16, null));
        testFormat(Short.MIN_VALUE, GenericDataTypeFormatter.forShort(16, null));
        testFormat(Byte.MAX_VALUE, GenericDataTypeFormatter.forByte(16, null));
        testFormat(Byte.MIN_VALUE, GenericDataTypeFormatter.forByte(16, null));
        testFormat(1234.56789d, GenericDataTypeFormatter.forDouble(null));
        testFormat(1234.56789f, GenericDataTypeFormatter.forFloat(null));
        testFormat(BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.TWO), GenericDataTypeFormatter.forBigInteger(16, null));
        testFormat(new BigDecimal("1234.56789"), GenericDataTypeFormatter.forBigDecimal(null));
        testFormat(new BigDecimal("1E+4"), GenericDataTypeFormatter.forBigDecimal(null));
        testFormat(new BigDecimal("1E+4"), GenericDataTypeFormatter.forBigDecimalPlain(null));
        testFormat(new BigDecimal("1E+4"), GenericDataTypeFormatter.forBigDecimalEngineering(null));

        System.out.println("---GenericDataTypeParser");
        testParse("7fffffffffffffff", GenericDataTypeParser.forLong(16, null, null), integerFormat);
        testParse("-8000000000000000", GenericDataTypeParser.forLong(16, null, null), integerFormat);
        testParse("7fffffff", GenericDataTypeParser.forInteger(16, null, null), integerFormat);
        testParse("-80000000", GenericDataTypeParser.forInteger(16, null, null), integerFormat);
        testParse("7fff", GenericDataTypeParser.forShort(16, null, null), integerFormat);
        testParse("-8000", GenericDataTypeParser.forShort(16, null, null), integerFormat);
        testParse("7f", GenericDataTypeParser.forByte(16, null, null), integerFormat);
        testParse("-80", GenericDataTypeParser.forByte(16, null, null), integerFormat);
        testParse("1234.56789", GenericDataTypeParser.forDouble(null, null), integerFormat);
        testParse("1234.56789", GenericDataTypeParser.forFloat(null, null), integerFormat);
        testParse("   111222333444555666777888999   ", GenericDataTypeParser.forDouble(null, null), decimalFormat);
        testParse("   .111222333444555666777888999   ", GenericDataTypeParser.forDouble(null, null), decimalFormat);
        testParse("fffffffffffffffe", GenericDataTypeParser.forBigInteger(16, null, null), bigDecimalFormat);
        testParse("1234.56789", GenericDataTypeParser.forBigDecimal(null, null), bigDecimalFormat);
        testParse("1E+4", GenericDataTypeParser.forBigDecimal(null, null), bigDecimalFormat);
        testParse("10000", GenericDataTypeParser.forBigDecimal(null, null), bigDecimalFormat);
        testParse("10E+3", GenericDataTypeParser.forBigDecimal(null, null), bigDecimalFormat);
        testParse("1.0E+4", GenericDataTypeParser.forBigDecimal(null, null), bigDecimalFormat);
    }

}
