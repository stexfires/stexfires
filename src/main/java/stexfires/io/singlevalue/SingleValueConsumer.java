package stexfires.io.singlevalue;

import stexfires.io.internal.AbstractWritableConsumer;
import stexfires.record.TextField;
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

        TextField valueField = record.valueField();

        if (!fileSpec.skipNullValue() || valueField.isNotNull()) {
            if (fileSpec.linePrefix() != null) {
                writeString(fileSpec.linePrefix());
            }
            if (valueField.isNotNull()) {
                writeString(valueField.text());
            }
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
