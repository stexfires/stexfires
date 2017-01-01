package stexfires.io.properties;

import stexfires.core.record.KeyValueRecord;
import stexfires.util.LineSeparator;

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
public final class PropertiesFileSpec {

    public static final CodingErrorAction DEFAULT_CODING_ERROR_ACTION = CodingErrorAction.REPORT;
    public static final String DEFAULT_READ_NULL_VALUE_REPLACEMENT = "";
    public static final String DEFAULT_WRITE_NULL_VALUE_REPLACEMENT = "";
    public static final boolean DEFAULT_ESCAPE_UNICODE = false;
    public static final boolean DEFAULT_CATEGORY_AS_KEY_PREFIX = false;
    public static final String DEFAULT_KEY_PREFIX_DELIMITER = ".";
    public static final boolean DEFAULT_DATE_COMMENT = false;
    public static final LineSeparator DEFAULT_LINE_SEPARATOR = LineSeparator.LF;

    private final Charset charset;
    private final CodingErrorAction codingErrorAction;
    private final List<PropertiesFieldSpec> fieldSpecs;

    private final boolean escapeUnicode;
    private final boolean categoryAsKeyPrefix;
    private final String keyPrefixDelimiter;
    private final boolean dateComment;
    private final LineSeparator lineSeparator;

    public PropertiesFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                              String readNullValueReplacement, String writeNullValueReplacement,
                              boolean escapeUnicode, boolean categoryAsKeyPrefix, String keyPrefixDelimiter,
                              boolean dateComment, LineSeparator lineSeparator) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(codingErrorAction);
        Objects.requireNonNull(writeNullValueReplacement);
        Objects.requireNonNull(keyPrefixDelimiter);
        Objects.requireNonNull(lineSeparator);
        this.charset = charset;
        this.codingErrorAction = codingErrorAction;
        this.escapeUnicode = escapeUnicode;
        this.categoryAsKeyPrefix = categoryAsKeyPrefix;
        this.keyPrefixDelimiter = keyPrefixDelimiter;
        this.dateComment = dateComment;
        this.lineSeparator = lineSeparator;

        fieldSpecs = new ArrayList<>(2);
        fieldSpecs.add(KeyValueRecord.KEY_INDEX,
                new PropertiesFieldSpec());
        fieldSpecs.add(KeyValueRecord.VALUE_INDEX,
                new PropertiesFieldSpec(readNullValueReplacement, writeNullValueReplacement));
    }

    public static PropertiesFileSpec read(Charset charset, CodingErrorAction codingErrorAction) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                DEFAULT_READ_NULL_VALUE_REPLACEMENT, DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                DEFAULT_ESCAPE_UNICODE, DEFAULT_CATEGORY_AS_KEY_PREFIX, DEFAULT_KEY_PREFIX_DELIMITER,
                DEFAULT_DATE_COMMENT, DEFAULT_LINE_SEPARATOR);
    }

    public static PropertiesFileSpec read(Charset charset, CodingErrorAction codingErrorAction,
                                          String nullValueReplacement) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                nullValueReplacement, DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                DEFAULT_ESCAPE_UNICODE, DEFAULT_CATEGORY_AS_KEY_PREFIX, DEFAULT_KEY_PREFIX_DELIMITER,
                DEFAULT_DATE_COMMENT, DEFAULT_LINE_SEPARATOR);
    }

    public static PropertiesFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                           boolean escapeUnicode,
                                           LineSeparator lineSeparator) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                DEFAULT_READ_NULL_VALUE_REPLACEMENT, DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                escapeUnicode, DEFAULT_CATEGORY_AS_KEY_PREFIX, DEFAULT_KEY_PREFIX_DELIMITER,
                DEFAULT_DATE_COMMENT, lineSeparator);
    }

    public static PropertiesFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                           String nullValueReplacement,
                                           boolean escapeUnicode, boolean categoryAsKeyPrefix, String keyPrefixDelimiter,
                                           boolean dateComment, LineSeparator lineSeparator) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                DEFAULT_READ_NULL_VALUE_REPLACEMENT, nullValueReplacement,
                escapeUnicode, categoryAsKeyPrefix, keyPrefixDelimiter,
                dateComment, lineSeparator);
    }

    public PropertiesFile file(Path path) {
        return new PropertiesFile(path, this);
    }

    public Charset getCharset() {
        return charset;
    }

    public CodingErrorAction getCodingErrorAction() {
        return codingErrorAction;
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

    public LineSeparator getLineSeparator() {
        return lineSeparator;
    }

}
