package stexfires.io.combined;

import stexfires.core.Record;
import stexfires.core.consumer.ConsumerException;
import stexfires.io.WritableRecordConsumer;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CombinedWritableRecordConsumer<T extends Record> implements WritableRecordConsumer<T> {

    protected final WritableRecordConsumer<? super T> firstConsumer;
    protected final WritableRecordConsumer<? super T> secondConsumer;

    public CombinedWritableRecordConsumer(WritableRecordConsumer<? super T> firstConsumer,
                                          WritableRecordConsumer<? super T> secondConsumer) {
        Objects.requireNonNull(firstConsumer);
        Objects.requireNonNull(secondConsumer);
        this.firstConsumer = firstConsumer;
        this.secondConsumer = secondConsumer;
    }

    @Override
    public void writeBefore() throws IOException {
        firstConsumer.writeBefore();
        secondConsumer.writeBefore();
    }

    @Override
    public void writeRecord(T record) throws IOException, ConsumerException {
        firstConsumer.writeRecord(record);
        secondConsumer.writeRecord(record);
    }

    @Override
    public void writeAfter() throws IOException {
        firstConsumer.writeAfter();
        secondConsumer.writeAfter();
    }

    @Override
    public void flush() throws IOException {
        firstConsumer.flush();
        secondConsumer.flush();
    }

    @Override
    public void close() throws IOException {
        IOException e1 = null;
        try {
            firstConsumer.close();
        } catch (IOException e) {
            e1 = e;
        }
        IOException e2 = null;
        try {
            secondConsumer.close();
        } catch (IOException e) {
            e2 = e;
        }
        if (e2 != null) {
            if (e1 != null) {
                e2.addSuppressed(e1);
            }
            throw e2;
        }
        if (e1 != null) {
            throw e1;
        }
    }

}
