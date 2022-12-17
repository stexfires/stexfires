package stexfires.io.combined;

import stexfires.io.consumer.WritableRecordConsumer;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class CombinedWritableRecordConsumer<CTR extends TextRecord> implements WritableRecordConsumer<CTR> {

    private final WritableRecordConsumer<? super CTR> firstConsumer;
    private final WritableRecordConsumer<? super CTR> secondConsumer;

    public CombinedWritableRecordConsumer(WritableRecordConsumer<? super CTR> firstConsumer,
                                          WritableRecordConsumer<? super CTR> secondConsumer) {
        Objects.requireNonNull(firstConsumer);
        Objects.requireNonNull(secondConsumer);
        this.firstConsumer = firstConsumer;
        this.secondConsumer = secondConsumer;
    }

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        firstConsumer.writeBefore();
        secondConsumer.writeBefore();
    }

    @Override
    public void writeRecord(CTR record) throws ConsumerException, UncheckedConsumerException, IOException {
        firstConsumer.writeRecord(record);
        secondConsumer.writeRecord(record);
    }

    @Override
    public void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException {
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
