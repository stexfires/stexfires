package stexfires.data;

import org.jspecify.annotations.Nullable;

import java.io.UncheckedIOException;
import java.nio.file.FileSystemNotFoundException;
import java.time.DateTimeException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @see stexfires.data.DataTypeConverters
 * @since 0.1
 */
public final class ConvertingDataTypeFormatter<T, V> implements DataTypeFormatter<T> {

    private final Function<T, V> dataTypeConverter;
    private final DataTypeFormatter<V> dataTypeFormatter;
    private final @Nullable UnaryOperator<@Nullable String> postAdjustment;
    private final @Nullable Supplier<@Nullable String> nullSourceSupplier;

    public ConvertingDataTypeFormatter(Function<T, V> dataTypeConverter,
                                       DataTypeFormatter<V> dataTypeFormatter,
                                       @Nullable UnaryOperator<@Nullable String> postAdjustment,
                                       @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        Objects.requireNonNull(dataTypeConverter);
        Objects.requireNonNull(dataTypeFormatter);
        this.dataTypeConverter = dataTypeConverter;
        this.dataTypeFormatter = dataTypeFormatter;
        this.postAdjustment = postAdjustment;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    @Override
    public @Nullable String format(@Nullable T source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            try {
                String formattedString = dataTypeFormatter.format(dataTypeConverter.apply(source));
                return (postAdjustment == null) ? formattedString : postAdjustment.apply(formattedString);
            } catch (IllegalArgumentException | NullPointerException | UncheckedIOException | ClassCastException |
                     IllegalStateException | IndexOutOfBoundsException | ArithmeticException | DateTimeException |
                     UnsupportedOperationException | FileSystemNotFoundException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Formatter, "Cannot format source: " + e.getClass().getName());
            }
        }
    }

}
