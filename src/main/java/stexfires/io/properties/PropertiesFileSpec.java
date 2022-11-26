package stexfires.io.properties;

import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableWritableRecordFileSpec;
import stexfires.record.KeyValueRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class PropertiesFileSpec extends ReadableWritableRecordFileSpec<KeyValueRecord, KeyValueRecord> {

    public static final String DELIMITER = "=";
    public static final String COMMENT_PREFIX = "#";
    public static final int FIRST_NON_ESCAPED_CHAR = 0x0020;
    public static final int LAST_NON_ESCAPED_CHAR = 0x007e;
    public static final char ESCAPE_CHAR = '\\';
    public static final int UNICODE_ENCODE_LENGTH = 4;
    public static final int UNICODE_ENCODE_RADIX = 16;

    // DEFAULT - read
    public static final String DEFAULT_READ_NULL_VALUE_REPLACEMENT = "";
    public static final boolean DEFAULT_COMMENT_AS_CATEGORY = false;

    // DEFAULT - write
    public static final String DEFAULT_WRITE_NULL_VALUE_REPLACEMENT = "";
    public static final boolean DEFAULT_ESCAPE_UNICODE = false;
    public static final boolean DEFAULT_DATE_COMMENT = false;
    public static final boolean DEFAULT_CATEGORY_AS_KEY_PREFIX = false;
    public static final String DEFAULT_KEY_PREFIX_DELIMITER = ".";

    // FIELD - read
    private final String readNullValueReplacement;
    private final boolean commentAsCategory;

    // FIELD - write
    private final String writeNullValueReplacement;
    private final boolean escapeUnicode;
    private final boolean dateComment;
    private final boolean categoryAsKeyPrefix;
    private final String keyPrefixDelimiter;

    public PropertiesFileSpec(CharsetCoding charsetCoding,
                              LineSeparator lineSeparator,
                              @Nullable String readNullValueReplacement,
                              boolean commentAsCategory,
                              String writeNullValueReplacement,
                              boolean escapeUnicode,
                              boolean dateComment,
                              boolean categoryAsKeyPrefix,
                              String keyPrefixDelimiter) {
        super(charsetCoding,
                lineSeparator,
                null,
                null);
        Objects.requireNonNull(writeNullValueReplacement);
        Objects.requireNonNull(keyPrefixDelimiter);

        // read
        this.readNullValueReplacement = readNullValueReplacement;
        this.commentAsCategory = commentAsCategory;

        // write
        this.writeNullValueReplacement = writeNullValueReplacement;
        this.escapeUnicode = escapeUnicode;
        this.dateComment = dateComment;
        this.categoryAsKeyPrefix = categoryAsKeyPrefix;
        this.keyPrefixDelimiter = keyPrefixDelimiter;
    }

    public static PropertiesFileSpec read(CharsetCoding charsetCoding,
                                          @Nullable String readNullValueReplacement,
                                          boolean commentAsCategory) {
        return new PropertiesFileSpec(charsetCoding,
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
        return new PropertiesFileSpec(charsetCoding,
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
    public PropertiesConsumer consumer(OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        return consumer(charsetCoding().newBufferedWriter(outputStream));
    }

    public @Nullable String readNullValueReplacement() {
        return readNullValueReplacement;
    }

    public boolean commentAsCategory() {
        return commentAsCategory;
    }

    public String writeNullValueReplacement() {
        return writeNullValueReplacement;
    }

    public boolean escapeUnicode() {
        return escapeUnicode;
    }

    public boolean dateComment() {
        return dateComment;
    }

    public boolean categoryAsKeyPrefix() {
        return categoryAsKeyPrefix;
    }

    public String keyPrefixDelimiter() {
        return keyPrefixDelimiter;
    }

}
