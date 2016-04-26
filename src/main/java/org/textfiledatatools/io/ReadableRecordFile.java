package org.textfiledatatools.io;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.producer.RecordProducer;
import org.textfiledatatools.core.producer.UncheckedProducerException;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface ReadableRecordFile<T extends Record> extends RecordFile, RecordProducer<T> {

    RecordProducer<T> newProducer() throws IOException;

    @Override
    default Stream<T> produceStream() throws UncheckedProducerException {
        try {
            return newProducer().produceStream();
        } catch (IOException e) {
            throw new UncheckedProducerException(e);
        }
    }

}
