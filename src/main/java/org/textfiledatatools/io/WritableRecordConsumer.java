package org.textfiledatatools.io;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.consumer.ConsumerException;
import org.textfiledatatools.core.consumer.RecordConsumer;
import org.textfiledatatools.core.consumer.UncheckedConsumerException;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface WritableRecordConsumer<T extends Record> extends RecordConsumer<T>, Closeable {

    void writeBefore() throws IOException;

    void writeRecord(T record) throws IOException, ConsumerException;

    void writeAfter() throws IOException;

    @Override
    default void consume(T record) throws UncheckedConsumerException {
        try {
            writeRecord(record);
        } catch (ConsumerException | IOException e) {
            throw new UncheckedConsumerException(record, e);
        }
    }

}
