package stexfires.io.properties;

import org.jspecify.annotations.Nullable;
import stexfires.io.internal.AbstractInternalReadableProducer;
import stexfires.io.producer.AbstractRecordRawDataIterator;
import stexfires.io.producer.RecordRawData;
import stexfires.record.KeyValueRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static stexfires.io.properties.PropertiesFileSpec.*;

/**
 * @since 0.1
 */
public final class PropertiesProducer extends AbstractInternalReadableProducer<KeyValueRecord> {

    private static final int ILLEGAL_INDEX = -1;
    private final PropertiesFileSpec fileSpec;

    public PropertiesProducer(BufferedReader bufferedReader, PropertiesFileSpec fileSpec) {
        super(bufferedReader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    private static KeyValue splitLineIntoKeyValue(String line) {
        Objects.requireNonNull(line);

        int maxIndex = line.length();

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

        return new KeyValue(key, value);
    }

    @SuppressWarnings("HardcodedLineSeparator")
    private static @Nullable String decode(@Nullable String encodedStr) throws UncheckedProducerException {
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
                    String unicodeSequence = encodedStr.substring(unicodeStartIndex, unicodeStartIndex + UNICODE_ENCODE_LENGTH);
                    try {
                        b.appendCodePoint(Integer.parseInt(unicodeSequence, UNICODE_ENCODE_RADIX));
                    } catch (NumberFormatException e) {
                        throw new UncheckedProducerException(new ProducerException("Invalid unicode sequence found! " + unicodeSequence, e));
                    }
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

        if (escapeFound) {
            throw new UncheckedProducerException(new ProducerException("Unhandled or unfinished escape sequence found!"));
        }
        if (unicodeStartIndex != ILLEGAL_INDEX) {
            throw new UncheckedProducerException(new ProducerException("Unhandled or unfinished unicode sequence found! " + unicodeStartIndex));
        }

        return b.toString();
    }

    private static Optional<KeyValueRecord> createKeyValueRecord(@Nullable String category, @Nullable Long recordId,
                                                                 @Nullable String key, @Nullable String value,
                                                                 @Nullable String nullValueReplacement) {
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

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        return new PropertiesIterator(bufferedReader());
    }

    @Override
    protected Optional<KeyValueRecord> createRecord(RecordRawData recordRawData) throws UncheckedProducerException {
        String category = fileSpec.producerCommentAsCategory() ? recordRawData.category() : null;
        KeyValue keyValue = splitLineIntoKeyValue(recordRawData.rawData());

        return createKeyValueRecord(
                category,
                recordRawData.recordId(),
                decode(keyValue.key()),
                decode(keyValue.value()),
                fileSpec.producerNullValueReplacement());
    }

    /**
     * A simple record to hold a key and a value.
     *
     * @param key   the key, can be {@code null}
     * @param value the value, can be {@code null}
     */
    private record KeyValue(@Nullable String key, @Nullable String value) {
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
                            // the comment starts after the comment character and extends to the end of the line
                            currentComment = currentLine.substring(i + 1);
                            break;
                        }

                        // ignore leading whitespace (characters space, tab and form feed)
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

            return RecordRawData.buildOptionalRecordRawData(currentComment, recordIndex, b.toString());
        }

    }

}
