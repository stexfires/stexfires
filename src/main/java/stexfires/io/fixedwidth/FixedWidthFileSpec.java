package stexfires.io.fixedwidth;

import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableWritableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.util.Alignment;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class FixedWidthFileSpec implements ReadableWritableRecordFileSpec<TextRecord, TextRecord> {

    // DEFAULT - read
    public static final int DEFAULT_IGNORE_FIRST = 0;
    public static final int DEFAULT_IGNORE_LAST = 0;
    public static final boolean DEFAULT_SKIP_EMPTY_LINES = false;
    public static final boolean DEFAULT_SKIP_ALL_NULL_OR_EMPTY = false;

    // FIELD - both
    private final CharsetCoding charsetCoding;
    private final LineSeparator lineSeparator;
    private final String textBefore;
    private final String textAfter;
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

    public FixedWidthFileSpec(CharsetCoding charsetCoding,
                              LineSeparator lineSeparator,
                              @Nullable String textBefore,
                              @Nullable String textAfter,
                              int recordWidth,
                              boolean separateRecordsByLineSeparator,
                              Alignment alignment,
                              Character fillCharacter,
                              List<FixedWidthFieldSpec> fieldSpecs,
                              int ignoreFirst, int ignoreLast,
                              boolean skipEmptyLines, boolean skipAllNullOrEmpty) {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(lineSeparator);
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
        this.charsetCoding = charsetCoding;
        this.lineSeparator = lineSeparator;
        this.textBefore = textBefore;
        this.textAfter = textAfter;
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

    public static FixedWidthFileSpec read(CharsetCoding charsetCoding,
                                          int recordWidth,
                                          boolean separateRecordsByLineSeparator,
                                          Alignment alignment,
                                          Character fillCharacter,
                                          List<FixedWidthFieldSpec> fieldSpecs,
                                          int ignoreFirst, int ignoreLast,
                                          boolean skipEmptyLines, boolean skipAllNullOrEmpty) {
        return new FixedWidthFileSpec(charsetCoding,
                DEFAULT_LINE_SEPARATOR,
                DEFAULT_TEXT_BEFORE,
                DEFAULT_TEXT_AFTER,
                recordWidth,
                separateRecordsByLineSeparator,
                alignment,
                fillCharacter,
                fieldSpecs,
                ignoreFirst, ignoreLast,
                skipEmptyLines, skipAllNullOrEmpty);
    }

    public static FixedWidthFileSpec write(CharsetCoding charsetCoding,
                                           LineSeparator lineSeparator,
                                           @Nullable String textBefore,
                                           @Nullable String textAfter,
                                           int recordWidth, boolean separateRecordsByLineSeparator,
                                           Alignment alignment, Character fillCharacter,
                                           List<FixedWidthFieldSpec> fieldSpecs) {
        return new FixedWidthFileSpec(charsetCoding,
                lineSeparator,
                textBefore,
                textAfter,
                recordWidth,
                separateRecordsByLineSeparator,
                alignment,
                fillCharacter,
                fieldSpecs,
                DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST,
                DEFAULT_SKIP_EMPTY_LINES, DEFAULT_SKIP_ALL_NULL_OR_EMPTY);
    }

    @Override
    public FixedWidthProducer producer(BufferedReader bufferedReader) {
        Objects.requireNonNull(bufferedReader);
        return new FixedWidthProducer(bufferedReader, this);
    }

    @Override
    public FixedWidthProducer producer(InputStream inputStream) {
        Objects.requireNonNull(inputStream);
        return producer(charsetCoding().newBufferedReader(inputStream));
    }

    @Override
    public FixedWidthConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new FixedWidthConsumer(bufferedWriter, this);
    }

    @Override
    public FixedWidthConsumer consumer(OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        return consumer(charsetCoding().newBufferedWriter(outputStream));
    }

    @Override
    public CharsetCoding charsetCoding() {
        return charsetCoding;
    }

    @Override
    public LineSeparator lineSeparator() {
        return lineSeparator;
    }

    @Override
    public @Nullable String textBefore() {
        return textBefore;
    }

    @Override
    public @Nullable String textAfter() {
        return textAfter;
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
