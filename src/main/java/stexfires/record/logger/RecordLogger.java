package stexfires.record.logger;

import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * A RecordLogger logs a {@link stexfires.record.TextRecord}.
 * <p>
 * It must be {@code thread-safe} and {@code non-interfering}.
 * <p>
 * It should not throw a RuntimeException (like NullPointerException).
 * It is expected to operate via side effects.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #log(stexfires.record.TextRecord)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.Consumer
 * @see java.util.stream.Stream#peek(Consumer)
 * @since 0.1
 */
@FunctionalInterface
public interface RecordLogger<T extends TextRecord> {

    static <T extends TextRecord> RecordLogger<T> of(Consumer<T> consumer) {
        Objects.requireNonNull(consumer);
        return consumer::accept;
    }

    static <T extends TextRecord> RecordLogger<T> concat(RecordLogger<? super T> firstRecordLogger,
                                                         RecordLogger<? super T> secondRecordLogger) {
        Objects.requireNonNull(firstRecordLogger);
        Objects.requireNonNull(secondRecordLogger);
        return record -> {
            firstRecordLogger.log(record);
            secondRecordLogger.log(record);
        };
    }

    static <T extends TextRecord> RecordLogger<T> concat(RecordLogger<? super T> firstRecordLogger,
                                                         RecordLogger<? super T> secondRecordLogger,
                                                         RecordLogger<? super T> thirdRecordLogger) {
        Objects.requireNonNull(firstRecordLogger);
        Objects.requireNonNull(secondRecordLogger);
        Objects.requireNonNull(thirdRecordLogger);
        return record -> {
            firstRecordLogger.log(record);
            secondRecordLogger.log(record);
            thirdRecordLogger.log(record);
        };
    }

    void log(T record);

    default Consumer<T> asConsumer() {
        return this::log;
    }

    default RecordLogger<T> andThen(RecordLogger<? super T> afterRecordLogger) {
        Objects.requireNonNull(afterRecordLogger);
        return record -> {
            log(record);
            afterRecordLogger.log(record);
        };
    }

}
