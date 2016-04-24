package org.textfiledatatools.core.consumer;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.logger.RecordLogger;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class LoggerConsumer<T extends Record> implements RecordConsumer<T> {

    private final RecordLogger<? super T> recordLogger;

    public LoggerConsumer(RecordLogger<? super T> recordLogger) {
        Objects.requireNonNull(recordLogger);
        this.recordLogger = recordLogger;
    }

    @Override
    public void consume(T record) {
        recordLogger.log(record);
    }

}
