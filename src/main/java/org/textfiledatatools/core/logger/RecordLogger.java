package org.textfiledatatools.core.logger;

import org.textfiledatatools.core.Record;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * A RecordLogger logs a {@link Record}.
 * <p>
 * It should not throw a RuntimeException (like NullPointerException).
 * It must be thread-safe and null-safe.
 * It must be <code>non-interfering</code>.
 * <p>
 * <p>This is a functional interface
 * whose functional method is {@link #log(T)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.Consumer
 * @see java.util.stream.Stream#peek(Consumer)
 * @since 0.1
 */
@FunctionalInterface
public interface RecordLogger<T extends Record> {

    static <T extends Record> RecordLogger<T> concat(RecordLogger<? super T> firstRecordLogger,
                                                     RecordLogger<? super T> secondRecordLogger) {
        Objects.requireNonNull(firstRecordLogger);
        Objects.requireNonNull(secondRecordLogger);
        return (T record) -> {
            firstRecordLogger.log(record);
            secondRecordLogger.log(record);
        };
    }

    static <T extends Record> RecordLogger<T> concat(RecordLogger<? super T> firstRecordLogger,
                                                     RecordLogger<? super T> secondRecordLogger,
                                                     RecordLogger<? super T> thirdRecordLogger) {
        Objects.requireNonNull(firstRecordLogger);
        Objects.requireNonNull(secondRecordLogger);
        Objects.requireNonNull(thirdRecordLogger);
        return (T record) -> {
            firstRecordLogger.log(record);
            secondRecordLogger.log(record);
            thirdRecordLogger.log(record);
        };
    }

    void log(T record);

    default RecordLogger<T> andThen(RecordLogger<? super T> afterRecordLogger) {
        Objects.requireNonNull(afterRecordLogger);
        return (T record) -> {
            log(record);
            afterRecordLogger.log(record);
        };
    }

}
