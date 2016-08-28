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

    private final Charset charset;

    private final LineSeparator lineSeparator;
    private final boolean escapeUnicode;
    private final boolean dateComment;
    private final CodingErrorAction unmappableCharacterAction;

    public PropertiesFileSpec(Charset charset, LineSeparator lineSeparator, boolean escapeUnicode) {
        this(charset, lineSeparator, escapeUnicode, false, CodingErrorAction.REPLACE);
    }

    public PropertiesFileSpec(Charset charset, LineSeparator lineSeparator, boolean escapeUnicode, boolean dateComment, CodingErrorAction unmappableCharacterAction) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(lineSeparator);
        this.charset = charset;
        this.lineSeparator = lineSeparator;
        this.escapeUnicode = escapeUnicode;
        this.dateComment = dateComment;
        this.unmappableCharacterAction = unmappableCharacterAction;
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

    public CodingErrorAction getUnmappableCharacterAction() {
        return unmappableCharacterAction;
    }

}
