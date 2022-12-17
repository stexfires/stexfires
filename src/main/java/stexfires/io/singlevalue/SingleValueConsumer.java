package stexfires.io.singlevalue;

import stexfires.io.internal.AbstractInternalWritableConsumer;
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
public final class SingleValueConsumer extends AbstractInternalWritableConsumer<ValueRecord> {

    private final SingleValueFileSpec fileSpec;

    public SingleValueConsumer(BufferedWriter bufferedWriter, SingleValueFileSpec fileSpec) {
        super(bufferedWriter);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeBefore();

        // write text before
        if (fileSpec.consumerTextBefore() != null) {
            writeString(fileSpec.consumerTextBefore());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }
    }

    @Override
    public void writeRecord(ValueRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        TextField valueField = record.valueField();

        if (!fileSpec.consumerSkipNullValueLines() || valueField.isNotNull()) {
            writeLinePrefix();
            if (valueField.isNotNull()) {
                writeString(valueField.text());
            }
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }
    }

    @Override
    public void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeAfter();

        // write text after
        if (fileSpec.consumerTextAfter() != null) {
            writeString(fileSpec.consumerTextAfter());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }
    }

    private void writeLinePrefix() throws IOException {
        if (fileSpec.linePrefix() != null) {
            writeString(fileSpec.linePrefix());
        }
    }

}
