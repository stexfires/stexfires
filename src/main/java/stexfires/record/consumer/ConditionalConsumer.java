package stexfires.record.consumer;

import stexfires.record.TextRecord;
import stexfires.record.filter.RecordFilter;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConditionalConsumer<T extends TextRecord> implements RecordConsumer<T> {

    private final RecordFilter<? super T> condition;
    private final RecordConsumer<? super T> trueConsumer;
    private final RecordConsumer<? super T> falseConsumer;

    public ConditionalConsumer(RecordFilter<? super T> condition,
                               RecordConsumer<? super T> trueConsumer,
                               RecordConsumer<? super T> falseConsumer) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueConsumer);
        Objects.requireNonNull(falseConsumer);
        this.condition = condition;
        this.trueConsumer = trueConsumer;
        this.falseConsumer = falseConsumer;
    }

    @Override
    public final void consume(T record) {
        if (condition.isValid(record)) {
            trueConsumer.consume(record);
        } else {
            falseConsumer.consume(record);
        }
    }

}
