package stexfires.io.properties;

import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableWritableRecordFileSpec;
import stexfires.record.KeyValueRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.util.LineSeparator;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class PropertiesFileSpec extends ReadableWritableRecordFileSpec<KeyValueRecord, KeyValueRecord> {

    // DEFAULT - PropertiesFieldSpec
    public static final String DEFAULT_READ_NULL_VALUE_REPLACEMENT = "";
    public static final String DEFAULT_WRITE_NULL_VALUE_REPLACEMENT = "";

    // DEFAULT - read
    public static final boolean DEFAULT_COMMENT_AS_CATEGORY = false;

    // DEFAULT - write
    public static final boolean DEFAULT_ESCAPE_UNICODE = false;
    public static final boolean DEFAULT_DATE_COMMENT = false;
    public static final boolean DEFAULT_CATEGORY_AS_KEY_PREFIX = false;
    public static final String DEFAULT_KEY_PREFIX_DELIMITER = ".";

    // FIELD -  both
    private final List<PropertiesFieldSpec> fieldSpecs;

    // FIELD - read
    private final boolean commentAsCategory;

    // FIELD - write
    private final boolean escapeUnicode;
    private final boolean dateComment;
    private final boolean categoryAsKeyPrefix;
    private final String keyPrefixDelimiter;

    public PropertiesFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                              @Nullable String decoderReplacement, @Nullable String encoderReplacement,
                              @Nullable String readNullValueReplacement,
                              boolean commentAsCategory,
                              LineSeparator lineSeparator,
                              String writeNullValueReplacement,
                              boolean escapeUnicode, boolean dateComment,
                              boolean categoryAsKeyPrefix, String keyPrefixDelimiter) {
        super(charset, codingErrorAction, decoderReplacement, encoderReplacement, lineSeparator);
        Objects.requireNonNull(writeNullValueReplacement);
        Objects.requireNonNull(keyPrefixDelimiter);

        // both
        fieldSpecs = new ArrayList<>(2);
        fieldSpecs.add(KeyValueFieldsRecord.KEY_INDEX,
                new PropertiesFieldSpec(DEFAULT_READ_NULL_VALUE_REPLACEMENT, DEFAULT_WRITE_NULL_VALUE_REPLACEMENT));
        fieldSpecs.add(KeyValueFieldsRecord.VALUE_INDEX,
                new PropertiesFieldSpec(readNullValueReplacement, writeNullValueReplacement));

        // read
        this.commentAsCategory = commentAsCategory;

        // write
        this.escapeUnicode = escapeUnicode;
        this.dateComment = dateComment;
        this.categoryAsKeyPrefix = categoryAsKeyPrefix;
        this.keyPrefixDelimiter = keyPrefixDelimiter;
    }

    public static PropertiesFileSpec read(Charset charset, CodingErrorAction codingErrorAction,
                                          @Nullable String decoderReplacement) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                decoderReplacement, null,
                DEFAULT_READ_NULL_VALUE_REPLACEMENT,
                DEFAULT_COMMENT_AS_CATEGORY,
                DEFAULT_LINE_SEPARATOR,
                DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                DEFAULT_ESCAPE_UNICODE, DEFAULT_DATE_COMMENT,
                DEFAULT_CATEGORY_AS_KEY_PREFIX, DEFAULT_KEY_PREFIX_DELIMITER);
    }

    public static PropertiesFileSpec read(Charset charset, CodingErrorAction codingErrorAction,
                                          @Nullable String decoderReplacement,
                                          String nullValueReplacement) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                decoderReplacement, null,
                nullValueReplacement,
                DEFAULT_COMMENT_AS_CATEGORY,
                DEFAULT_LINE_SEPARATOR,
                DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                DEFAULT_ESCAPE_UNICODE, DEFAULT_DATE_COMMENT,
                DEFAULT_CATEGORY_AS_KEY_PREFIX, DEFAULT_KEY_PREFIX_DELIMITER);
    }

    public static PropertiesFileSpec read(Charset charset, CodingErrorAction codingErrorAction,
                                          @Nullable String decoderReplacement,
                                          String nullValueReplacement,
                                          boolean commentAsCategory) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                decoderReplacement, null,
                nullValueReplacement,
                commentAsCategory,
                DEFAULT_LINE_SEPARATOR,
                DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                DEFAULT_ESCAPE_UNICODE, DEFAULT_DATE_COMMENT,
                DEFAULT_CATEGORY_AS_KEY_PREFIX, DEFAULT_KEY_PREFIX_DELIMITER);
    }

    public static PropertiesFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                           @Nullable String encoderReplacement,
                                           LineSeparator lineSeparator,
                                           boolean escapeUnicode) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                null, encoderReplacement,
                DEFAULT_READ_NULL_VALUE_REPLACEMENT,
                DEFAULT_COMMENT_AS_CATEGORY,
                lineSeparator,
                DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                escapeUnicode, DEFAULT_DATE_COMMENT,
                DEFAULT_CATEGORY_AS_KEY_PREFIX, DEFAULT_KEY_PREFIX_DELIMITER);
    }

    public static PropertiesFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                           @Nullable String encoderReplacement,
                                           LineSeparator lineSeparator,
                                           String nullValueReplacement,
                                           boolean escapeUnicode, boolean dateComment,
                                           boolean categoryAsKeyPrefix, String keyPrefixDelimiter) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                null, encoderReplacement,
                DEFAULT_READ_NULL_VALUE_REPLACEMENT,
                DEFAULT_COMMENT_AS_CATEGORY,
                lineSeparator,
                nullValueReplacement,
                escapeUnicode, dateComment,
                categoryAsKeyPrefix, keyPrefixDelimiter);
    }

    @Override
    public PropertiesProducer producer(InputStream inputStream) {
        return new PropertiesProducer(newBufferedReader(inputStream), this);
    }

    @Override
    public PropertiesConsumer consumer(OutputStream outputStream) {
        return new PropertiesConsumer(newBufferedWriter(outputStream), this);
    }

    public List<PropertiesFieldSpec> getFieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

    public PropertiesFieldSpec getValueSpec() {
        return fieldSpecs.get(KeyValueFieldsRecord.VALUE_INDEX);
    }

    public boolean isCommentAsCategory() {
        return commentAsCategory;
    }

    public boolean isEscapeUnicode() {
        return escapeUnicode;
    }

    public boolean isDateComment() {
        return dateComment;
    }

    public boolean isCategoryAsKeyPrefix() {
        return categoryAsKeyPrefix;
    }

    public String getKeyPrefixDelimiter() {
        return keyPrefixDelimiter;
    }

}
