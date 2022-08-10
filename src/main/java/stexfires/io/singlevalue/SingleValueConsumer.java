package stexfires.io.singlevalue;

import stexfires.core.consumer.ConsumerException;
import stexfires.core.consumer.UncheckedConsumerException;
import stexfires.core.record.ValueRecord;
import stexfires.io.internal.AbstractWritableConsumer;

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

        String value = record.valueOfValueField();
        if (value != null) {
            writeString(value);
            writeLineSeparator(fileSpec.getLineSeparator());
        } else if (!fileSpec.isSkipNullValue()) {
            writeLineSeparator(fileSpec.getLineSeparator());
        }
    }

}
