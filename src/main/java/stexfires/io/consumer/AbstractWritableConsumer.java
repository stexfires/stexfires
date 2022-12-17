package stexfires.io.consumer;

import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public abstract class AbstractWritableConsumer<T extends TextRecord> implements WritableRecordConsumer<T> {

    private final BufferedWriter bufferedWriter;

    protected AbstractWritableConsumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        this.bufferedWriter = bufferedWriter;
    }

    public final BufferedWriter bufferedWriter() {
        return bufferedWriter;
    }

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
    }

    @Override
    public void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException {
    }

    @Override
    public void flush() throws IOException {
        bufferedWriter.flush();
    }

    @Override
    public void close() throws IOException {
        bufferedWriter.close();
    }

}
