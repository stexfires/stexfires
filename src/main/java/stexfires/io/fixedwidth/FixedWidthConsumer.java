package stexfires.io.fixedwidth;

import stexfires.io.internal.AbstractWritableConsumer;
import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.util.Alignment;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class FixedWidthConsumer extends AbstractWritableConsumer<TextRecord> {

    private final FixedWidthFileSpec fileSpec;

    public FixedWidthConsumer(BufferedWriter bufferedWriter, FixedWidthFileSpec fileSpec) {
        super(bufferedWriter);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    private static String createRecordString(int recordWidth,
                                             Character fillCharacter,
                                             Alignment alignment,
                                             List<FixedWidthFieldSpec> fieldSpecs,
                                             List<TextField> fields) {
        Objects.requireNonNull(fillCharacter);
        Objects.requireNonNull(alignment);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(fields);

        // Create and fill character array
        char[] characters = new char[recordWidth];
        Arrays.fill(characters, fillCharacter);

        // Insert field values and fill characters
        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            FixedWidthFieldSpec fieldSpec = fieldSpecs.get(fieldIndex);
            int fieldWidth = Math.min(fieldSpec.width(), recordWidth - fieldSpec.startIndex());
            if ((fieldWidth <= 0) || (fieldSpec.startIndex() < 0)) {
                // field can be skipped
                continue;
            }

            TextField field = (fields.size() > fieldIndex) ? fields.get(fieldIndex) : null;
            String text = (field != null) ? field.text() : null;
            int textWidth = (text != null) ? text.length() : 0;

            // Insert field fill character
            if ((textWidth < fieldWidth)
                    && (fieldSpec.fillCharacter() != null)) {
                Arrays.fill(characters, fieldSpec.startIndex(),
                        fieldSpec.startIndex() + fieldWidth, fieldSpec.fillCharacter());
            }

            // Insert field value
            if (textWidth > 0) {
                fillCharacters(characters, fieldSpec.startIndex(), fieldWidth, textWidth, text,
                        (fieldSpec.alignment() != null) ? fieldSpec.alignment() : alignment);
            }
        }
        return String.valueOf(characters);
    }

    private static void fillCharacters(char[] characters, int startIndex, int fieldWidth,
                                       int valueWidth, String value, Alignment alignment) {
        // Calculate width and start positions
        int width = Math.min(valueWidth, fieldWidth);
        int startPosValue;
        int startPosChars;
        switch (alignment) {
            case START -> {
                startPosValue = 0;
                startPosChars = startIndex;
            }
            case CENTER -> {
                startPosValue = ((valueWidth - width) / 2);
                startPosChars = ((fieldWidth - width) / 2) + startIndex;
            }
            case END -> {
                startPosValue = (valueWidth - width);
                startPosChars = (fieldWidth - width) + startIndex;
            }
            default -> throw new IllegalArgumentException("alignment = " + alignment);
        }

        // fill character array with value
        for (int charIndex = 0; charIndex < width; charIndex++) {
            characters[startPosChars + charIndex] = value.charAt(startPosValue + charIndex);
        }
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
                fileSpec.recordWidth(),
                fileSpec.fillCharacter(),
                fileSpec.alignment(),
                fileSpec.fieldSpecs(),
                record.listOfFields()));

        if (fileSpec.separateRecordsByLineSeparator()) {
            writeLineSeparator(fileSpec.lineSeparator());
        }
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
