package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.util.function.NumberPredicates;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.LongPredicate;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class NumberDataTypeParser<T extends Number> implements DataTypeParser<T> {

    private final NumberFormat numberFormat;
    private final Function<Number, T> convertNumberFunction;
    private final Supplier<T> nullSourceSupplier;
    private final Supplier<T> emptySourceSupplier;
    private final Object lock = new Object();

    public NumberDataTypeParser(@NotNull NumberFormat numberFormat,
                                @NotNull Function<Number, T> convertNumberFunction,
                                @Nullable Supplier<T> nullSourceSupplier,
                                @Nullable Supplier<T> emptySourceSupplier) {
        Objects.requireNonNull(numberFormat);
        Objects.requireNonNull(convertNumberFunction);
        this.numberFormat = numberFormat;
        this.convertNumberFunction = convertNumberFunction;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    public static Function<Number, Integer> convertIntegerFunction() {
        LongPredicate rangeNotValid = NumberPredicates.PrimitiveLongPredicates.rangeOfInteger().negate();
        return numberResult -> switch (numberResult) {
            case null -> throw new DataTypeParseException("Parsed number is null.");
            case Long longResult -> {
                if (rangeNotValid.test(longResult)) {
                    throw new DataTypeParseException("Parsed number has an invalid range for integer: " + longResult);
                }
                yield longResult.intValue();
            }
            case Integer integerResult -> integerResult;
            case default ->
                    throw new DataTypeParseException("Parsed number has an unhandled class: " + numberResult.getClass());
        };
    }

    public static Function<Number, Long> convertLongFunction() {
        return numberResult -> switch (numberResult) {
            case null -> throw new DataTypeParseException("Parsed number is null.");
            case Long longResult -> longResult;
            case Integer integerResult -> integerResult.longValue();
            case default ->
                    throw new DataTypeParseException("Parsed number has an unhandled class: " + numberResult.getClass());
        };
    }

    public static Function<Number, Double> convertDoubleFunction() {
        return numberResult -> switch (numberResult) {
            case null -> throw new DataTypeParseException("Parsed number is null.");
            case Double doubleResult -> doubleResult;
            case Long longResult -> longResult.doubleValue();
            case Integer integerResult -> integerResult.doubleValue();
            case default ->
                    throw new DataTypeParseException("Parsed number has an unhandled class: " + numberResult.getClass());
        };
    }

    public static Function<Number, BigInteger> convertBigIntegerFunction() {
        return numberResult -> switch (numberResult) {
            case null -> throw new DataTypeParseException("Parsed number is null.");
            case BigDecimal bigDecimalResult -> {
                try {
                    yield bigDecimalResult.toBigIntegerExact();
                } catch (ArithmeticException e) {
                    throw new DataTypeParseException("Parsed number cannot be converted without loss of data: " + bigDecimalResult);
                }
            }
            case BigInteger bigIntegerResult -> bigIntegerResult;
            case Double doubleResult -> {
                if (doubleResult.isNaN() || doubleResult.isInfinite()) {
                    throw new DataTypeParseException("Parsed number is infinite or NaN: " + doubleResult);
                }
                try {
                    yield BigDecimal.valueOf(doubleResult).toBigIntegerExact();
                } catch (ArithmeticException e) {
                    throw new DataTypeParseException("Parsed number cannot be converted without loss of data: " + doubleResult);
                }
            }
            case Long longResult -> BigInteger.valueOf(longResult);
            case Integer integerResult -> BigInteger.valueOf(integerResult);
            case default ->
                    throw new DataTypeParseException("Parsed number has an unhandled class: " + numberResult.getClass());
        };
    }

    public static Function<Number, BigDecimal> convertBigDecimalFunction() {
        return numberResult -> switch (numberResult) {
            case null -> throw new DataTypeParseException("Parsed number is null.");
            case BigDecimal bigDecimalResult -> bigDecimalResult;
            case BigInteger bigIntegerResult -> new BigDecimal(bigIntegerResult);
            case Double doubleResult -> {
                if (doubleResult.isNaN() || doubleResult.isInfinite()) {
                    throw new DataTypeParseException("Parsed number is infinite or NaN: " + doubleResult);
                }
                yield BigDecimal.valueOf(doubleResult);
            }
            case Long longResult -> BigDecimal.valueOf(longResult);
            case Integer integerResult -> BigDecimal.valueOf(integerResult);
            case default ->
                    throw new DataTypeParseException("Parsed number has an unhandled class: " + numberResult.getClass());
        };
    }

    @Override
    public @Nullable T parse(@Nullable String source) throws DataTypeParseException {
        if (source == null) {
            if (nullSourceSupplier == null) {
                throw new DataTypeParseException("Source is null.");
            } else {
                return nullSourceSupplier.get();
            }
        } else if (source.isEmpty()) {
            if (emptySourceSupplier == null) {
                throw new DataTypeParseException("Source is empty.");
            } else {
                return emptySourceSupplier.get();
            }
        } else {
            ParsePosition parsePosition = new ParsePosition(0);
            Number numberResult;
            synchronized (lock) {
                numberResult = numberFormat.parse(source, parsePosition);
            }
            // Check ParsePosition
            if (parsePosition.getErrorIndex() != -1) {
                throw new DataTypeParseException("An error occurred while parsing the source \"" + source + "\" to a number at position: " +
                        parsePosition.getErrorIndex());
            }
            if (source.length() != parsePosition.getIndex()) {
                throw new DataTypeParseException("The source \"" + source + "\" was not completely parsed into a number at position: " +
                        parsePosition.getIndex());
            }
            return convertNumberFunction.apply(numberResult);
        }
    }

}
