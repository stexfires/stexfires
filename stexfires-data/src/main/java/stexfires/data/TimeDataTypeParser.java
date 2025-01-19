package stexfires.data;

import org.jspecify.annotations.Nullable;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public final class TimeDataTypeParser<T extends TemporalAccessor> implements DataTypeParser<T> {

    private final DateTimeFormatter dateTimeFormatter;
    private final TemporalQuery<T> temporalQuery;
    private final @Nullable Supplier<@Nullable T> nullSourceSupplier;
    private final @Nullable Supplier<@Nullable T> emptySourceSupplier;

    public TimeDataTypeParser(DateTimeFormatter dateTimeFormatter,
                              TemporalQuery<T> temporalQuery,
                              @Nullable Supplier<@Nullable T> nullSourceSupplier,
                              @Nullable Supplier<@Nullable T> emptySourceSupplier) {
        Objects.requireNonNull(dateTimeFormatter);
        Objects.requireNonNull(temporalQuery);
        this.dateTimeFormatter = dateTimeFormatter;
        this.temporalQuery = temporalQuery;
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
                return dateTimeFormatter.parse(source, temporalQuery);
            } catch (DateTimeParseException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, e);
            }
        }
    }

}
