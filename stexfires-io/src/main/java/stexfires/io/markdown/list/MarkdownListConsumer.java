package stexfires.io.markdown.list;

import stexfires.io.internal.AbstractInternalWritableConsumer;
import stexfires.record.TextField;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;

import static stexfires.io.markdown.list.MarkdownListFileSpec.ORDERED_LIST_START_NUMBER;

/**
 * @since 0.1
 */
public final class MarkdownListConsumer extends AbstractInternalWritableConsumer<ValueRecord> {

    private final MarkdownListFileSpec fileSpec;

    private long currentNumber;

    public MarkdownListConsumer(BufferedWriter bufferedWriter, MarkdownListFileSpec fileSpec) {
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

        // init
        currentNumber = ORDERED_LIST_START_NUMBER;
    }

    @Override
    public void writeRecord(ValueRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        TextField valueField = record.valueField();

        if (!fileSpec.consumerSkipNullValue() || valueField.isNotNull()) {
            writeLinePrefix();
            if (valueField.isNotNull()) {
                writeString(valueField.text());
            }
            writeLineSeparator(fileSpec.consumerLineSeparator());
            currentNumber++;
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
        writeString(fileSpec.consumerListMarker().linePrefix(currentNumber));
    }

}
