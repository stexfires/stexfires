package stexfires.core.filter;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;
import stexfires.util.StringCheckType;
import stexfires.util.StringComparisonType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

import static stexfires.util.StringCheckType.NOT_NULL;
import static stexfires.util.StringCheckType.NULL;
import static stexfires.util.StringComparisonType.EQUALS;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class MessageFilter<T extends Record> implements RecordFilter<T> {

    private final RecordMessage<? super T> recordMessage;
    private final Predicate<String> messagePredicate;

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
        return compare(recordMessage, EQUALS, compareMessage);
    }

    public static <T extends Record> MessageFilter<T> isNotNull(RecordMessage<? super T> recordMessage) {
        return check(recordMessage, NOT_NULL);
    }

    public static <T extends Record> MessageFilter<T> isNull(RecordMessage<? super T> recordMessage) {
        return check(recordMessage, NULL);
    }

    public static <T extends Record> MessageFilter<T> containedIn(RecordMessage<? super T> recordMessage,
                                                                  Collection<String> messages) {
        return new MessageFilter<>(recordMessage,
                messages::contains);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static <T extends Record> MessageFilter<T> containedIn(RecordMessage<? super T> recordMessage,
                                                                  String... messages) {
        return containedIn(recordMessage, Arrays.asList(messages));
    }

    @Override
    public final boolean isValid(T record) {
        return messagePredicate.test(recordMessage.createMessage(record));
    }

}

