package stexfires.core.consumer;

import stexfires.core.Record;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * A RecordConsumer consumes a {@link Record}.
 * <p>
 * It must be <code>thread-safe</code> and <code>non-interfering</code>.
 * <p>
 * It is expected to operate via side-effects.
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

    void consume(T record) throws UncheckedConsumerException;

    default Consumer<T> asConsumer() {
        return this::consume;
    }

    default RecordConsumer<T> andThen(RecordConsumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T record) -> {
            consume(record);
            after.consume(record);
        };
    }

}
