package stexfires.io.delimited.simple;

import stexfires.io.internal.AbstractWritableConsumer;
import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class SimpleDelimitedConsumer extends AbstractWritableConsumer<TextRecord> {

    private final SimpleDelimitedFileSpec fileSpec;

    public SimpleDelimitedConsumer(BufferedWriter bufferedWriter, SimpleDelimitedFileSpec fileSpec) {
        super(bufferedWriter);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    private static String createRecordString(String fieldDelimiter,
                                             List<SimpleDelimitedFieldSpec> fieldSpecs,
                                             List<TextField> fields) {
        Objects.requireNonNull(fieldDelimiter);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(fields);

        StringBuilder b = new StringBuilder();

        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            SimpleDelimitedFieldSpec fieldSpec = fieldSpecs.get(fieldIndex);

            if (fieldIndex > 0) {
                b.append(fieldDelimiter);
            }

            TextField field = (fields.size() > fieldIndex) ? fields.get(fieldIndex) : null;
            String text = (field != null) ? field.text() : null;

            if (text != null) {
                b.append(text);
            }
        }

        return b.toString();
    }

    @Override
    public void writeBefore() throws IOException {
        super.writeBefore();

        if (fileSpec.textBefore() != null) {
            writeString(fileSpec.textBefore());
            writeLineSeparator(fileSpec.lineSeparator());
        }
    }

    @Override
    public void writeRecord(TextRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        writeString(createRecordString(
                fileSpec.fieldDelimiter(),
                fileSpec.fieldSpecs(),
                record.listOfFields()));
        writeLineSeparator(fileSpec.lineSeparator());
    }

    @Override
    public void writeAfter() throws IOException {
        super.writeAfter();

        if (fileSpec.textAfter() != null) {
            writeString(fileSpec.textAfter());
            writeLineSeparator(fileSpec.lineSeparator());
        }
    }

}
