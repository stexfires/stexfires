package stexfires.record.modifier;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.filter.RecordFilter;
import stexfires.record.logger.RecordLogger;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public class LogFilterModifier<T extends TextRecord> implements RecordStreamModifier<T, T> {

    private final RecordFilter<? super T> recordFilter;
    private final Consumer<T> peekConsumer;

    public LogFilterModifier(RecordFilter<? super T> recordFilter,
                             RecordLogger<? super T> validLogger,
                             RecordLogger<? super T> invalidLogger) {
        Objects.requireNonNull(recordFilter);
        Objects.requireNonNull(validLogger);
        Objects.requireNonNull(invalidLogger);
        this.recordFilter = recordFilter;

        peekConsumer = record -> {
            if (recordFilter.isValid(record)) {
                validLogger.log(record);
            } else {
                invalidLogger.log(record);
            }
        };
    }

    @Override
    public final @NotNull Stream<T> modify(Stream<T> recordStream) {
        return recordStream
                .peek(peekConsumer)
                .filter(recordFilter::isValid);
    }

}
