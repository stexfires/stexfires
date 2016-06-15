package stexfires.core.filter;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;
import stexfires.util.StringCheckType;
import stexfires.util.StringComparisonType;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class MessageFilter<T extends Record> implements RecordFilter<T> {

    protected final RecordMessage<? super T> recordMessage;
    protected final Predicate<String> stringPredicate;

    public MessageFilter(RecordMessage<? super T> recordMessage,
                         StringComparisonType stringComparisonType, String compareMessage) {
        this(recordMessage, stringComparisonType.stringPredicate(compareMessage));
    }

    public MessageFilter(RecordMessage<? super T> recordMessage,
                         StringCheckType stringCheckType) {
        this(recordMessage, stringCheckType.stringPredicate());
    }

    public MessageFilter(RecordMessage<? super T> recordMessage,
                         Predicate<String> stringPredicate) {
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(stringPredicate);
        this.recordMessage = recordMessage;
        this.stringPredicate = stringPredicate;
    }

    @Override
    public boolean isValid(T record) {
        return stringPredicate.test(recordMessage.createMessage(record));
    }

}
