package org.textfiledatatools.core.consumer;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.filter.RecordFilter;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A RecordConsumer consumes a {@link Record}.
 * <p>
 * It must be <code>non-interfering</code> and thread-safe.
 * <p>
 * This is a functional interface whose functional method is {@link #consume(Record)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.Consumer
 * @see java.util.stream.Stream#forEach(Consumer)
 * @see java.util.stream.Stream#forEachOrdered(Consumer)
 * @since 0.1
 */
@FunctionalInterface
public interface RecordConsumer<T extends Record> {

    static <T extends Record> RecordConsumer<T> of(Consumer<T> consumer) {
        Objects.requireNonNull(consumer);
        return consumer::accept;
    }

    static <T extends Record> RecordConsumer<T> concat(RecordConsumer<? super T> firstRecordConsumer,
                                                       RecordConsumer<? super T> secondRecordConsumer) {
        Objects.requireNonNull(firstRecordConsumer);
        Objects.requireNonNull(secondRecordConsumer);
        return (T record) -> {
            firstRecordConsumer.consume(record);
            secondRecordConsumer.consume(record);
        };
    }

    static <T extends Record> RecordConsumer<T> concat(RecordConsumer<? super T> firstRecordConsumer,
                                                       RecordConsumer<? super T> secondRecordConsumer,
                                                       RecordConsumer<? super T> thirdRecordConsumer) {
        Objects.requireNonNull(firstRecordConsumer);
        Objects.requireNonNull(secondRecordConsumer);
        Objects.requireNonNull(thirdRecordConsumer);
        return (T record) -> {
            firstRecordConsumer.consume(record);
            secondRecordConsumer.consume(record);
            thirdRecordConsumer.consume(record);
        };
    }

    static <T extends Record> RecordConsumer<T> conditionalConsumer(List<RecordFilter<? super T>> consumerConditions,
                                                                    List<RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(consumerConditions);
        Objects.requireNonNull(recordConsumers);
        if (consumerConditions.size() != recordConsumers.size()) {
            throw new IllegalArgumentException();
        }
        return (T record) -> {
            for (int i = 0; i < consumerConditions.size(); i++) {
                if (consumerConditions.get(i).isValid(record)) {
                    recordConsumers.get(i).consume(record);
                }
            }
        };
    }

    static <T extends Record> RecordConsumer<T> splitter(RecordFilter<? super T> splitCondition,
                                                         RecordConsumer<? super T> trueRecordConsumer,
                                                         RecordConsumer<? super T> falseRecordConsumer) {
        Objects.requireNonNull(splitCondition);
        Objects.requireNonNull(trueRecordConsumer);
        Objects.requireNonNull(falseRecordConsumer);
        return (T record) -> {
            if (splitCondition.isValid(record)) {
                trueRecordConsumer.consume(record);
            } else {
                falseRecordConsumer.consume(record);
            }
        };
    }

    static <T extends Record> RecordConsumer<T> splitter(Function<? super T, Integer> splitFunction,
                                                         List<RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(splitFunction);
        Objects.requireNonNull(recordConsumers);
        return (T record) -> recordConsumers.get(splitFunction.apply(record)).consume(record);
    }

    void consume(T record) throws UncheckedConsumerException;

    default RecordConsumer<T> andThen(RecordConsumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T record) -> {
            consume(record);
            after.consume(record);
        };
    }

}
