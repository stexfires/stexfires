package org.textfiledatatools.core.logger;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.filter.RecordFilter;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A RecordLogger logs a {@link Record}.
 * <p>
 * It should not throw a RuntimeException (like NullPointerException).
 * It must be thread-safe and null-safe.
 * It must be <code>non-interfering</code>.
 * <p>
 * This is a functional interface whose functional method is {@link #log(Record)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.Consumer
 * @see java.util.stream.Stream#peek(Consumer)
 * @since 0.1
 */
@FunctionalInterface
public interface RecordLogger<T extends Record> {

    static <T extends Record> RecordLogger<T> of(Consumer<T> consumer) {
        Objects.requireNonNull(consumer);
        return consumer::accept;
    }

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

    static <T extends Record> RecordLogger<T> conditionalLogger(List<RecordFilter<? super T>> loggerConditions,
                                                                List<RecordLogger<? super T>> recordLoggers) {
        Objects.requireNonNull(loggerConditions);
        Objects.requireNonNull(recordLoggers);
        if (loggerConditions.size() != recordLoggers.size()) {
            throw new IllegalArgumentException();
        }
        return (T record) -> {
            for (int i = 0; i < loggerConditions.size(); i++) {
                if (loggerConditions.get(i).isValid(record)) {
                    recordLoggers.get(i).log(record);
                }
            }
        };
    }

    static <T extends Record> RecordLogger<T> splitter(RecordFilter<? super T> splitCondition,
                                                       RecordLogger<? super T> trueRecordLogger,
                                                       RecordLogger<? super T> falseRecordLogger) {
        Objects.requireNonNull(splitCondition);
        Objects.requireNonNull(trueRecordLogger);
        Objects.requireNonNull(falseRecordLogger);
        return (T record) -> {
            if (splitCondition.isValid(record)) {
                trueRecordLogger.log(record);
            } else {
                falseRecordLogger.log(record);
            }
        };
    }

    static <T extends Record> RecordLogger<T> splitter(Function<? super T, Integer> splitFunction,
                                                       List<RecordLogger<? super T>> recordLoggers) {
        Objects.requireNonNull(splitFunction);
        Objects.requireNonNull(recordLoggers);
        return (T record) -> recordLoggers.get(splitFunction.apply(record)).log(record);
    }

    void log(T record);

    default Consumer<T> asConsumer() {
        return this::log;
    }

    default RecordLogger<T> andThen(RecordLogger<? super T> afterRecordLogger) {
        Objects.requireNonNull(afterRecordLogger);
        return (T record) -> {
            log(record);
            afterRecordLogger.log(record);
        };
    }

}
