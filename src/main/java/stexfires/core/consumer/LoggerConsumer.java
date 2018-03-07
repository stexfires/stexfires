package stexfires.core.consumer;

import stexfires.core.Record;
import stexfires.core.logger.RecordLogger;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class LoggerConsumer<T extends Record> implements RecordConsumer<T> {

    protected final RecordLogger<? super T> recordLogger;

    public LoggerConsumer(RecordLogger<? super T> recordLogger) {
        Objects.requireNonNull(recordLogger);
        this.recordLogger = recordLogger;
    }

    @Override
    public final void consume(T record) {
        recordLogger.log(record);
    }

}
