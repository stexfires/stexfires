package stexfires.record.consumer;

import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * A RecordConsumer consumes a {@link stexfires.record.TextRecord}.
 * <p>
 * It must be {@code thread-safe} and {@code non-interfering}.
 * <p>
 * It is expected to operate via side effects.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #consume(stexfires.record.TextRecord)}.
 *
 * @see java.util.function.Consumer
 * @see java.util.stream.Stream#forEach(Consumer)
 * @see java.util.stream.Stream#forEachOrdered(Consumer)
 * @since 0.1
 */
@FunctionalInterface
public interface RecordConsumer<T extends TextRecord> {

    static <T extends TextRecord> RecordConsumer<T> ofConsumer(Consumer<T> consumer) {
        Objects.requireNonNull(consumer);
        return consumer::accept;
    }

    static <T extends TextRecord> RecordConsumer<T> concat(RecordConsumer<? super T> firstRecordConsumer,
                                                           RecordConsumer<? super T> secondRecordConsumer) {
        Objects.requireNonNull(firstRecordConsumer);
        Objects.requireNonNull(secondRecordConsumer);
        return record -> {
            firstRecordConsumer.consume(record);
            secondRecordConsumer.consume(record);
        };
    }

    static <T extends TextRecord> RecordConsumer<T> concat(RecordConsumer<? super T> firstRecordConsumer,
                                                           RecordConsumer<? super T> secondRecordConsumer,
                                                           RecordConsumer<? super T> thirdRecordConsumer) {
        Objects.requireNonNull(firstRecordConsumer);
        Objects.requireNonNull(secondRecordConsumer);
        Objects.requireNonNull(thirdRecordConsumer);
        return record -> {
            firstRecordConsumer.consume(record);
            secondRecordConsumer.consume(record);
            thirdRecordConsumer.consume(record);
        };
    }

    void consume(T record) throws UncheckedConsumerException;

    default Consumer<T> asConsumer() {
        return this::consume;
    }

    default RecordConsumer<T> andThen(RecordConsumer<? super T> afterRecordConsumer) {
        Objects.requireNonNull(afterRecordConsumer);
        return record -> {
            consume(record);
            afterRecordConsumer.consume(record);
        };
    }

}
