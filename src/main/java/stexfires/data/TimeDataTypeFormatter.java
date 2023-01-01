package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class TimeDataTypeFormatter<T extends TemporalAccessor> implements DataTypeFormatter<T> {

    private final DateTimeFormatter dateTimeFormatter;
    private final Supplier<String> nullSourceSupplier;

    public TimeDataTypeFormatter(@NotNull DateTimeFormatter dateTimeFormatter,
                                 @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(dateTimeFormatter);
        this.dateTimeFormatter = dateTimeFormatter;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    @Override
    public @Nullable String format(@Nullable T source) throws DataTypeFormatException {
        if (source == null) {
            if (nullSourceSupplier == null) {
                throw new DataTypeFormatException("Source is null.");
            } else {
                return nullSourceSupplier.get();
            }
        } else {
            try {
                return dateTimeFormatter.format(source);
            } catch (DateTimeException e) {
                throw new DataTypeFormatException(e.getMessage());
            }
        }
    }

}
