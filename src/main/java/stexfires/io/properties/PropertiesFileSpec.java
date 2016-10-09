package stexfires.io.properties;

import stexfires.util.LineSeparator;

import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class PropertiesFileSpec {

    public static final String DELIMITER = "=";
    public static final String COMMENT_PREFIX = "#";
    public static final String NULL_VALUE = "";

    private final Charset charset;

    private final LineSeparator lineSeparator;
    private final boolean escapeUnicode;
    private final boolean dateComment;
    private final CodingErrorAction codingErrorAction;

    public PropertiesFileSpec(Charset charset, LineSeparator lineSeparator, boolean escapeUnicode) {
        this(charset, lineSeparator, escapeUnicode, false, CodingErrorAction.REPLACE);
    }

    public PropertiesFileSpec(Charset charset, LineSeparator lineSeparator, boolean escapeUnicode, boolean dateComment, CodingErrorAction codingErrorAction) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(lineSeparator);
        this.charset = charset;
        this.lineSeparator = lineSeparator;
        this.escapeUnicode = escapeUnicode;
        this.dateComment = dateComment;
        this.codingErrorAction = codingErrorAction;
    }

    public Charset getCharset() {
        return charset;
    }

    public LineSeparator getLineSeparator() {
        return lineSeparator;
    }

    public boolean isEscapeUnicode() {
        return escapeUnicode;
    }

    public boolean isDateComment() {
        return dateComment;
    }

    public CodingErrorAction getCodingErrorAction() {
        return codingErrorAction;
    }

}
