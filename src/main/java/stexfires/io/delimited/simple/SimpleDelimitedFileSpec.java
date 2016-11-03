package stexfires.io.delimited.simple;

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
public final class SimpleDelimitedFileSpec {

    public static final CodingErrorAction DEFAULT_CODING_ERROR_ACTION = CodingErrorAction.REPORT;

    public static final boolean DEFAULT_SKIP_EMPTY_LINES = false;
    public static final boolean DEFAULT_SKIP_ALL_NULL = false;
    public static final int DEFAULT_IGNORE_FIRST = 0;
    public static final int DEFAULT_IGNORE_LAST = 0;

    public static final LineSeparator DEFAULT_LINE_SEPARATOR = LineSeparator.LF;

    private final Charset charset;
    private final CodingErrorAction codingErrorAction;
    private final String fieldDelimiter;
    private final List<SimpleDelimitedFieldSpec> fieldSpecs;

    private final int ignoreFirst;
    private final int ignoreLast;
    private final boolean skipEmptyLines;
    private final boolean skipAllNull;

    private final LineSeparator lineSeparator;

    public SimpleDelimitedFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                                   String fieldDelimiter,
                                   List<SimpleDelimitedFieldSpec> fieldSpecs,
                                   int ignoreFirst, int ignoreLast, boolean skipEmptyLines, boolean skipAllNull,
                                   LineSeparator lineSeparator) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(codingErrorAction);
        Objects.requireNonNull(fieldDelimiter);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(lineSeparator);
        if (ignoreFirst < 0) {
            throw new IllegalArgumentException("ignoreFirst < 0");
        }
        if (ignoreLast < 0) {
            throw new IllegalArgumentException("ignoreLast < 0");
        }

        this.charset = charset;
        this.codingErrorAction = codingErrorAction;
        this.fieldDelimiter = fieldDelimiter;
        this.fieldSpecs = new ArrayList(fieldSpecs);

        this.ignoreFirst = ignoreFirst;
        this.ignoreLast = ignoreLast;
        this.skipEmptyLines = skipEmptyLines;
        this.skipAllNull = skipAllNull;

        this.lineSeparator = lineSeparator;
    }

    public static SimpleDelimitedFileSpec read(Charset charset,
                                               String fieldDelimiter,
                                               List<SimpleDelimitedFieldSpec> fieldSpecs,
                                               int ignoreFirst, int ignoreLast, boolean skipEmptyLines, boolean skipAllNull) {
        return new SimpleDelimitedFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                fieldDelimiter,
                fieldSpecs,
                ignoreFirst, ignoreLast, skipEmptyLines, skipAllNull,
                DEFAULT_LINE_SEPARATOR);
    }

    public static SimpleDelimitedFileSpec read(Charset charset, CodingErrorAction codingErrorAction,
                                               String fieldDelimiter,
                                               List<SimpleDelimitedFieldSpec> fieldSpecs,
                                               int ignoreFirst, int ignoreLast, boolean skipEmptyLines, boolean skipAllNull) {
        return new SimpleDelimitedFileSpec(charset, codingErrorAction,
                fieldDelimiter,
                fieldSpecs,
                ignoreFirst, ignoreLast, skipEmptyLines, skipAllNull,
                DEFAULT_LINE_SEPARATOR);
    }

    public static SimpleDelimitedFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                                String fieldDelimiter,
                                                List<SimpleDelimitedFieldSpec> fieldSpecs,
                                                LineSeparator lineSeparator) {
        return new SimpleDelimitedFileSpec(charset, codingErrorAction,
                fieldDelimiter,
                fieldSpecs,
                DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST, DEFAULT_SKIP_EMPTY_LINES, DEFAULT_SKIP_ALL_NULL,
                lineSeparator);
    }

    public SimpleDelimitedFile file(Path path) {
        return new SimpleDelimitedFile(path, this);
    }

    public Charset getCharset() {
        return charset;
    }

    public CodingErrorAction getCodingErrorAction() {
        return codingErrorAction;
    }

    public String getFieldDelimiter() {
        return fieldDelimiter;
    }

    public List<SimpleDelimitedFieldSpec> getFieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

    public int getIgnoreFirst() {
        return ignoreFirst;
    }

    public int getIgnoreLast() {
        return ignoreLast;
    }

    public boolean isSkipEmptyLines() {
        return skipEmptyLines;
    }

    public boolean isSkipAllNull() {
        return skipAllNull;
    }

    public LineSeparator getLineSeparator() {
        return lineSeparator;
    }

}
