package org.textfiledatatools.core.message;

import org.textfiledatatools.core.Record;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * An RecordMessage creates a text message from a {@link Record}.
 * <p>
 * It must be immutable and thread-safe.
 * It must be <code>non-interfering</code>.
 * <p>
 * <p>This is a functional interface
 * whose functional method is {@link #createMessage(T)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.Function
 * @see org.textfiledatatools.core.logger.RecordLogger
 * @since 0.1
 */
@FunctionalInterface
public interface RecordMessage<T extends Record> {

    static <T extends Record> RecordMessage<T> addPrefixLocalTime(String delimiter,
                                                                  RecordMessage<T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(delimiter);
        return (T record) -> LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + delimiter + recordMessage.createMessage(record);
    }

    static <T extends Record> RecordMessage<T> addPrefixThreadName(String delimiter,
                                                                   RecordMessage<T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(delimiter);
        return (T record) -> Thread.currentThread().getName() + delimiter + recordMessage.createMessage(record);
    }

    static <T extends Record> RecordMessage<T> addPrefix(String prefix,
                                                         RecordMessage<T> recordMessage) {
        return addPrefixAndPostfix(prefix, recordMessage, null);
    }

    static <T extends Record> RecordMessage<T> addPostfix(RecordMessage<T> recordMessage,
                                                          String postfix) {
        return addPrefixAndPostfix(null, recordMessage, postfix);
    }

    static <T extends Record> RecordMessage<T> addPrefixAndPostfix(String prefix,
                                                                   RecordMessage<T> recordMessage,
                                                                   String postfix) {
        Objects.requireNonNull(recordMessage);
        if (prefix != null && postfix != null) {
            return (T record) -> prefix + recordMessage.createMessage(record) + postfix;
        } else if (prefix != null) {
            return (T record) -> prefix + recordMessage.createMessage(record);
        } else if (postfix != null) {
            return (T record) -> recordMessage.createMessage(record) + postfix;
        }
        return recordMessage;
    }

    static <T extends Record> RecordMessage<T> concat(RecordMessage<? super T> firstRecordMessage,
                                                      RecordMessage<? super T> secondRecordMessage) {
        Objects.requireNonNull(firstRecordMessage);
        Objects.requireNonNull(secondRecordMessage);
        return (T record) -> firstRecordMessage.createMessage(record) + secondRecordMessage.createMessage(record);
    }

    static <T extends Record> RecordMessage<T> concat(RecordMessage<? super T> firstRecordMessage,
                                                      RecordMessage<? super T> secondRecordMessage,
                                                      RecordMessage<? super T> thirdRecordMessage) {
        Objects.requireNonNull(firstRecordMessage);
        Objects.requireNonNull(secondRecordMessage);
        Objects.requireNonNull(thirdRecordMessage);
        return (T record) -> firstRecordMessage.createMessage(record)
                + secondRecordMessage.createMessage(record)
                + thirdRecordMessage.createMessage(record);
    }

    String createMessage(T record);

}
