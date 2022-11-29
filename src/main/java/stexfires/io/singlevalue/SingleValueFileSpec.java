package stexfires.io.singlevalue;

import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableWritableRecordFileSpec;
import stexfires.record.ValueRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class SingleValueFileSpec implements ReadableWritableRecordFileSpec<ValueRecord, ValueRecord> {

    // DEFAULT - read
    public static final boolean DEFAULT_SKIP_EMPTY_LINES = false;
    public static final int DEFAULT_IGNORE_FIRST = 0;
    public static final int DEFAULT_IGNORE_LAST = 0;

    // DEFAULT - write
    public static final boolean DEFAULT_SKIP_NULL_VALUE = false;

    // FIELD - both
    private final CharsetCoding charsetCoding;
    private final LineSeparator lineSeparator;
    private final String textBefore;
    private final String textAfter;

    // FIELD - read
    private final boolean skipEmptyLines;
    private final int ignoreFirst;
    private final int ignoreLast;

    // FIELD - write
    private final boolean skipNullValue;

    public SingleValueFileSpec(CharsetCoding charsetCoding,
                               LineSeparator lineSeparator,
                               @Nullable String textBefore,
                               @Nullable String textAfter,
                               boolean skipEmptyLines,
                               int ignoreFirst,
                               int ignoreLast,
                               boolean skipNullValue) {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(lineSeparator);
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

        // read
        this.skipEmptyLines = skipEmptyLines;
        this.ignoreFirst = ignoreFirst;
        this.ignoreLast = ignoreLast;

        // write
        this.skipNullValue = skipNullValue;
    }

    public static SingleValueFileSpec read(CharsetCoding charsetCoding,
                                           boolean skipEmptyLines,
                                           int ignoreFirstLines,
                                           int ignoreLastLines) {
        return new SingleValueFileSpec(charsetCoding,
                DEFAULT_LINE_SEPARATOR,
                DEFAULT_TEXT_BEFORE,
                DEFAULT_TEXT_AFTER,
                skipEmptyLines,
                ignoreFirstLines,
                ignoreLastLines,
                DEFAULT_SKIP_NULL_VALUE);
    }

    public static SingleValueFileSpec write(CharsetCoding charsetCoding,
                                            LineSeparator lineSeparator,
                                            @Nullable String textBefore,
                                            @Nullable String textAfter,
                                            boolean skipNullValue) {
        return new SingleValueFileSpec(charsetCoding,
                lineSeparator,
                textBefore,
                textAfter,
                DEFAULT_SKIP_EMPTY_LINES,
                DEFAULT_IGNORE_FIRST,
                DEFAULT_IGNORE_LAST,
                skipNullValue);
    }

    @Override
    public SingleValueProducer producer(BufferedReader bufferedReader) {
        Objects.requireNonNull(bufferedReader);
        return new SingleValueProducer(bufferedReader, this);
    }

    @Override
    public SingleValueProducer producer(InputStream inputStream) {
        Objects.requireNonNull(inputStream);
        return producer(charsetCoding().newBufferedReader(inputStream));
    }

    @Override
    public SingleValueConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new SingleValueConsumer(bufferedWriter, this);
    }

    @Override
    public SingleValueConsumer consumer(OutputStream outputStream) {
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

    public boolean skipEmptyLines() {
        return skipEmptyLines;
    }

    public int ignoreFirst() {
        return ignoreFirst;
    }

    public int ignoreLast() {
        return ignoreLast;
    }

    public boolean skipNullValue() {
        return skipNullValue;
    }

}
