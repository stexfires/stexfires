package stexfires.io.properties;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableRecordFileSpec;
import stexfires.io.WritableRecordFileSpec;
import stexfires.record.KeyValueRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record PropertiesFileSpec(
        @NotNull CharsetCoding charsetCoding,
        @NotNull LineSeparator lineSeparator,
        @Nullable String readNullValueReplacement,
        boolean commentAsCategory,
        @NotNull String writeNullValueReplacement,
        boolean escapeUnicode,
        boolean dateComment,
        boolean categoryAsKeyPrefix,
        @NotNull String keyPrefixDelimiter
) implements ReadableRecordFileSpec<KeyValueRecord>, WritableRecordFileSpec<KeyValueRecord, PropertiesConsumer> {

    public static final String DELIMITER = "=";
    public static final String COMMENT_PREFIX = "#";
    public static final int FIRST_NON_ESCAPED_CHAR = 0x0020;
    public static final int LAST_NON_ESCAPED_CHAR = 0x007e;
    public static final char ESCAPE_CHAR = '\\';
    public static final int UNICODE_ENCODE_LENGTH = 4;
    public static final int UNICODE_ENCODE_RADIX = 16;
    public static final String DEFAULT_READ_NULL_VALUE_REPLACEMENT = "";
    public static final boolean DEFAULT_COMMENT_AS_CATEGORY = false;
    public static final String DEFAULT_WRITE_NULL_VALUE_REPLACEMENT = "";
    public static final boolean DEFAULT_ESCAPE_UNICODE = false;
    public static final boolean DEFAULT_DATE_COMMENT = false;
    public static final boolean DEFAULT_CATEGORY_AS_KEY_PREFIX = false;
    public static final String DEFAULT_KEY_PREFIX_DELIMITER = ".";

    public PropertiesFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(lineSeparator);
        Objects.requireNonNull(writeNullValueReplacement);
        Objects.requireNonNull(keyPrefixDelimiter);
    }

    public static PropertiesFileSpec read(CharsetCoding charsetCoding,
                                          @Nullable String readNullValueReplacement,
                                          boolean commentAsCategory) {
        return new PropertiesFileSpec(
                charsetCoding,
                DEFAULT_LINE_SEPARATOR,
                readNullValueReplacement,
                commentAsCategory,
                DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                DEFAULT_ESCAPE_UNICODE,
                DEFAULT_DATE_COMMENT,
                DEFAULT_CATEGORY_AS_KEY_PREFIX,
                DEFAULT_KEY_PREFIX_DELIMITER);
    }

    public static PropertiesFileSpec write(CharsetCoding charsetCoding,
                                           LineSeparator lineSeparator,
                                           String writeNullValueReplacement,
                                           boolean escapeUnicode,
                                           boolean dateComment,
                                           boolean categoryAsKeyPrefix,
                                           String keyPrefixDelimiter) {
        return new PropertiesFileSpec(
                charsetCoding,
                lineSeparator,
                DEFAULT_READ_NULL_VALUE_REPLACEMENT,
                DEFAULT_COMMENT_AS_CATEGORY,
                writeNullValueReplacement,
                escapeUnicode,
                dateComment,
                categoryAsKeyPrefix,
                keyPrefixDelimiter);
    }

    @Override
    public PropertiesProducer producer(BufferedReader bufferedReader) {
        Objects.requireNonNull(bufferedReader);
        return new PropertiesProducer(bufferedReader, this);
    }

    @Override
    public PropertiesProducer producer(InputStream inputStream) {
        Objects.requireNonNull(inputStream);
        return producer(charsetCoding().newBufferedReader(inputStream));
    }

    @Override
    public PropertiesConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new PropertiesConsumer(bufferedWriter, this);
    }

    @Override
    public @Nullable String textBefore() {
        return WritableRecordFileSpec.DEFAULT_TEXT_BEFORE;
    }

    @Override
    public @Nullable String textAfter() {
        return WritableRecordFileSpec.DEFAULT_TEXT_AFTER;
    }

}
