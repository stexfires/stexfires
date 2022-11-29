package stexfires.io.delimited.simple;

import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableWritableRecordFileSpec;
import stexfires.record.TextRecord;
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
public final class SimpleDelimitedFileSpec implements ReadableWritableRecordFileSpec<TextRecord, TextRecord> {

    // DEFAULT - read
    public static final int DEFAULT_IGNORE_FIRST = 0;
    public static final int DEFAULT_IGNORE_LAST = 0;
    public static final boolean DEFAULT_SKIP_EMPTY_LINES = false;
    public static final boolean DEFAULT_SKIP_ALL_NULL = false;

    // FIELD - both
    private final CharsetCoding charsetCoding;
    private final LineSeparator lineSeparator;
    private final String textBefore;
    private final String textAfter;
    private final String fieldDelimiter;
    private final List<SimpleDelimitedFieldSpec> fieldSpecs;

    // FIELD - read
    private final int ignoreFirst;
    private final int ignoreLast;
    private final boolean skipEmptyLines;
    private final boolean skipAllNull;

    public SimpleDelimitedFileSpec(CharsetCoding charsetCoding,
                                   LineSeparator lineSeparator,
                                   @Nullable String textBefore,
                                   @Nullable String textAfter,
                                   String fieldDelimiter,
                                   List<SimpleDelimitedFieldSpec> fieldSpecs,
                                   int ignoreFirst, int ignoreLast,
                                   boolean skipEmptyLines, boolean skipAllNull) {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(lineSeparator);
        Objects.requireNonNull(fieldDelimiter);
        Objects.requireNonNull(fieldSpecs);
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
        this.fieldDelimiter = fieldDelimiter;
        this.fieldSpecs = new ArrayList<>(fieldSpecs);

        // read
        this.ignoreFirst = ignoreFirst;
        this.ignoreLast = ignoreLast;
        this.skipEmptyLines = skipEmptyLines;
        this.skipAllNull = skipAllNull;
    }

    public static SimpleDelimitedFileSpec read(CharsetCoding charsetCoding,
                                               String fieldDelimiter,
                                               List<SimpleDelimitedFieldSpec> fieldSpecs,
                                               int ignoreFirst, int ignoreLast,
                                               boolean skipEmptyLines, boolean skipAllNull) {
        return new SimpleDelimitedFileSpec(charsetCoding,
                DEFAULT_LINE_SEPARATOR,
                DEFAULT_TEXT_BEFORE,
                DEFAULT_TEXT_AFTER,
                fieldDelimiter,
                fieldSpecs,
                ignoreFirst, ignoreLast,
                skipEmptyLines, skipAllNull);
    }

    public static SimpleDelimitedFileSpec write(CharsetCoding charsetCoding,
                                                LineSeparator lineSeparator,
                                                @Nullable String textBefore,
                                                @Nullable String textAfter,
                                                String fieldDelimiter,
                                                List<SimpleDelimitedFieldSpec> fieldSpecs) {
        return new SimpleDelimitedFileSpec(charsetCoding,
                lineSeparator,
                textBefore,
                textAfter,
                fieldDelimiter,
                fieldSpecs,
                DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST,
                DEFAULT_SKIP_EMPTY_LINES, DEFAULT_SKIP_ALL_NULL);
    }

    @Override
    public SimpleDelimitedProducer producer(BufferedReader bufferedReader) {
        Objects.requireNonNull(bufferedReader);
        return new SimpleDelimitedProducer(bufferedReader, this);
    }

    @Override
    public SimpleDelimitedProducer producer(InputStream inputStream) {
        Objects.requireNonNull(inputStream);
        return producer(charsetCoding().newBufferedReader(inputStream));
    }

    @Override
    public SimpleDelimitedConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new SimpleDelimitedConsumer(bufferedWriter, this);
    }

    @Override
    public SimpleDelimitedConsumer consumer(OutputStream outputStream) {
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

    public String fieldDelimiter() {
        return fieldDelimiter;
    }

    public List<SimpleDelimitedFieldSpec> fieldSpecs() {
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

    public boolean skipAllNull() {
        return skipAllNull;
    }

}
