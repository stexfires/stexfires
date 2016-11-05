package stexfires.core.modifier;

import stexfires.core.Record;
import stexfires.core.filter.RecordFilter;
import stexfires.core.logger.RecordLogger;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class LogFilterModifier<T extends Record> implements RecordStreamModifier<T, T> {

    protected final RecordFilter<? super T> recordFilter;
    protected final RecordLogger<? super T> validLogger;
    protected final RecordLogger<? super T> invalidLogger;

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
    public Stream<T> modify(Stream<T> recordStream) {
        return recordStream
                .peek(logRecord())
                .filter(recordFilter::isValid);
    }

    protected Consumer<T> logRecord() {
        return (T record) -> {
            if (recordFilter.isValid(record)) {
                validLogger.log(record);
            } else {
                invalidLogger.log(record);
            }
        };
    }

}
