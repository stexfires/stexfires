package org.textfiledatatools.core.producer;

import org.textfiledatatools.core.Record;

import java.util.stream.Stream;

/**
 * A RecordProducer produces a {@link Stream} of {@link Record}s.
 * <p>
 * This is a functional interface whose functional method is {@link #produceStream()}.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
@FunctionalInterface
public interface RecordProducer<T extends Record> {

    Stream<T> produceStream() throws UncheckedProducerException;

}
