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

    private static final String DELIMITER = "=";
    private static final String COMMENT_PREFIX = "#";

    private final PropertiesFileSpec fileSpec;

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
                if (((character < 0x0020) || (character > 0x007e)) & escapeUnicode) {
                    return "\\u" + String.format("%04X", (int) character);
                }
                return Character.toString(character);
        }
    }

    @Override
    public void writeBefore() throws IOException {
        super.writeBefore();
        if (fileSpec.isDateComment()) {
            write(COMMENT_PREFIX);
            write(new Date().toString());
            write(fileSpec.getLineSeparator().getSeparator());
        }
    }

    @Override
    public void writeRecord(KeyValueRecord record) throws IOException, ConsumerException {
        super.writeRecord(record);
        write(convertKey(record.getValueOfKeyField()));
        write(DELIMITER);
        write(convertValue(record.getValueOfValueField()));
        write(fileSpec.getLineSeparator().getSeparator());
    }

    private String convertKey(String key) {
        return IntStream.range(0, key.length())
                        .mapToObj(i -> mapCharacter(key.charAt(i), true, fileSpec.isEscapeUnicode()))
                        .collect(Collectors.joining());
    }

    private String convertValue(String value) {
        return IntStream.range(0, value.length())
                        .mapToObj(i -> mapCharacter(value.charAt(i), i == 0, fileSpec.isEscapeUnicode()))
                        .collect(Collectors.joining());
    }

}

