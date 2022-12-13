package stexfires.io.markdown.list;

import stexfires.io.internal.AbstractWritableConsumer;
import stexfires.record.TextField;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;

import static stexfires.io.markdown.list.MarkdownListFileSpec.BULLET_POINT_START_NUMBER;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class MarkdownListConsumer extends AbstractWritableConsumer<ValueRecord> {

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

        if (fileSpec.consumerTextBefore() != null) {
            writeString(fileSpec.consumerTextBefore());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }

        // Init currentNumber
        currentNumber = BULLET_POINT_START_NUMBER;
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
            currentNumber++;
        }
    }

    private void writeLinePrefix() throws IOException {
        writeString(fileSpec.bulletPoint().linePrefix(currentNumber));
    }

    @Override
    public void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeAfter();

        if (fileSpec.consumerTextAfter() != null) {
            writeString(fileSpec.consumerTextAfter());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }
    }

}
