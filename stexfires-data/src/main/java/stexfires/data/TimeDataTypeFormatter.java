package stexfires.data;

import org.jspecify.annotations.Nullable;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @since 0.1
 */
public final class TimeDataTypeFormatter<T extends TemporalAccessor> implements DataTypeFormatter<T> {

    private final DateTimeFormatter dateTimeFormatter;
    private final @Nullable Supplier<@Nullable String> nullSourceSupplier;

    public TimeDataTypeFormatter(DateTimeFormatter dateTimeFormatter,
                                 @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        Objects.requireNonNull(dateTimeFormatter);
        this.dateTimeFormatter = dateTimeFormatter;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    @Override
    public @Nullable String format(@Nullable T source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            try {
                return dateTimeFormatter.format(source);
            } catch (DateTimeException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Formatter, e);
            }
        }
    }

}
