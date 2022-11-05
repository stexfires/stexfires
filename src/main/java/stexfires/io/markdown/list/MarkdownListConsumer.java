package stexfires.io.markdown.list;

import stexfires.io.internal.AbstractWritableConsumer;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;

import static stexfires.io.markdown.list.MarkdownListFileSpec.BULLET_POINT_DASH;
import static stexfires.io.markdown.list.MarkdownListFileSpec.BULLET_POINT_NUMBER;
import static stexfires.io.markdown.list.MarkdownListFileSpec.BULLET_POINT_STAR;
import static stexfires.io.markdown.list.MarkdownListFileSpec.FILL_CHARACTER;
import static stexfires.io.markdown.list.MarkdownListFileSpec.START_NUMBER;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class MarkdownListConsumer extends AbstractWritableConsumer<ValueRecord> {

    private final MarkdownListFileSpec fileSpec;

    private long currentNumber;

    public MarkdownListConsumer(BufferedWriter writer, MarkdownListFileSpec fileSpec) {
        super(writer);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public void writeBefore() throws IOException {
        super.writeBefore();

        if (fileSpec.getWriteBefore() != null) {
            writeString(fileSpec.getWriteBefore());
            writeLineSeparator(fileSpec.lineSeparator());
        }

        // Init currentNumber
        currentNumber = START_NUMBER;
    }

    @Override
    public void writeRecord(ValueRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        String value = record.value();
        if (value != null || !fileSpec.isSkipNullValue()) {
            switch (fileSpec.getBulletPoint()) {
                case NUMBER -> {
                    writeString(String.valueOf(currentNumber));
                    writeString(BULLET_POINT_NUMBER);
                    currentNumber++;
                }
                case STAR -> writeString(BULLET_POINT_STAR);
                case DASH -> writeString(BULLET_POINT_DASH);
            }
            writeString(FILL_CHARACTER);
            if (value != null) {
                writeString(value);
            }
            writeLineSeparator(fileSpec.lineSeparator());
        }
    }

    @Override
    public void writeAfter() throws IOException {
        super.writeAfter();

        if (fileSpec.getWriteAfter() != null) {
            writeString(fileSpec.getWriteAfter());
            writeLineSeparator(fileSpec.lineSeparator());
        }
    }

}
