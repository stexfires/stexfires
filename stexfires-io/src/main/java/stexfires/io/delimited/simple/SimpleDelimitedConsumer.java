package stexfires.io.delimited.simple;

import stexfires.io.internal.AbstractInternalWritableConsumer;
import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @since 0.1
 */
public final class SimpleDelimitedConsumer extends AbstractInternalWritableConsumer<TextRecord> {

    private final SimpleDelimitedFileSpec fileSpec;

    public SimpleDelimitedConsumer(BufferedWriter bufferedWriter, SimpleDelimitedFileSpec fileSpec) {
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
    public void writeRecord(TextRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        writeString(createRecordString(
                fileSpec.fieldDelimiter(),
                fileSpec.fieldSpecs(),
                record.listOfFields()));
        writeLineSeparator(fileSpec.consumerLineSeparator());
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

}
