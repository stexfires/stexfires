package stexfires.io.properties;

import stexfires.io.internal.AbstractWritableConsumer;
import stexfires.record.KeyValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static stexfires.io.properties.PropertiesFileSpec.COMMENT_PREFIX;
import static stexfires.io.properties.PropertiesFileSpec.DELIMITER;
import static stexfires.io.properties.PropertiesFileSpec.FIRST_NON_ESCAPED_CHAR;
import static stexfires.io.properties.PropertiesFileSpec.LAST_NON_ESCAPED_CHAR;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class PropertiesConsumer extends AbstractWritableConsumer<KeyValueRecord> {

    private final PropertiesFileSpec fileSpec;

    public PropertiesConsumer(BufferedWriter writer, PropertiesFileSpec fileSpec) {
        super(writer);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @SuppressWarnings("HardcodedLineSeparator")
    private static String mapCharacter(char character, boolean escapeSpace, boolean escapeUnicode) {
        switch (character) {
            case ' ':
                return escapeSpace ? "\\ " : " ";
            case '\t':
                return "\\t";
            case '\n':
                return "\\n";
            case '\r':
                return "\\r";
            case '\f':
                return "\\f";
            case '\\':
                return "\\\\";
            case '=':
                return "\\=";
            case ':':
                return "\\:";
            case '#':
                return "\\#";
            case '!':
                return "\\!";
            default:
                if (escapeUnicode
                        && ((character < FIRST_NON_ESCAPED_CHAR) || (character > LAST_NON_ESCAPED_CHAR))) {
                    return "\\u" + String.format("%04X", (int) character);
                }
                return Character.toString(character);
        }
    }

    private static String convertKey(String key, boolean escapeUnicode) {
        return IntStream.range(0, key.length())
                        .mapToObj(i -> mapCharacter(key.charAt(i), true, escapeUnicode))
                        .collect(Collectors.joining());
    }

    private static String convertValue(String value, boolean escapeUnicode) {
        return IntStream.range(0, value.length())
                        .mapToObj(i -> mapCharacter(value.charAt(i), i == 0, escapeUnicode))
                        .collect(Collectors.joining());
    }

    @SuppressWarnings("UseOfObsoleteDateTimeApi")
    @Override
    public void writeBefore() throws IOException {
        super.writeBefore();

        if (fileSpec.isDateComment()) {
            writeString(COMMENT_PREFIX);
            writeString(new Date().toString());
            writeLineSeparator(fileSpec.lineSeparator());
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void writeRecord(KeyValueRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        String key;
        if (fileSpec.isCategoryAsKeyPrefix() && record.hasCategory()) {
            key = record.category() + fileSpec.getKeyPrefixDelimiter() + record.key();
        } else {
            key = record.key();
        }
        writeString(convertKey(key, fileSpec.isEscapeUnicode()));
        writeString(DELIMITER);
        writeString(convertValue(record.valueField().orElse(fileSpec.getValueSpec().writeNullReplacement()),
                fileSpec.isEscapeUnicode()));
        writeLineSeparator(fileSpec.lineSeparator());
    }

}

