package stexfires.io.singlevalue;

import stexfires.util.LineSeparator;

import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class SingleValueFileSpec {

    public static final CodingErrorAction DEFAULT_CODING_ERROR_ACTION = CodingErrorAction.REPORT;

    public static final boolean DEFAULT_SKIP_EMPTY_LINES = false;
    public static final int DEFAULT_IGNORE_FIRST = 0;
    public static final int DEFAULT_IGNORE_LAST = 0;

    public static final LineSeparator DEFAULT_LINE_SEPARATOR = LineSeparator.LF;
    public static final boolean DEFAULT_SKIP_NULL_VALUE = false;

    private final Charset charset;
    private final CodingErrorAction codingErrorAction;

    private final int ignoreFirst;
    private final int ignoreLast;
    private final boolean skipEmptyLines;

    private final LineSeparator lineSeparator;
    private final boolean skipNullValue;

    public SingleValueFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                               boolean skipEmptyLines, int ignoreFirst, int ignoreLast,
                               LineSeparator lineSeparator, boolean skipNullValue) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(codingErrorAction);
        Objects.requireNonNull(lineSeparator);
        if (ignoreFirst < 0) {
            throw new IllegalArgumentException("ignoreFirst < 0");
        }
        if (ignoreLast < 0) {
            throw new IllegalArgumentException("ignoreLast < 0");
        }

        this.charset = charset;
        this.codingErrorAction = codingErrorAction;

        // read
        this.skipEmptyLines = skipEmptyLines;
        this.ignoreFirst = ignoreFirst;
        this.ignoreLast = ignoreLast;

        // write
        this.lineSeparator = lineSeparator;
        this.skipNullValue = skipNullValue;
    }

    public static SingleValueFileSpec read(Charset charset) {
        return new SingleValueFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                DEFAULT_SKIP_EMPTY_LINES, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST,
                DEFAULT_LINE_SEPARATOR, DEFAULT_SKIP_NULL_VALUE);
    }

    public static SingleValueFileSpec read(Charset charset,
                                           boolean skipEmptyLines, int ignoreFirstLines, int ignoreLastLines) {
        return new SingleValueFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                skipEmptyLines, ignoreFirstLines, ignoreLastLines,
                DEFAULT_LINE_SEPARATOR, DEFAULT_SKIP_NULL_VALUE);
    }

    public static SingleValueFileSpec read(Charset charset, CodingErrorAction codingErrorAction,
                                           boolean skipEmptyLines, int ignoreFirstLines, int ignoreLastLines) {
        return new SingleValueFileSpec(charset, codingErrorAction,
                skipEmptyLines, ignoreFirstLines, ignoreLastLines,
                DEFAULT_LINE_SEPARATOR, DEFAULT_SKIP_NULL_VALUE);
    }

    public static SingleValueFileSpec write(Charset charset,
                                            LineSeparator lineSeparator) {
        return new SingleValueFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                DEFAULT_SKIP_EMPTY_LINES, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST,
                lineSeparator, DEFAULT_SKIP_NULL_VALUE);
    }

    public static SingleValueFileSpec write(Charset charset,
                                            LineSeparator lineSeparator, boolean skipNullValue) {
        return new SingleValueFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                DEFAULT_SKIP_EMPTY_LINES, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST,
                lineSeparator, skipNullValue);
    }

    public static SingleValueFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                            LineSeparator lineSeparator, boolean skipNullValue) {
        return new SingleValueFileSpec(charset, codingErrorAction,
                DEFAULT_SKIP_EMPTY_LINES, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST,
                lineSeparator, skipNullValue);
    }

    public SingleValueFile file(Path path) {
        return new SingleValueFile(path, this);
    }

    public Charset getCharset() {
        return charset;
    }

    public CodingErrorAction getCodingErrorAction() {
        return codingErrorAction;
    }

    public boolean isSkipEmptyLines() {
        return skipEmptyLines;
    }

    public int getIgnoreFirst() {
        return ignoreFirst;
    }

    public int getIgnoreLast() {
        return ignoreLast;
    }

    public LineSeparator getLineSeparator() {
        return lineSeparator;
    }

    public boolean isSkipNullValue() {
        return skipNullValue;
    }

}
