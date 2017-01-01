package stexfires.io.markdown.list;

import stexfires.core.consumer.ConsumerException;
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
            write(fileSpec.getBeforeList());
            write(fileSpec.getLineSeparator());
        }
        currentNumber = 1;
    }

    @Override
    public void writeRecord(ValueRecord record) throws IOException, ConsumerException {
        super.writeRecord(record);

        String value = record.getValueOfValueField();
        if (value != null || !fileSpec.isSkipNullValue()) {
            switch (fileSpec.getBulletPoint()) {
                case NUMBER:
                    write(String.valueOf(currentNumber));
                    write(MarkdownListFileSpec.BULLET_POINT_NUMBER);
                    currentNumber++;
                    break;
                case STAR:
                    write(MarkdownListFileSpec.BULLET_POINT_STAR);
                    break;
                case DASH:
                    write(MarkdownListFileSpec.BULLET_POINT_DASH);
                    break;
            }
            write(" ");
            if (value != null) {
                write(value);
            }
            write(fileSpec.getLineSeparator());
        }
    }

    @Override
    public void writeAfter() throws IOException {
        super.writeAfter();
        if (fileSpec.getAfterList() != null) {
            write(fileSpec.getAfterList());
            write(fileSpec.getLineSeparator());
        }
    }

}
