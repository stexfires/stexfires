package stexfires.io.consumer;

import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.util.function.StringUnaryOperators;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * @since 0.1
 */
public final class StringWritableRecordConsumer<CTR extends TextRecord, WRC extends WritableRecordConsumer<CTR>>
        implements WritableRecordConsumer<CTR> {

    private final WritableRecordFileSpec<CTR, WRC> writableRecordFileSpec;
    private final StringWriter stringWriter;
    private final WRC writableRecordConsumer;

    public StringWritableRecordConsumer(WritableRecordFileSpec<CTR, WRC> writableRecordFileSpec) {
        Objects.requireNonNull(writableRecordFileSpec);

        this.writableRecordFileSpec = writableRecordFileSpec;
        stringWriter = new StringWriter();
        writableRecordConsumer = writableRecordFileSpec.consumer(new BufferedWriter(stringWriter));
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

    public WritableRecordFileSpec<CTR, WRC> writableRecordFileSpec() {
        return writableRecordFileSpec;
    }

    public WRC writableRecordConsumer() {
        return writableRecordConsumer;
    }

    public String consumedString(boolean removeLastLineSeparator) throws UncheckedConsumerException {
        try {
            writableRecordConsumer.flush();
        } catch (IOException e) {
            throw new UncheckedConsumerException(new ConsumerException(e));
        }
        return removeLastLineSeparator
                ? Objects.requireNonNull(StringUnaryOperators.removeStringFromEnd(writableRecordFileSpec.consumerLineSeparator().string())
                                                             .apply(stringWriter.toString()))
                : stringWriter.toString();
    }

}
