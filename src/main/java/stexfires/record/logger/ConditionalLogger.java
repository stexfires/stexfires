package stexfires.record.logger;

import stexfires.record.TextRecord;
import stexfires.record.filter.RecordFilter;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConditionalLogger<T extends TextRecord> implements RecordLogger<T> {

    private final RecordFilter<? super T> condition;
    private final RecordLogger<? super T> trueLogger;
    private final RecordLogger<? super T> falseLogger;

    public ConditionalLogger(RecordFilter<? super T> condition,
                             RecordLogger<? super T> trueLogger,
                             RecordLogger<? super T> falseLogger) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueLogger);
        Objects.requireNonNull(falseLogger);
        this.condition = condition;
        this.trueLogger = trueLogger;
        this.falseLogger = falseLogger;
    }

    @Override
    public final void log(T record) {
        try {
            if (condition.isValid(record)) {
                trueLogger.log(record);
            } else {
                falseLogger.log(record);
            }
        } catch (NullPointerException | UnsupportedOperationException | ClassCastException | IllegalArgumentException |
                 IllegalStateException e) {
            // Ignore Exception
        }
    }

}
