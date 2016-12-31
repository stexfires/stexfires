package stexfires.io.properties;

import stexfires.util.LineSeparator;

import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class PropertiesFileSpec {

    public static final CodingErrorAction DEFAULT_CODING_ERROR_ACTION = CodingErrorAction.REPORT;
    public static final boolean DEFAULT_CATEGORY_AS_KEY_PREFIX = false;
    public static final String DEFAULT_KEY_PREFIX_DELIMITER = ".";
    public static final boolean DEFAULT_DATE_COMMENT = false;

    private final Charset charset;
    private final CodingErrorAction codingErrorAction;

    private final boolean escapeUnicode;
    private final boolean categoryAsKeyPrefix;
    private final String keyPrefixDelimiter;
    private final boolean dateComment;
    private final LineSeparator lineSeparator;

    public PropertiesFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                              boolean escapeUnicode, boolean categoryAsKeyPrefix, String keyPrefixDelimiter,
                              boolean dateComment, LineSeparator lineSeparator) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(codingErrorAction);
        Objects.requireNonNull(keyPrefixDelimiter);
        Objects.requireNonNull(lineSeparator);
        this.charset = charset;
        this.codingErrorAction = codingErrorAction;
        this.escapeUnicode = escapeUnicode;
        this.categoryAsKeyPrefix = categoryAsKeyPrefix;
        this.keyPrefixDelimiter = keyPrefixDelimiter;
        this.dateComment = dateComment;
        this.lineSeparator = lineSeparator;
    }

    public static PropertiesFileSpec write(Charset charset,
                                           boolean escapeUnicode,
                                           LineSeparator lineSeparator) {
        return new PropertiesFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                escapeUnicode, DEFAULT_CATEGORY_AS_KEY_PREFIX, DEFAULT_KEY_PREFIX_DELIMITER,
                DEFAULT_DATE_COMMENT, lineSeparator);
    }

    public static PropertiesFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                           boolean escapeUnicode, boolean categoryAsKeyPrefix, String keyPrefixDelimiter,
                                           boolean dateComment, LineSeparator lineSeparator) {
        return new PropertiesFileSpec(charset, codingErrorAction,
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
