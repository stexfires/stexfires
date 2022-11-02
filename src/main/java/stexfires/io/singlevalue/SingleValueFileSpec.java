package stexfires.io.singlevalue;

import org.jetbrains.annotations.Nullable;
import stexfires.io.spec.AbstractRecordFileSpec;
import stexfires.util.LineSeparator;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class SingleValueFileSpec extends AbstractRecordFileSpec {

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

    public SingleValueFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                               @Nullable String decoderReplacement, @Nullable String encoderReplacement,
                               boolean skipEmptyLines, int ignoreFirst, int ignoreLast,
                               LineSeparator lineSeparator, boolean skipNullValue) {
        super(charset, codingErrorAction, decoderReplacement, encoderReplacement, lineSeparator);
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

    public static SingleValueFileSpec read(Charset charset) {
        return new SingleValueFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                null, null,
                DEFAULT_SKIP_EMPTY_LINES, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST,
                DEFAULT_LINE_SEPARATOR, DEFAULT_SKIP_NULL_VALUE);
    }

    public static SingleValueFileSpec read(Charset charset, CodingErrorAction codingErrorAction,
                                           @Nullable String decoderReplacement) {
        return new SingleValueFileSpec(charset, codingErrorAction,
                decoderReplacement, null,
                DEFAULT_SKIP_EMPTY_LINES, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST,
                DEFAULT_LINE_SEPARATOR, DEFAULT_SKIP_NULL_VALUE);
    }

    public static SingleValueFileSpec read(Charset charset,
                                           boolean skipEmptyLines, int ignoreFirstLines, int ignoreLastLines) {
        return new SingleValueFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                null, null,
                skipEmptyLines, ignoreFirstLines, ignoreLastLines,
                DEFAULT_LINE_SEPARATOR, DEFAULT_SKIP_NULL_VALUE);
    }

    public static SingleValueFileSpec read(Charset charset, CodingErrorAction codingErrorAction,
                                           @Nullable String decoderReplacement,
                                           boolean skipEmptyLines, int ignoreFirstLines, int ignoreLastLines) {
        return new SingleValueFileSpec(charset, codingErrorAction,
                decoderReplacement, null,
                skipEmptyLines, ignoreFirstLines, ignoreLastLines,
                DEFAULT_LINE_SEPARATOR, DEFAULT_SKIP_NULL_VALUE);
    }

    public static SingleValueFileSpec write(Charset charset,
                                            LineSeparator lineSeparator) {
        return new SingleValueFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                null, null,
                DEFAULT_SKIP_EMPTY_LINES, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST,
                lineSeparator, DEFAULT_SKIP_NULL_VALUE);
    }

    public static SingleValueFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                            @Nullable String encoderReplacement,
                                            LineSeparator lineSeparator) {
        return new SingleValueFileSpec(charset, codingErrorAction,
                null, encoderReplacement,
                DEFAULT_SKIP_EMPTY_LINES, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST,
                lineSeparator, DEFAULT_SKIP_NULL_VALUE);
    }

    public static SingleValueFileSpec write(Charset charset,
                                            LineSeparator lineSeparator, boolean skipNullValue) {
        return new SingleValueFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                null, null,
                DEFAULT_SKIP_EMPTY_LINES, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST,
                lineSeparator, skipNullValue);
    }

    public static SingleValueFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                            @Nullable String encoderReplacement,
                                            LineSeparator lineSeparator, boolean skipNullValue) {
        return new SingleValueFileSpec(charset, codingErrorAction,
                null, encoderReplacement,
                DEFAULT_SKIP_EMPTY_LINES, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST,
                lineSeparator, skipNullValue);
    }

    @Override
    public SingleValueFile file(Path path) {
        return new SingleValueFile(path, this);
    }

    public SingleValueProducer producer(InputStream inputStream) {
        return new SingleValueProducer(newBufferedReader(inputStream), this);
    }

    public SingleValueConsumer consumer(OutputStream outputStream) {
        return new SingleValueConsumer(newBufferedWriter(outputStream), this);
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

    public boolean isSkipNullValue() {
        return skipNullValue;
    }

}
