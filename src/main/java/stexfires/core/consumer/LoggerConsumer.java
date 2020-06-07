package stexfires.core.consumer;

import stexfires.core.TextRecord;
import stexfires.core.logger.RecordLogger;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class LoggerConsumer<T extends TextRecord> implements RecordConsumer<T> {

    private final RecordLogger<? super T> recordLogger;

    public LoggerConsumer(RecordLogger<? super T> recordLogger) {
        Objects.requireNonNull(recordLogger);
        this.recordLogger = recordLogger;
    }

    @Override
    public final void consume(T record) {
        recordLogger.log(record);
    }

}
