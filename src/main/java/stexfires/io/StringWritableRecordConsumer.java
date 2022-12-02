package stexfires.io;

import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class StringWritableRecordConsumer<CTR extends TextRecord> implements WritableRecordConsumer<CTR> {

    private final StringWriter stringWriter;
    private final WritableRecordConsumer<CTR> writableRecordConsumer;

    public StringWritableRecordConsumer(WritableRecordFileSpec<CTR> writableRecordFileSpec) {
        Objects.requireNonNull(writableRecordFileSpec);

        this.stringWriter = new StringWriter();
        this.writableRecordConsumer = writableRecordFileSpec.consumer(new BufferedWriter(stringWriter));
    }

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        writableRecordConsumer.writeBefore();
    }

    @Override
    public void writeRecord(CTR record) throws ConsumerException, UncheckedConsumerException, IOException {
        writableRecordConsumer.writeRecord(record);
    }

    @Override
    public void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException {
        writableRecordConsumer.writeAfter();
    }

    @Override
    public void consume(CTR record) throws UncheckedConsumerException {
        writableRecordConsumer.consume(record);
    }

    @Override
    public void flush() throws IOException {
        writableRecordConsumer.flush();
    }

    @Override
    public void close() throws IOException {
        writableRecordConsumer.close();
    }

    public String consumedString() throws UncheckedConsumerException {
        try {
            writableRecordConsumer.flush();
        } catch (IOException e) {
            throw new UncheckedConsumerException(new ConsumerException(e));
        }
        return stringWriter.toString();
    }

}
