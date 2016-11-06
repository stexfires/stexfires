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
    protected final Predicate<String> messagePredicate;

    public MessageFilter(RecordMessage<? super T> recordMessage,
                         Predicate<String> messagePredicate) {
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(messagePredicate);
        this.recordMessage = recordMessage;
        this.messagePredicate = messagePredicate;
    }

    public static <T extends Record> MessageFilter<T> compare(RecordMessage<? super T> recordMessage,
                                                              StringComparisonType stringComparisonType,
                                                              String compareMessage) {
        return new MessageFilter<>(recordMessage,
                stringComparisonType.stringPredicate(compareMessage));
    }

    public static <T extends Record> MessageFilter<T> check(RecordMessage<? super T> recordMessage,
                                                            StringCheckType stringCheckType) {
        return new MessageFilter<>(recordMessage,
                stringCheckType.stringPredicate());
    }

    @Override
    public boolean isValid(T record) {
        return messagePredicate.test(recordMessage.createMessage(record));
    }

}

