package stexfires.core.producer;

import stexfires.core.TextRecord;

import java.util.stream.Stream;

/**
 * A RecordProducer produces a {@link Stream} of {@link stexfires.core.TextRecord}s.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #produceStream()}.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
@FunctionalInterface
public interface RecordProducer<T extends TextRecord> {

    Stream<T> produceStream() throws UncheckedProducerException;

}
