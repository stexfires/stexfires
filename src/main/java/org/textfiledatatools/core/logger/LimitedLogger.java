package org.textfiledatatools.core.logger;

import org.textfiledatatools.core.Record;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class LimitedLogger<T extends Record> implements RecordLogger<T> {

    private final RecordLogger<T> recordLogger;
    private final int limit;
    private final AtomicInteger counter;

    public LimitedLogger(RecordLogger<T> recordLogger, int limit) {
        Objects.requireNonNull(recordLogger);
        this.recordLogger = recordLogger;
        this.limit = limit;
        this.counter = new AtomicInteger(0);
    }

    @Override
    public void log(T record) {
        if (counter.get() < limit) {
            if (counter.getAndIncrement() < limit) {
                recordLogger.log(record);
            }
        }
    }

}
