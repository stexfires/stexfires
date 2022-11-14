package stexfires.io.singlevalue;

import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableWritableRecordFileSpec;
import stexfires.record.ValueRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class SingleValueFileSpec extends ReadableWritableRecordFileSpec<ValueRecord, ValueRecord> {

    // DEFAULT - read
    public static final boolean DEFAULT_SKIP_EMPTY_LINES = false;
    public static final int DEFAULT_IGNORE_FIRST = 0;
    public static final int DEFAULT_IGNORE_LAST = 0;

    // DEFAULT - write
    public static final boolean DEFAULT_SKIP_NULL_VALUE = false;

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
        super(charsetCoding,
                lineSeparator,
                textBefore,
                textAfter);
        if (ignoreFirst < 0) {
            throw new IllegalArgumentException("ignoreFirst < 0");
        }
        if (ignoreLast < 0) {
            throw new IllegalArgumentException("ignoreLast < 0");
        }

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
    public SingleValueProducer producer(InputStream inputStream) {
        return new SingleValueProducer(charsetCoding().newBufferedReader(inputStream), this);
    }

    @Override
    public SingleValueConsumer consumer(OutputStream outputStream) {
        return new SingleValueConsumer(charsetCoding().newBufferedWriter(outputStream), this);
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
