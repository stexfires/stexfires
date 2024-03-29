package stexfires.io.consumer;

import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.RecordConsumer;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * @since 0.1
 */
public interface WritableRecordConsumer<CTR extends TextRecord> extends RecordConsumer<CTR>, Closeable, Flushable {

    void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException;

    void writeRecord(CTR record) throws ConsumerException, UncheckedConsumerException, IOException;

    void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException;

    /**
     * Not synchronized. Use it only with forEachOrdered().
     */
    @Override
    default void consume(CTR record) throws UncheckedConsumerException {
        try {
            writeRecord(record);
        } catch (ConsumerException e) {
            throw new UncheckedConsumerException(e);
        } catch (IOException e) {
            throw new UncheckedConsumerException(new ConsumerException(record, e));
        }
    }

}
