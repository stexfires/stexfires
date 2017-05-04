package stexfires.io.properties;

import stexfires.core.record.KeyValueRecord;
import stexfires.io.spec.AbstractRecordFileSpec;
import stexfires.util.LineSeparator;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class PropertiesFileSpec extends AbstractRecordFileSpec {

    public static final String DEFAULT_READ_NULL_VALUE_REPLACEMENT = "";
    public static final String DEFAULT_WRITE_NULL_VALUE_REPLACEMENT = "";
    public static final boolean DEFAULT_ESCAPE_UNICODE = false;
    public static final boolean DEFAULT_CATEGORY_AS_KEY_PREFIX = false;
    public static final String DEFAULT_KEY_PREFIX_DELIMITER = ".";
    public static final boolean DEFAULT_DATE_COMMENT = false;

    // both
    private final List<PropertiesFieldSpec> fieldSpecs;

    // read
    private final boolean escapeUnicode;
    private final boolean categoryAsKeyPrefix;
    private final String keyPrefixDelimiter;
    private final boolean dateComment;

    public PropertiesFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                              String readNullValueReplacement,
                              LineSeparator lineSeparator,
                              String writeNullValueReplacement,
                              boolean escapeUnicode, boolean categoryAsKeyPrefix,
                              String keyPrefixDelimiter, boolean dateComment) {
        super(charset, codingErrorAction, lineSeparator);
        Objects.requireNonNull(writeNullValueReplacement);
        Objects.requireNonNull(keyPrefixDelimiter);

        // both
        fieldSpecs = new ArrayList<>(2);
        fieldSpecs.add(KeyValueRecord.KEY_INDEX,
                new PropertiesFieldSpec());
        fieldSpecs.add(KeyValueRecord.VALUE_INDEX,
                new PropertiesFieldSpec(readNullValueReplacement, writeNullValueReplacement));

        // write
        this.escapeUnicode = escapeUnicode;
        this.categoryAsKeyPrefix = categoryAsKeyPrefix;
        this.keyPrefixDelimiter = keyPrefixDelimiter;
        this.dateComment = dateComment;
    }

    public static PropertiesFileSpec read(Charset charset, CodingErrorAction codingErrorAction) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                DEFAULT_READ_NULL_VALUE_REPLACEMENT,
                DEFAULT_LINE_SEPARATOR,
                DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                DEFAULT_ESCAPE_UNICODE, DEFAULT_CATEGORY_AS_KEY_PREFIX,
                DEFAULT_KEY_PREFIX_DELIMITER, DEFAULT_DATE_COMMENT);
    }

    public static PropertiesFileSpec read(Charset charset, CodingErrorAction codingErrorAction,
                                          String nullValueReplacement) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                nullValueReplacement,
                DEFAULT_LINE_SEPARATOR,
                DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                DEFAULT_ESCAPE_UNICODE, DEFAULT_CATEGORY_AS_KEY_PREFIX,
                DEFAULT_KEY_PREFIX_DELIMITER, DEFAULT_DATE_COMMENT);
    }

    public static PropertiesFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                           LineSeparator lineSeparator,
                                           boolean escapeUnicode) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                DEFAULT_READ_NULL_VALUE_REPLACEMENT,
                lineSeparator,
                DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                escapeUnicode, DEFAULT_CATEGORY_AS_KEY_PREFIX,
                DEFAULT_KEY_PREFIX_DELIMITER, DEFAULT_DATE_COMMENT);
    }

    public static PropertiesFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                           LineSeparator lineSeparator,
                                           String nullValueReplacement,
                                           boolean escapeUnicode, boolean categoryAsKeyPrefix,
                                           String keyPrefixDelimiter, boolean dateComment) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                DEFAULT_READ_NULL_VALUE_REPLACEMENT,
                lineSeparator,
                nullValueReplacement,
                escapeUnicode, categoryAsKeyPrefix,
                keyPrefixDelimiter, dateComment);
    }

    public PropertiesFile file(Path path) {
        return new PropertiesFile(path, this);
    }

    public PropertiesProducer producer(InputStream inputStream) {
        return new PropertiesProducer(newBufferedReader(inputStream), this);
    }

    public PropertiesConsumer consumer(OutputStream outputStream) {
        return new PropertiesConsumer(newBufferedWriter(outputStream), this);
    }

    public List<PropertiesFieldSpec> getFieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

    public PropertiesFieldSpec getValueSpec() {
        return fieldSpecs.get(KeyValueRecord.VALUE_INDEX);
    }

    public boolean isEscapeUnicode() {
        return escapeUnicode;
    }

    public boolean isCategoryAsKeyPrefix() {
        return categoryAsKeyPrefix;
    }

    public String getKeyPrefixDelimiter() {
        return keyPrefixDelimiter;
    }

    public boolean isDateComment() {
        return dateComment;
    }

}
