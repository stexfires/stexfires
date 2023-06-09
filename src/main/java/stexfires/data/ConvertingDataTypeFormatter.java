package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    private final UnaryOperator<String> postAdjustment;
    private final Supplier<String> nullSourceSupplier;

    public ConvertingDataTypeFormatter(@NotNull Function<T, V> dataTypeConverter,
                                       @NotNull DataTypeFormatter<V> dataTypeFormatter,
                                       @Nullable UnaryOperator<String> postAdjustment,
                                       @Nullable Supplier<String> nullSourceSupplier) {
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
