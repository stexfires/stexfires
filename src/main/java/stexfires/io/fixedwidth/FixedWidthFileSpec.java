package stexfires.io.fixedwidth;

import stexfires.util.Alignment;
import stexfires.util.LineSeparator;

import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class FixedWidthFileSpec {

    public static final CodingErrorAction DEFAULT_CODING_ERROR_ACTION = CodingErrorAction.REPORT;

    public static final boolean DEFAULT_SKIP_EMPTY_LINES = false;
    public static final boolean DEFAULT_SKIP_ALL_NULL_OR_EMPTY = false;
    public static final int DEFAULT_IGNORE_FIRST = 0;
    public static final int DEFAULT_IGNORE_LAST = 0;

    public static final LineSeparator DEFAULT_LINE_SEPARATOR = LineSeparator.LF;

    private final Charset charset;
    private final CodingErrorAction codingErrorAction;
    private final int recordWidth;
    private final boolean separateRecordsByLineSeparator;
    private final Alignment alignment;
    private final Character fillCharacter;
    private final List<FixedWidthFieldSpec> fieldSpecs;

    private final int ignoreFirst;
    private final int ignoreLast;
    private final boolean skipEmptyLines;
    private final boolean skipAllNullOrEmpty;

    private final LineSeparator lineSeparator;

    public FixedWidthFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                              int recordWidth, boolean separateRecordsByLineSeparator,
                              Alignment alignment, Character fillCharacter,
                              List<FixedWidthFieldSpec> fieldSpecs,
                              int ignoreFirst, int ignoreLast, boolean skipEmptyLines, boolean skipAllNullOrEmpty,
                              LineSeparator lineSeparator) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(codingErrorAction);
        Objects.requireNonNull(alignment);
        Objects.requireNonNull(fillCharacter);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(lineSeparator);
        if (recordWidth < 0) {
            throw new IllegalArgumentException("recordWidth < 0");
        }
        if (ignoreFirst < 0) {
            throw new IllegalArgumentException("ignoreFirst < 0");
        }
        if (ignoreLast < 0) {
            throw new IllegalArgumentException("ignoreLast < 0");
        }

        this.charset = charset;
        this.codingErrorAction = codingErrorAction;
        this.recordWidth = recordWidth;
        this.separateRecordsByLineSeparator = separateRecordsByLineSeparator;
        this.alignment = alignment;
        this.fillCharacter = fillCharacter;
        this.fieldSpecs = fieldSpecs;

        this.ignoreFirst = ignoreFirst;
        this.ignoreLast = ignoreLast;
        this.skipEmptyLines = skipEmptyLines;
        this.skipAllNullOrEmpty = skipAllNullOrEmpty;

        this.lineSeparator = lineSeparator;
    }

    public static FixedWidthFileSpec read(Charset charset,
                                          int recordWidth, boolean separateRecordsByLineSeparator,
                                          Alignment alignment, Character fillCharacter,
                                          List<FixedWidthFieldSpec> fieldSpecs,
                                          int ignoreFirst, int ignoreLast, boolean skipEmptyLines, boolean skipAllNullOrEmpty) {
        return new FixedWidthFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                recordWidth, separateRecordsByLineSeparator,
                alignment, fillCharacter,
                fieldSpecs,
                ignoreFirst, ignoreLast, skipEmptyLines, skipAllNullOrEmpty,
                DEFAULT_LINE_SEPARATOR);
    }

    public static FixedWidthFileSpec read(Charset charset, CodingErrorAction codingErrorAction,
                                          int recordWidth, boolean separateRecordsByLineSeparator,
                                          Alignment alignment, Character fillCharacter,
                                          List<FixedWidthFieldSpec> fieldSpecs,
                                          int ignoreFirst, int ignoreLast, boolean skipEmptyLines, boolean skipAllNullOrEmpty) {
        return new FixedWidthFileSpec(charset, codingErrorAction,
                recordWidth, separateRecordsByLineSeparator,
                alignment, fillCharacter,
                fieldSpecs,
                ignoreFirst, ignoreLast, skipEmptyLines, skipAllNullOrEmpty,
                DEFAULT_LINE_SEPARATOR);
    }

    public static FixedWidthFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                           int recordWidth, boolean separateRecordsByLineSeparator,
                                           Alignment alignment, Character fillCharacter,
                                           List<FixedWidthFieldSpec> fieldSpecs,
                                           LineSeparator lineSeparator) {
        return new FixedWidthFileSpec(charset, codingErrorAction,
                recordWidth, separateRecordsByLineSeparator,
                alignment, fillCharacter,
                fieldSpecs,
                DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST, DEFAULT_SKIP_EMPTY_LINES, DEFAULT_SKIP_ALL_NULL_OR_EMPTY,
                lineSeparator);
    }

    public FixedWidthFile file(Path path) {
        return new FixedWidthFile(path, this);
    }

    public Charset getCharset() {
        return charset;
    }

    public CodingErrorAction getCodingErrorAction() {
        return codingErrorAction;
    }

    public int getRecordWidth() {
        return recordWidth;
    }

    public boolean isSeparateRecordsByLineSeparator() {
        return separateRecordsByLineSeparator;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public Character getFillCharacter() {
        return fillCharacter;
    }

    public List<FixedWidthFieldSpec> getFieldSpecs() {
        return fieldSpecs;
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

    public boolean isSkipAllNullOrEmpty() {
        return skipAllNullOrEmpty;
    }

    public LineSeparator getLineSeparator() {
        return lineSeparator;
    }

}
