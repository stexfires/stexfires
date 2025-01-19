package stexfires.data;

import org.jspecify.annotations.Nullable;
import stexfires.util.function.NumberPredicates;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public final class NumberDataTypeParser<T extends Number> implements DataTypeParser<T> {

    private final NumberFormat numberFormat;
    private final Function<Number, @Nullable T> convertNumberFunction;
    private final @Nullable Supplier<@Nullable T> nullSourceSupplier;
    private final @Nullable Supplier<@Nullable T> emptySourceSupplier;
    private final Object lock = new Object();

    public NumberDataTypeParser(NumberFormat numberFormat,
                                Function<Number, @Nullable T> convertNumberFunction,
                                @Nullable Supplier<@Nullable T> nullSourceSupplier,
                                @Nullable Supplier<@Nullable T> emptySourceSupplier) {
        Objects.requireNonNull(numberFormat);
        Objects.requireNonNull(convertNumberFunction);
        this.numberFormat = numberFormat;
        this.convertNumberFunction = convertNumberFunction;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    public static Integer toInteger(Number numberResult) {
        LongPredicate rangeNotValid = NumberPredicates.PrimitiveLongPredicates.rangeOfInteger().negate();
        return switch (numberResult) {
            case null ->
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number is null.");
            case Long longResult -> {
                if (rangeNotValid.test(longResult)) {
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number has an invalid range for integer: " + longResult);
                }
                yield longResult.intValue();
            }
            case Integer integerResult -> integerResult;
            default ->
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number has an unhandled class: " + numberResult.getClass());
        };
    }

    public static Long toLong(Number numberResult) {
        return switch (numberResult) {
            case null ->
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number is null.");
            case Long longResult -> longResult;
            case Integer integerResult -> integerResult.longValue();
            default ->
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number has an unhandled class: " + numberResult.getClass());
        };
    }

    public static Double toDouble(Number numberResult) {
        return switch (numberResult) {
            case null ->
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number is null.");
            case Double doubleResult -> doubleResult;
            case Long longResult -> longResult.doubleValue();
            case Integer integerResult -> integerResult.doubleValue();
            default ->
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number has an unhandled class: " + numberResult.getClass());
        };
    }

    public static BigInteger toBigInteger(Number numberResult) {
        return switch (numberResult) {
            case null ->
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number is null.");
            case BigDecimal bigDecimalResult -> {
                try {
                    yield bigDecimalResult.toBigIntegerExact();
                } catch (ArithmeticException e) {
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number cannot be converted without loss of data: " + bigDecimalResult);
                }
            }
            case BigInteger bigIntegerResult -> bigIntegerResult;
            case Double doubleResult -> {
                if (doubleResult.isNaN() || doubleResult.isInfinite()) {
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number is infinite or NaN: " + doubleResult);
                }
                try {
                    yield BigDecimal.valueOf(doubleResult).toBigIntegerExact();
                } catch (ArithmeticException e) {
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number cannot be converted without loss of data: " + doubleResult);
                }
            }
            case Long longResult -> BigInteger.valueOf(longResult);
            case Integer integerResult -> BigInteger.valueOf(integerResult);
            default ->
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number has an unhandled class: " + numberResult.getClass());
        };
    }

    public static BigDecimal toBigDecimal(Number numberResult) {
        return switch (numberResult) {
            case null ->
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number is null.");
            case BigDecimal bigDecimalResult -> bigDecimalResult;
            case BigInteger bigIntegerResult -> new BigDecimal(bigIntegerResult);
            case Double doubleResult -> {
                if (doubleResult.isNaN() || doubleResult.isInfinite()) {
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number is infinite or NaN: " + doubleResult);
                }
                yield BigDecimal.valueOf(doubleResult);
            }
            case Long longResult -> BigDecimal.valueOf(longResult);
            case Integer integerResult -> BigDecimal.valueOf(integerResult);
            default ->
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Parsed number has an unhandled class: " + numberResult.getClass());
        };
    }

    @Override
    public @Nullable T parse(@Nullable String source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else if (source.isEmpty()) {
            return handleEmptySource(emptySourceSupplier);
        } else {
            ParsePosition parsePosition = new ParsePosition(0);
            Number numberResult;
            synchronized (lock) {
                numberResult = numberFormat.parse(source, parsePosition);
            }
            // Check ParsePosition
            if (parsePosition.getErrorIndex() != -1) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "An error occurred while parsing the source \"" + source + "\" to a number at position: " +
                        parsePosition.getErrorIndex());
            }
            if (source.length() != parsePosition.getIndex()) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "The source \"" + source + "\" was not completely parsed into a number at position: " +
                        parsePosition.getIndex());
            }
            return convertNumberFunction.apply(numberResult);
        }
    }

}
