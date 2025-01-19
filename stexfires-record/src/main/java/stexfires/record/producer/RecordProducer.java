package stexfires.record.producer;

import stexfires.record.TextRecord;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * A RecordProducer produces a {@link Stream} of {@link stexfires.record.TextRecord}s.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #produceStream()}.
 *
 * @since 0.1
 */
@FunctionalInterface
public interface RecordProducer<T extends TextRecord> {

    static <T extends TextRecord> RecordProducer<T> ofSupplier(Supplier<Stream<T>> supplier) {
        Objects.requireNonNull(supplier);
        // The "get" function must not return "null"!
        return supplier::get;
    }

    Stream<T> produceStream() throws UncheckedProducerException;

    default Supplier<Stream<T>> asSupplier() {
        return this::produceStream;
    }

}
