package stexfires.core.message;

import stexfires.core.Record;
import stexfires.core.filter.RecordFilter;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConditionalMessage<T extends Record> implements RecordMessage<T> {

    protected final RecordFilter<? super T> condition;
    protected final RecordMessage<? super Record> trueMessage;
    protected final RecordMessage<? super Record> falseMessage;

    public ConditionalMessage(RecordFilter<? super T> condition,
                              RecordMessage<? super Record> trueMessage,
                              RecordMessage<? super Record> falseMessage) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueMessage);
        Objects.requireNonNull(falseMessage);
        this.condition = condition;
        this.trueMessage = trueMessage;
        this.falseMessage = falseMessage;
    }

    @Override
    public final String createMessage(T record) {
        return condition.isValid(record) ? trueMessage.createMessage(record) : falseMessage.createMessage(record);
    }

}
