package org.textfiledatatools.core.message;

import org.textfiledatatools.core.Record;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * An RecordMessage creates a text message from a {@link Record}.
 * <p>
 * It should not throw a RuntimeException (like NullPointerException).
 * It must be immutable and thread-safe.
 * It must be <code>non-interfering</code>.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
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

    String createMessage(T record);

}
