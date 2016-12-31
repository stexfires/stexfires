package stexfires.io.properties;

import stexfires.core.consumer.ConsumerException;
import stexfires.core.record.KeyValueRecord;
import stexfires.io.internal.AbstractWritableConsumer;

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
    public static final String NULL_VALUE = "";
    public static final int FIRST_NON_ESCAPED_CHAR = 0x0020;
    public static final int LAST_NON_ESCAPED_CHAR = 0x007e;

    protected final PropertiesFileSpec fileSpec;

    public PropertiesConsumer(BufferedWriter writer, PropertiesFileSpec fileSpec) {
        super(writer);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

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

    @Override
    public void writeBefore() throws IOException {
        super.writeBefore();
        if (fileSpec.isDateComment()) {
            write(COMMENT_PREFIX);
            write(new Date().toString());
            write(fileSpec.getLineSeparator().string());
        }
    }

    @Override
    public void writeRecord(KeyValueRecord record) throws IOException, ConsumerException {
        super.writeRecord(record);
        String key;
        if (fileSpec.isCategoryAsKeyPrefix() && record.hasCategory()) {
            key = record.getCategory() + fileSpec.getKeyPrefixDelimiter() + record.getValueOfKeyField();
        } else {
            key = record.getValueOfKeyField();
        }
        write(convertKey(key, fileSpec.isEscapeUnicode()));
        write(DELIMITER);
        write(convertValue(record.getValueField().getValueOrElse(NULL_VALUE), fileSpec.isEscapeUnicode()));
        write(fileSpec.getLineSeparator().string());
    }

}

