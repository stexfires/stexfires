package stexfires.io.properties;

import stexfires.io.internal.AbstractWritableConsumer;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.impl.KeyValueRecord;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class PropertiesConsumer extends AbstractWritableConsumer<KeyValueRecord> {

    public static final String DELIMITER = "=";
    public static final String COMMENT_PREFIX = "#";
    public static final int FIRST_NON_ESCAPED_CHAR = 0x0020;
    public static final int LAST_NON_ESCAPED_CHAR = 0x007e;

    protected final PropertiesFileSpec fileSpec;

    public PropertiesConsumer(BufferedWriter writer, PropertiesFileSpec fileSpec) {
        super(writer);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @SuppressWarnings("HardcodedLineSeparator")
    protected static String mapCharacter(char character, boolean escapeSpace, boolean escapeUnicode) {
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

    protected static String convertKey(String key, boolean escapeUnicode) {
        return IntStream.range(0, key.length())
                        .mapToObj(i -> mapCharacter(key.charAt(i), true, escapeUnicode))
                        .collect(Collectors.joining());
    }

    protected static String convertValue(String value, boolean escapeUnicode) {
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
            writeLineSeparator(fileSpec.getLineSeparator());
        }
    }

    @Override
    public void writeRecord(KeyValueRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        String key;
        if (fileSpec.isCategoryAsKeyPrefix() && record.hasCategory()) {
            key = record.category() + fileSpec.getKeyPrefixDelimiter() + record.valueOfKeyField();
        } else {
            key = record.valueOfKeyField();
        }
        writeString(convertKey(key, fileSpec.isEscapeUnicode()));
        writeString(DELIMITER);
        writeString(convertValue(record.valueField().valueOrElse(fileSpec.getValueSpec().getWriteNullReplacement()),
                fileSpec.isEscapeUnicode()));
        writeLineSeparator(fileSpec.getLineSeparator());
    }

}

