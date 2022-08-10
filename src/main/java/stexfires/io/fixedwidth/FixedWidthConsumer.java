package stexfires.io.fixedwidth;

import stexfires.core.Field;
import stexfires.core.TextRecord;
import stexfires.core.consumer.ConsumerException;
import stexfires.core.consumer.UncheckedConsumerException;
import stexfires.io.internal.AbstractWritableConsumer;
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
public class FixedWidthConsumer extends AbstractWritableConsumer<TextRecord> {

    protected final FixedWidthFileSpec fileSpec;

    public FixedWidthConsumer(BufferedWriter writer, FixedWidthFileSpec fileSpec) {
        super(writer);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    protected static String createRecordString(int recordWidth,
                                               Character fillCharacter,
                                               Alignment alignment,
                                               List<FixedWidthFieldSpec> fieldSpecs,
                                               List<Field> fields) {
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
            int fieldWidth = Math.min(fieldSpec.getWidth(), recordWidth - fieldSpec.getStartIndex());
            if ((fieldWidth <= 0) || (fieldSpec.getStartIndex() < 0)) {
                // field can be skipped
                continue;
            }

            Field field = (fields.size() > fieldIndex) ? fields.get(fieldIndex) : null;
            String value = (field != null) ? field.value() : null;
            int valueWidth = (value != null) ? value.length() : 0;

            // Insert field fill character
            if ((valueWidth < fieldWidth)
                    && (fieldSpec.getFillCharacter() != null)) {
                Arrays.fill(characters, fieldSpec.getStartIndex(),
                        fieldSpec.getStartIndex() + fieldWidth, fieldSpec.getFillCharacter());
            }

            // Insert field value
            if (valueWidth > 0) {
                fillCharacters(characters, fieldSpec.getStartIndex(), fieldWidth, valueWidth, value,
                        (fieldSpec.getAlignment() != null) ? fieldSpec.getAlignment() : alignment);
            }
        }
        return String.valueOf(characters);
    }

    protected static void fillCharacters(char[] characters, int startIndex, int fieldWidth,
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
    public void writeRecord(TextRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        writeString(createRecordString(
                fileSpec.getRecordWidth(),
                fileSpec.getFillCharacter(),
                fileSpec.getAlignment(),
                fileSpec.getFieldSpecs(),
                record.listOfFields()));

        if (fileSpec.isSeparateRecordsByLineSeparator()) {
            writeLineSeparator(fileSpec.getLineSeparator());
        }
    }

}
