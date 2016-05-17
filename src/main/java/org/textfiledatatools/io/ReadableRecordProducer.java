package org.textfiledatatools.io;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.producer.ProducerException;
import org.textfiledatatools.core.producer.RecordProducer;
import org.textfiledatatools.core.producer.UncheckedProducerException;

import java.io.Closeable;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface ReadableRecordProducer<T extends Record> extends RecordProducer<T>, Closeable {

    void readBefore() throws IOException;

    Stream<T> readRecords() throws IOException, ProducerException;

    void readAfter() throws IOException;

    @Override
    default Stream<T> produceStream() throws UncheckedProducerException {
        try {
            return readRecords();
        } catch (ProducerException | IOException e) {
            throw new UncheckedProducerException(e);
        }
    }

}
