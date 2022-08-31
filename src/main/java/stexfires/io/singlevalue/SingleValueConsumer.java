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
public class SingleValueConsumer extends AbstractWritableConsumer<ValueRecord> {

    protected final SingleValueFileSpec fileSpec;

    public SingleValueConsumer(BufferedWriter writer, SingleValueFileSpec fileSpec) {
        super(writer);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public void writeRecord(ValueRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        String value = record.value();
        if (value != null) {
            writeString(value);
            writeLineSeparator(fileSpec.getLineSeparator());
        } else if (!fileSpec.isSkipNullValue()) {
            writeLineSeparator(fileSpec.getLineSeparator());
        }
    }

}
