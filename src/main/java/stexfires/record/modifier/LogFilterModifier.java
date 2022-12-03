package stexfires.record.modifier;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.filter.RecordFilter;
import stexfires.record.logger.RecordLogger;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class LogFilterModifier<T extends TextRecord> implements RecordStreamModifier<T, T> {

    private final RecordFilter<? super T> recordFilter;
    private final RecordLogger<? super T> validLogger;
    private final RecordLogger<? super T> invalidLogger;

    public LogFilterModifier(RecordFilter<? super T> recordFilter,
                             RecordLogger<? super T> validLogger,
                             RecordLogger<? super T> invalidLogger) {
        Objects.requireNonNull(recordFilter);
        Objects.requireNonNull(validLogger);
        Objects.requireNonNull(invalidLogger);
        this.recordFilter = recordFilter;
        this.validLogger = validLogger;
        this.invalidLogger = invalidLogger;
    }

    @Override
    public final @NotNull Stream<T> modify(Stream<T> recordStream) {
        return recordStream
                .peek(logRecord())
                .filter(recordFilter::isValid);
    }

    protected final Consumer<T> logRecord() {
        return record -> {
            if (recordFilter.isValid(record)) {
                validLogger.log(record);
            } else {
                invalidLogger.log(record);
            }
        };
    }

}
