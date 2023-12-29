package stexfires.record.message;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.filter.RecordFilter;

import java.util.Objects;

/**
 * @since 0.1
 */
public class ConditionalMessage<T extends TextRecord> implements RecordMessage<T> {

    private final RecordFilter<? super T> condition;
    private final RecordMessage<? super T> trueMessage;
    private final RecordMessage<? super T> falseMessage;

    public ConditionalMessage(RecordFilter<? super T> condition,
                              RecordMessage<? super T> trueMessage,
                              RecordMessage<? super T> falseMessage) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueMessage);
        Objects.requireNonNull(falseMessage);
        this.condition = condition;
        this.trueMessage = trueMessage;
        this.falseMessage = falseMessage;
    }

    @Override
    public final @Nullable String createMessage(T record) {
        return condition.isValid(record) ? trueMessage.createMessage(record) : falseMessage.createMessage(record);
    }

}
