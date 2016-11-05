package stexfires.core.consumer;

import stexfires.core.Record;
import stexfires.core.filter.RecordFilter;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConditionalConsumer<T extends Record> implements RecordConsumer<T> {

    protected final RecordFilter<? super T> condition;
    protected final RecordConsumer<? super T> trueConsumer;
    protected final RecordConsumer<? super T> falseConsumer;

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
    public void consume(T record) {
        if (condition.isValid(record)) {
            trueConsumer.consume(record);
        } else {
            falseConsumer.consume(record);
        }
    }

}
