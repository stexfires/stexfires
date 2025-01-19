package stexfires.record.logger;

import stexfires.record.TextRecord;

import java.util.*;
import java.util.concurrent.atomic.*;

/**
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
        try {
            // get() is faster than getAndIncrement()
            if (counter.get() < limit) {
                if (counter.getAndIncrement() < limit) {
                    recordLogger.log(record);
                }
            }
        } catch (NullPointerException | UnsupportedOperationException | ClassCastException | IllegalArgumentException |
                 IllegalStateException e) {
            // Ignore Exception
        }
    }

    public final int getLimit() {
        return limit;
    }

}
