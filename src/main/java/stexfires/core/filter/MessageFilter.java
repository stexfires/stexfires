package stexfires.core.filter;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;
import stexfires.util.StringCheckType;
import stexfires.util.StringComparisonType;

import java.util.Collection;
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

    public static <T extends Record> MessageFilter<T> equalTo(RecordMessage<? super T> recordMessage,
                                                              String compareMessage) {
        return new MessageFilter<>(recordMessage,
                StringComparisonType.EQUALS.stringPredicate(compareMessage));
    }

    public static <T extends Record> MessageFilter<T> notNull(RecordMessage<? super T> recordMessage) {
        return new MessageFilter<>(recordMessage,
                StringCheckType.NOT_NULL.stringPredicate());
    }

    public static <T extends Record> MessageFilter<T> containedIn(RecordMessage<? super T> recordMessage,
                                                                  Collection<String> messages) {
        return new MessageFilter<>(recordMessage,
                messages::contains);
    }

    @Override
    public boolean isValid(T record) {
        return messagePredicate.test(recordMessage.createMessage(record));
    }

}

