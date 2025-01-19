package stexfires.io.properties;

import stexfires.io.internal.AbstractInternalWritableConsumer;
import stexfires.record.KeyValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.*;

import static stexfires.io.properties.PropertiesFileSpec.*;

/**
 * @since 0.1
 */
public final class PropertiesConsumer extends AbstractInternalWritableConsumer<KeyValueRecord> {

    private final PropertiesFileSpec fileSpec;

    public PropertiesConsumer(BufferedWriter bufferedWriter, PropertiesFileSpec fileSpec) {
        super(bufferedWriter);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @SuppressWarnings({"HardcodedLineSeparator", "EnhancedSwitchMigration"})
    static String mapCharacter(char character, boolean escapeSpace, boolean escapeUnicode) {
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

    static String convertKey(String key, boolean escapeUnicode) {
        Objects.requireNonNull(key);
        return IntStream.range(0, key.length())
                        .mapToObj(i -> mapCharacter(key.charAt(i), true, escapeUnicode))
                        .collect(Collectors.joining());
    }

    static String convertValue(String value, boolean escapeUnicode) {
        Objects.requireNonNull(value);
        return IntStream.range(0, value.length())
                        .mapToObj(i -> mapCharacter(value.charAt(i), i == 0, escapeUnicode))
                        .collect(Collectors.joining());
    }

    @SuppressWarnings("UseOfObsoleteDateTimeApi")
    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeBefore();

        // write comment
        if (fileSpec.consumerDateComment()) {
            writeString(COMMENT_PREFIX);
            writeString(new Date().toString());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }
    }

    @Override
    public void writeRecord(KeyValueRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        String key;
        if (fileSpec.consumerCategoryAsKeyPrefix() && record.hasCategory()) {
            key = record.category() + fileSpec.consumerKeyPrefixDelimiter() + record.key();
        } else {
            key = record.key();
        }
        writeString(convertKey(key, fileSpec.consumerEscapeUnicode()));
        writeString(DELIMITER);
        writeString(convertValue(record.valueField().orElse(fileSpec.consumerNullValueReplacement()),
                fileSpec.consumerEscapeUnicode()));
        writeLineSeparator(fileSpec.consumerLineSeparator());
    }

}
