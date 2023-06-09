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
public final class ConvertingDataTypeParser<T, V> implements DataTypeParser<T> {

    private final UnaryOperator<String> preparatoryAdjustment;
    private final DataTypeParser<V> dataTypeParser;
    private final Function<V, T> dataTypeConverter;
    private final Supplier<T> nullSourceSupplier;
    private final Supplier<T> emptySourceSupplier;

    public ConvertingDataTypeParser(@Nullable UnaryOperator<String> preparatoryAdjustment,
                                    @NotNull DataTypeParser<V> dataTypeParser,
                                    @NotNull Function<V, T> dataTypeConverter,
                                    @Nullable Supplier<T> nullSourceSupplier,
                                    @Nullable Supplier<T> emptySourceSupplier) {
        Objects.requireNonNull(dataTypeParser);
        Objects.requireNonNull(dataTypeConverter);
        this.preparatoryAdjustment = preparatoryAdjustment;
        this.dataTypeParser = dataTypeParser;
        this.dataTypeConverter = dataTypeConverter;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    @Override
    public @Nullable T parse(@Nullable String source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else if (source.isEmpty()) {
            return handleEmptySource(emptySourceSupplier);
        } else {
            try {
                String preparedString = (preparatoryAdjustment == null) ? source : preparatoryAdjustment.apply(source);

                return dataTypeConverter.apply(dataTypeParser.parse(preparedString));
            } catch (IllegalArgumentException | NullPointerException | UncheckedIOException | ClassCastException |
                     IllegalStateException | IndexOutOfBoundsException | ArithmeticException | DateTimeException |
                     UnsupportedOperationException | FileSystemNotFoundException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Cannot parse source: " + e.getClass().getName());
            }
        }
    }

}
