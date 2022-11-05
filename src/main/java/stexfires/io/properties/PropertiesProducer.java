package stexfires.io.properties;

import stexfires.io.internal.AbstractReadableProducer;
import stexfires.io.internal.AbstractRecordRawDataIterator;
import stexfires.io.internal.RecordRawData;
import stexfires.record.KeyValueRecord;
import stexfires.record.impl.KeyValueFieldsRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static stexfires.io.properties.PropertiesFileSpec.ESCAPE_CHAR;
import static stexfires.io.properties.PropertiesFileSpec.UNICODE_ENCODE_LENGTH;
import static stexfires.io.properties.PropertiesFileSpec.UNICODE_ENCODE_RADIX;
// TODO chars and codePoints

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("HardcodedLineSeparator")
public final class PropertiesProducer extends AbstractReadableProducer<KeyValueRecord> {

    private static final int ILLEGAL_INDEX = -1;

    private final PropertiesFileSpec fileSpec;

    public PropertiesProducer(BufferedReader reader, PropertiesFileSpec fileSpec) {
        super(reader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        return new PropertiesIterator(getReader());
    }

    @Override
    protected Optional<KeyValueRecord> createRecord(RecordRawData recordRawData) {
        String[] keyValue = splitLine(recordRawData.rawData());

        return createRecord(
                fileSpec.isCommentAsCategory() ? recordRawData.category() : null,
                recordRawData.recordId(),
                decode(keyValue[0]),
                decode(keyValue[1]),
                fileSpec.getValueSpec().readNullReplacement());
    }

    private static String[] splitLine(String line) {
        Objects.requireNonNull(line);

        final int maxIndex = line.length();

        boolean escapeFound = false;
        boolean delimiterFound = false;
        int keyStartIndex = ILLEGAL_INDEX;
        int keyEndIndex = ILLEGAL_INDEX;
        int valueStartIndex = ILLEGAL_INDEX;
        char character;

        for (int i = 0; i < maxIndex; i++) {
            character = line.charAt(i);

            if ((keyStartIndex == ILLEGAL_INDEX)
                    && (character != ' ' && character != '\t' && character != '\f')) {
                // Find start of key
                keyStartIndex = i;
            }
            if (keyEndIndex == ILLEGAL_INDEX) {
                // Find end of key
                if (!escapeFound && (keyStartIndex != ILLEGAL_INDEX)
                        && (character == '=' || character == ':'
                        || character == ' ' || character == '\t' || character == '\f')) {
                    keyEndIndex = i;
                    if (character == '=' || character == ':') {
                        delimiterFound = true;
                    }
                }
                escapeFound = (character == '\\') && !escapeFound;
            } else {
                // Find start of value
                if (character != ' ' && character != '\t' && character != '\f') {
                    if (!delimiterFound && (character == '=' || character == ':')) {
                        delimiterFound = true;
                    } else {
                        valueStartIndex = i;
                        break;
                    }
                }
            }
        }

        String key = null;
        if (keyStartIndex != ILLEGAL_INDEX) {
            if (keyEndIndex == ILLEGAL_INDEX) {
                keyEndIndex = maxIndex;
            }
            key = line.substring(keyStartIndex, keyEndIndex);
        }

        String value = null;
        if (valueStartIndex != ILLEGAL_INDEX) {
            value = line.substring(valueStartIndex);
        }

        return new String[]{
                key,
                value
        };
    }

    @SuppressWarnings("NumericCastThatLosesPrecision")
    private static String decode(String encodedStr) {
        if ((encodedStr == null) || (encodedStr.isEmpty())) {
            return encodedStr;
        }

        StringBuilder b = new StringBuilder(encodedStr.length());

        boolean escapeFound = false;
        int unicodeStartIndex = ILLEGAL_INDEX;

        for (int i = 0; i < encodedStr.length(); i++) {
            char character = encodedStr.charAt(i);

            if (unicodeStartIndex != ILLEGAL_INDEX) {
                if (unicodeStartIndex + UNICODE_ENCODE_LENGTH - 1 == i) {
                    // TODO Exception handling
                    b.append((char) Integer.parseInt(
                            encodedStr.substring(unicodeStartIndex, unicodeStartIndex + UNICODE_ENCODE_LENGTH),
                            UNICODE_ENCODE_RADIX));
                    unicodeStartIndex = ILLEGAL_INDEX;
                }
            } else if (escapeFound) {
                switch (character) {
                    case 'f' -> b.append('\f');
                    case 'n' -> b.append('\n');
                    case 'r' -> b.append('\r');
                    case 't' -> b.append('\t');
                    case 'u' -> unicodeStartIndex = i + 1;
                    default -> b.append(character);
                }
                escapeFound = false;
            } else if (character == ESCAPE_CHAR) {
                escapeFound = true;
            } else {
                b.append(character);
            }
        }

        if (escapeFound || (unicodeStartIndex != ILLEGAL_INDEX)) {
            // TODO Exception handling
            throw new RuntimeException(encodedStr);
        }

        return b.toString();
    }

    private static Optional<KeyValueRecord> createRecord(String category, Long recordId,
                                                         String key, String value, String nullValueReplacement) {
        KeyValueRecord record = null;

        if (key != null) {
            if (value != null) {
                record = new KeyValueFieldsRecord(category, recordId, key, value);
            } else {
                record = new KeyValueFieldsRecord(category, recordId, key, nullValueReplacement);
            }
        }

        return Optional.ofNullable(record);
    }

    private static final class PropertiesIterator extends AbstractRecordRawDataIterator {

        private PropertiesIterator(BufferedReader bufferedReader) {
            super(bufferedReader);
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws IOException {
            StringBuilder b = new StringBuilder();
            String currentLine;
            String currentComment = null;
            boolean commentFound;
            boolean escapeFound;
            boolean multiLine = false;
            int keyStartIndex;
            char character;

            do {
                currentLine = reader.readLine();

                // reset
                commentFound = false;
                escapeFound = false;
                keyStartIndex = ILLEGAL_INDEX;

                if (currentLine != null) {
                    for (int i = 0; i < currentLine.length(); i++) {
                        character = currentLine.charAt(i);

                        if (!multiLine && (keyStartIndex == ILLEGAL_INDEX)
                                && (character == '#' || character == '!')) {
                            commentFound = true;
                            currentComment = currentLine.substring(i + 1);
                            break;
                        }

                        if ((keyStartIndex == ILLEGAL_INDEX)
                                && (character != ' ' && character != '\t' && character != '\f')) {
                            keyStartIndex = i;
                        }

                        escapeFound = (character == ESCAPE_CHAR) && !escapeFound;
                    }
                    if (!commentFound && (keyStartIndex != ILLEGAL_INDEX)) {
                        if (escapeFound) {
                            b.append(currentLine, keyStartIndex, currentLine.length() - 1);
                        } else {
                            b.append(currentLine, keyStartIndex, currentLine.length());
                        }
                    }
                }
                multiLine = escapeFound;
            } while (commentFound || multiLine);

            if ((currentLine == null) && (b.isEmpty())) {
                return Optional.empty();
            }

            return RecordRawData.asOptional(currentComment, recordIndex, b.toString());
        }

    }

}
