package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class TimeDataTypeParser<T extends TemporalAccessor> implements DataTypeParser<T> {

    private final DateTimeFormatter dateTimeFormatter;
    private final TemporalQuery<T> temporalQuery;
    private final Supplier<T> nullSourceSupplier;
    private final Supplier<T> emptySourceSupplier;

    public TimeDataTypeParser(@NotNull DateTimeFormatter dateTimeFormatter,
                              @NotNull TemporalQuery<T> temporalQuery,
                              @Nullable Supplier<T> nullSourceSupplier,
                              @Nullable Supplier<T> emptySourceSupplier) {
        Objects.requireNonNull(dateTimeFormatter);
        Objects.requireNonNull(temporalQuery);
        this.dateTimeFormatter = dateTimeFormatter;
        this.temporalQuery = temporalQuery;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
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
            try {
                return dateTimeFormatter.parse(source, temporalQuery);
            } catch (DateTimeParseException e) {
                throw new DataTypeParseException(e.getMessage());
            }
        }
    }

}