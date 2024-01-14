package stexfires.record.filter;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.message.RecordMessage;
import stexfires.util.function.StringPredicates;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @since 0.1
 */
public class MessageFilter<T extends TextRecord> implements RecordFilter<T> {

    private final RecordMessage<? super T> recordMessage;
    private final Predicate<@Nullable String> messagePredicate;

    public MessageFilter(RecordMessage<? super T> recordMessage,
                         Predicate<@Nullable String> messagePredicate) {
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(messagePredicate);
        this.recordMessage = recordMessage;
        this.messagePredicate = messagePredicate;
    }

    public static <T extends TextRecord> MessageFilter<T> equalTo(RecordMessage<? super T> recordMessage,
                                                                  @Nullable String compareMessage) {
        return new MessageFilter<>(recordMessage, StringPredicates.equals(compareMessage));
    }

    public static <T extends TextRecord> MessageFilter<T> isNotNull(RecordMessage<? super T> recordMessage) {
        return new MessageFilter<>(recordMessage, StringPredicates.isNotNull());
    }

    public static <T extends TextRecord> MessageFilter<T> isNull(RecordMessage<? super T> recordMessage) {
        return new MessageFilter<>(recordMessage, StringPredicates.isNull());
    }

    public static <T extends TextRecord> MessageFilter<T> containedIn(RecordMessage<? super T> recordMessage,
                                                                      Collection<String> messages) {
        Objects.requireNonNull(messages);
        return new MessageFilter<>(recordMessage, messages::contains);
    }

    public static <T extends TextRecord> MessageFilter<T> containedIn(RecordMessage<? super T> recordMessage,
                                                                      String... messages) {
        Objects.requireNonNull(messages);
        return containedIn(recordMessage, Arrays.asList(messages));
    }

    @Override
    public final boolean isValid(T record) {
        return messagePredicate.test(recordMessage.createMessage(record));
    }

}

