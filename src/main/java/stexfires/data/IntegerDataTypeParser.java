package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.util.function.NumberPredicates;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Objects;
import java.util.function.LongPredicate;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class IntegerDataTypeParser implements DataTypeParser<Integer> {

    private final NumberFormat numberFormat;
    private final Supplier<Integer> nullSourceSupplier;
    private final Supplier<Integer> emptySourceSupplier;
    private final LongPredicate rangeNotValid;

    public IntegerDataTypeParser(@NotNull NumberFormat numberFormat,
                                 @Nullable Supplier<Integer> nullSourceSupplier,
                                 @Nullable Supplier<Integer> emptySourceSupplier) {
        Objects.requireNonNull(numberFormat);
        this.numberFormat = numberFormat;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
        rangeNotValid = NumberPredicates.PrimitiveLongPredicates.rangeOfInteger().negate();
    }

    @Override
    public Integer parse(String source) throws DataTypeParseException {
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
            Number numberResult = numberFormat.parse(source, parsePosition);
            // Check ParsePosition
            if (parsePosition.getErrorIndex() != -1) {
                throw new DataTypeParseException("An error occurred while parsing the source \"" + source + "\" to an integer at position: " +
                        parsePosition.getErrorIndex());
            }
            if (source.length() != parsePosition.getIndex()) {
                throw new DataTypeParseException("The source \"" + source + "\" was not completely parsed into an integer at position: " +
                        parsePosition.getIndex());
            }

            // Check and convert result
            return switch (numberResult) {
                case Integer integerResult -> integerResult;
                case Long longResult -> {
                    if (rangeNotValid.test(longResult)) {
                        throw new DataTypeParseException("Parsed number has an invalid range for integer: " + longResult);
                    }
                    yield longResult.intValue();
                }
                case null -> throw new DataTypeParseException("Parsed number is null.");
                case default ->
                        throw new DataTypeParseException("Parsed number has an unhandled class: " + numberResult.getClass());
            };
        }
    }

}
