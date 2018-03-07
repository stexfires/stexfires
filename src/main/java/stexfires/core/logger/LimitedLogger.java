package stexfires.core.logger;

import stexfires.core.Record;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class LimitedLogger<T extends Record> implements RecordLogger<T> {

    protected final RecordLogger<? super T> recordLogger;
    protected final int limit;
    protected final AtomicInteger counter;

    public LimitedLogger(RecordLogger<? super T> recordLogger, int limit) {
        Objects.requireNonNull(recordLogger);
        this.recordLogger = recordLogger;
        this.limit = limit;
        this.counter = new AtomicInteger(0);
    }

    @Override
    public final void log(T record) {
        // get() is faster than getAndIncrement()
        if (counter.get() < limit) {
            if (counter.getAndIncrement() < limit) {
                recordLogger.log(record);
            }
        }
    }

    public final int getLimit() {
        return limit;
    }

}
