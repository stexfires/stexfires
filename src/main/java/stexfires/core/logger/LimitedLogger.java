package stexfires.core.logger;

import stexfires.core.TextRecord;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class LimitedLogger<T extends TextRecord> implements RecordLogger<T> {

    private final RecordLogger<? super T> recordLogger;
    private final int limit;
    private final AtomicInteger counter;

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
