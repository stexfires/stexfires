package stexfires.io.singlevalue;

import stexfires.io.internal.AbstractWritableConsumer;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class SingleValueConsumer extends AbstractWritableConsumer<ValueRecord> {

    private final SingleValueFileSpec fileSpec;

    public SingleValueConsumer(BufferedWriter bufferedWriter, SingleValueFileSpec fileSpec) {
        super(bufferedWriter);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeBefore();

        if (fileSpec.textBefore() != null) {
            writeString(fileSpec.textBefore());
            writeLineSeparator(fileSpec.lineSeparator());
        }
    }

    @Override
    public void writeRecord(ValueRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        String value = record.value();
        if (value != null) {
            writeString(value);
            writeLineSeparator(fileSpec.lineSeparator());
        } else if (!fileSpec.skipNullValue()) {
            writeLineSeparator(fileSpec.lineSeparator());
        }
    }

    @Override
    public void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeAfter();

        if (fileSpec.textAfter() != null) {
            writeString(fileSpec.textAfter());
            writeLineSeparator(fileSpec.lineSeparator());
        }
    }

}
