package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class DateInstantDataTypeParser implements DataTypeParser<Instant> {

    private final DateTimeFormatter dateTimeFormatter;
    private final Supplier<Instant> nullSourceSupplier;
    private final Supplier<Instant> emptySourceSupplier;

    public DateInstantDataTypeParser(@NotNull DateTimeFormatter dateTimeFormatter,
                                     @Nullable Supplier<Instant> nullSourceSupplier,
                                     @Nullable Supplier<Instant> emptySourceSupplier) {
        Objects.requireNonNull(dateTimeFormatter);
        this.dateTimeFormatter = dateTimeFormatter;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    @Override
    public @Nullable Instant parse(@Nullable String source) throws DataTypeParseException {
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
                return dateTimeFormatter.parse(source, Instant::from);
            } catch (DateTimeParseException e) {
                throw new DataTypeParseException(e.getMessage());
            }
        }
    }

}
