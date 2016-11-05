package stexfires.core.logger;

import stexfires.core.Record;
import stexfires.core.filter.RecordFilter;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConditionalLogger<T extends Record> implements RecordLogger<T> {

    protected final RecordFilter<? super T> condition;
    protected final RecordLogger<? super T> trueLogger;
    protected final RecordLogger<? super T> falseLogger;

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
    public void log(T record) {
        if (condition.isValid(record)) {
            trueLogger.log(record);
        } else {
            falseLogger.log(record);
        }
    }

}
