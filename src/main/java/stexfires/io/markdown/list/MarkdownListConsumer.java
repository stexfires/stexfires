package stexfires.io.markdown.list;

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
public class MarkdownListConsumer extends AbstractWritableConsumer<ValueRecord> {

    protected final MarkdownListFileSpec fileSpec;

    protected long currentNumber;

    public MarkdownListConsumer(BufferedWriter writer, MarkdownListFileSpec fileSpec) {
        super(writer);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public void writeBefore() throws IOException {
        super.writeBefore();

        if (fileSpec.getBeforeList() != null) {
            writeString(fileSpec.getBeforeList());
            writeLineSeparator(fileSpec.getLineSeparator());
        }

        // Init currentNumber
        currentNumber = MarkdownListFileSpec.START_NUMBER;
    }

    @Override
    public void writeRecord(ValueRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        String value = record.valueOfValueField();
        if (value != null || !fileSpec.isSkipNullValue()) {
            switch (fileSpec.getBulletPoint()) {
                case NUMBER -> {
                    writeString(String.valueOf(currentNumber));
                    writeString(MarkdownListFileSpec.BULLET_POINT_NUMBER);
                    currentNumber++;
                }
                case STAR -> writeString(MarkdownListFileSpec.BULLET_POINT_STAR);
                case DASH -> writeString(MarkdownListFileSpec.BULLET_POINT_DASH);
            }
            writeString(MarkdownListFileSpec.FILL_CHARACTER);
            if (value != null) {
                writeString(value);
            }
            writeLineSeparator(fileSpec.getLineSeparator());
        }
    }

    @Override
    public void writeAfter() throws IOException {
        super.writeAfter();

        if (fileSpec.getAfterList() != null) {
            writeString(fileSpec.getAfterList());
            writeLineSeparator(fileSpec.getLineSeparator());
        }
    }

}
