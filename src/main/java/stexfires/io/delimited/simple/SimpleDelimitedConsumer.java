package stexfires.io.delimited.simple;

import stexfires.core.Field;
import stexfires.core.Record;
import stexfires.core.consumer.ConsumerException;
import stexfires.io.internal.AbstractWritableConsumer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SimpleDelimitedConsumer extends AbstractWritableConsumer<Record> {

    protected final SimpleDelimitedFileSpec fileSpec;

    public SimpleDelimitedConsumer(BufferedWriter writer, SimpleDelimitedFileSpec fileSpec) {
        super(writer);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    protected static String createRecordString(String fieldDelimiter,
                                               List<SimpleDelimitedFieldSpec> fieldSpecs,
                                               List<Field> fields) {
        Objects.requireNonNull(fieldDelimiter);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(fields);

        StringBuilder b = new StringBuilder();

        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            SimpleDelimitedFieldSpec fieldSpec = fieldSpecs.get(fieldIndex);

            if (fieldIndex > 0) {
                b.append(fieldDelimiter);
            }

            Field field = (fields.size() > fieldIndex) ? fields.get(fieldIndex) : null;
            String value = (field != null) ? field.getValue() : null;

            if (value != null) {
                b.append(value);
            }
        }

        return b.toString();
    }

    @Override
    public void writeRecord(Record record) throws IOException, ConsumerException {
        super.writeRecord(record);

        write(createRecordString(
                fileSpec.getFieldDelimiter(),
                fileSpec.getFieldSpecs(),
                record.listOfFields()));

        write(fileSpec.getLineSeparator().string());
    }

}
