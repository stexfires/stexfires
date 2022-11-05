package stexfires.io.fixedwidth;

import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableWritableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.util.Alignment;
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
public final class FixedWidthFileSpec extends ReadableWritableRecordFileSpec<TextRecord, TextRecord> {

    // DEFAULT - read
    public static final int DEFAULT_IGNORE_FIRST = 0;
    public static final int DEFAULT_IGNORE_LAST = 0;
    public static final boolean DEFAULT_SKIP_EMPTY_LINES = false;
    public static final boolean DEFAULT_SKIP_ALL_NULL_OR_EMPTY = false;

    // FIELD - both
    private final int recordWidth;
    private final boolean separateRecordsByLineSeparator;
    private final Alignment alignment;
    private final Character fillCharacter;
    private final List<FixedWidthFieldSpec> fieldSpecs;

    // FIELD - read
    private final int ignoreFirst;
    private final int ignoreLast;
    private final boolean skipEmptyLines;
    private final boolean skipAllNullOrEmpty;

    public FixedWidthFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                              @Nullable String decoderReplacement, @Nullable String encoderReplacement,
                              int recordWidth, boolean separateRecordsByLineSeparator,
                              Alignment alignment, Character fillCharacter,
                              List<FixedWidthFieldSpec> fieldSpecs,
                              int ignoreFirst, int ignoreLast,
                              boolean skipEmptyLines, boolean skipAllNullOrEmpty,
                              LineSeparator lineSeparator) {
        super(charset, codingErrorAction, decoderReplacement, encoderReplacement, lineSeparator);
        Objects.requireNonNull(alignment);
        Objects.requireNonNull(fillCharacter);
        Objects.requireNonNull(fieldSpecs);
        if (recordWidth < 0) {
            throw new IllegalArgumentException("recordWidth < 0");
        }
        if (ignoreFirst < 0) {
            throw new IllegalArgumentException("ignoreFirst < 0");
        }
        if (ignoreLast < 0) {
            throw new IllegalArgumentException("ignoreLast < 0");
        }

        // both
        this.recordWidth = recordWidth;
        this.separateRecordsByLineSeparator = separateRecordsByLineSeparator;
        this.alignment = alignment;
        this.fillCharacter = fillCharacter;
        this.fieldSpecs = new ArrayList<>(fieldSpecs);

        // read
        this.ignoreFirst = ignoreFirst;
        this.ignoreLast = ignoreLast;
        this.skipEmptyLines = skipEmptyLines;
        this.skipAllNullOrEmpty = skipAllNullOrEmpty;
    }

    public static FixedWidthFileSpec read(Charset charset,
                                          int recordWidth, boolean separateRecordsByLineSeparator,
                                          Alignment alignment, Character fillCharacter,
                                          List<FixedWidthFieldSpec> fieldSpecs,
                                          int ignoreFirst, int ignoreLast,
                                          boolean skipEmptyLines, boolean skipAllNullOrEmpty) {
        return new FixedWidthFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                null, null,
                recordWidth, separateRecordsByLineSeparator,
                alignment, fillCharacter,
                fieldSpecs,
                ignoreFirst, ignoreLast,
                skipEmptyLines, skipAllNullOrEmpty,
                DEFAULT_LINE_SEPARATOR);
    }

    public static FixedWidthFileSpec read(Charset charset, CodingErrorAction codingErrorAction,
                                          @Nullable String decoderReplacement,
                                          int recordWidth, boolean separateRecordsByLineSeparator,
                                          Alignment alignment, Character fillCharacter,
                                          List<FixedWidthFieldSpec> fieldSpecs,
                                          int ignoreFirst, int ignoreLast,
                                          boolean skipEmptyLines, boolean skipAllNullOrEmpty) {
        return new FixedWidthFileSpec(charset, codingErrorAction,
                decoderReplacement, null,
                recordWidth, separateRecordsByLineSeparator,
                alignment, fillCharacter,
                fieldSpecs,
                ignoreFirst, ignoreLast,
                skipEmptyLines, skipAllNullOrEmpty,
                DEFAULT_LINE_SEPARATOR);
    }

    public static FixedWidthFileSpec write(Charset charset,
                                           int recordWidth, boolean separateRecordsByLineSeparator,
                                           Alignment alignment, Character fillCharacter,
                                           List<FixedWidthFieldSpec> fieldSpecs,
                                           LineSeparator lineSeparator) {
        return new FixedWidthFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                null, null,
                recordWidth, separateRecordsByLineSeparator,
                alignment, fillCharacter,
                fieldSpecs,
                DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST,
                DEFAULT_SKIP_EMPTY_LINES, DEFAULT_SKIP_ALL_NULL_OR_EMPTY,
                lineSeparator);
    }

    public static FixedWidthFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                           @Nullable String encoderReplacement,
                                           int recordWidth, boolean separateRecordsByLineSeparator,
                                           Alignment alignment, Character fillCharacter,
                                           List<FixedWidthFieldSpec> fieldSpecs,
                                           LineSeparator lineSeparator) {
        return new FixedWidthFileSpec(charset, codingErrorAction,
                null, encoderReplacement,
                recordWidth, separateRecordsByLineSeparator,
                alignment, fillCharacter,
                fieldSpecs,
                DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST,
                DEFAULT_SKIP_EMPTY_LINES, DEFAULT_SKIP_ALL_NULL_OR_EMPTY,
                lineSeparator);
    }

    @Override
    public FixedWidthProducer producer(InputStream inputStream) {
        return new FixedWidthProducer(newBufferedReader(inputStream), this);
    }

    @Override
    public FixedWidthConsumer consumer(OutputStream outputStream) {
        return new FixedWidthConsumer(newBufferedWriter(outputStream), this);
    }

    public int recordWidth() {
        return recordWidth;
    }

    public boolean separateRecordsByLineSeparator() {
        return separateRecordsByLineSeparator;
    }

    public Alignment alignment() {
        return alignment;
    }

    public Character fillCharacter() {
        return fillCharacter;
    }

    public List<FixedWidthFieldSpec> fieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

    public int ignoreFirst() {
        return ignoreFirst;
    }

    public int ignoreLast() {
        return ignoreLast;
    }

    public boolean skipEmptyLines() {
        return skipEmptyLines;
    }

    public boolean skipAllNullOrEmpty() {
        return skipAllNullOrEmpty;
    }

}
