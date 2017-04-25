package stexfires.io;

import stexfires.core.Record;
import stexfires.core.consumer.ConsumerException;
import stexfires.core.consumer.RecordConsumer;
import stexfires.core.consumer.UncheckedConsumerException;

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

    void flush() throws IOException;

    /**
     * Not synchronized. Use it only with forEachOrdered().
     */
    @Override
    default void consume(T record) throws UncheckedConsumerException {
        try {
            writeRecord(record);
        } catch (ConsumerException | IOException e) {
            throw new UncheckedConsumerException(record, e);
        }
    }

}
