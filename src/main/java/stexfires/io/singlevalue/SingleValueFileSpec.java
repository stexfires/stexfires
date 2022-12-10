package stexfires.io.singlevalue;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableRecordFileSpec;
import stexfires.io.WritableRecordFileSpec;
import stexfires.record.ValueRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record SingleValueFileSpec(
        @NotNull CharsetCoding charsetCoding,
        @NotNull LineSeparator lineSeparator,
        @Nullable String textBefore,
        @Nullable String textAfter,
        boolean consumerSkipNullValueLines,
        boolean producerTrimToEmpty,
        boolean producerSkipEmptyLines,
        int ignoreFirst,
        int ignoreLast,
        @Nullable String linePrefix
) implements ReadableRecordFileSpec<ValueRecord, SingleValueProducer>, WritableRecordFileSpec<ValueRecord, SingleValueConsumer> {

    public static final String DEFAULT_LINE_PREFIX = null;
    public static final boolean DEFAULT_PRODUCER_TRIM_TO_EMPTY = false;
    public static final boolean DEFAULT_PRODUCER_SKIP_EMPTY_LINES = false;
    public static final int DEFAULT_IGNORE_FIRST = 0;
    public static final int DEFAULT_IGNORE_LAST = 0;
    public static final boolean DEFAULT_CONSUMER_SKIP_NULL_VALUE = false;

    public SingleValueFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(lineSeparator);
        if (ignoreFirst < 0) {
            throw new IllegalArgumentException("ignoreFirst < 0");
        }
        if (ignoreLast < 0) {
            throw new IllegalArgumentException("ignoreLast < 0");
        }
    }

    public static SingleValueFileSpec read(CharsetCoding charsetCoding,
                                           @Nullable String linePrefix,
                                           boolean producerTrimToEmpty,
                                           boolean producerSkipEmptyLines,
                                           int ignoreFirstLines,
                                           int ignoreLastLines) {
        return new SingleValueFileSpec(
                charsetCoding,
                DEFAULT_LINE_SEPARATOR,
                DEFAULT_TEXT_BEFORE,
                DEFAULT_TEXT_AFTER,
                DEFAULT_CONSUMER_SKIP_NULL_VALUE,
                producerTrimToEmpty,
                producerSkipEmptyLines,
                ignoreFirstLines,
                ignoreLastLines,
                linePrefix
        );
    }

    public static SingleValueFileSpec write(CharsetCoding charsetCoding,
                                            LineSeparator lineSeparator,
                                            @Nullable String linePrefix,
                                            @Nullable String textBefore,
                                            @Nullable String textAfter,
                                            boolean consumerSkipNullValueLines) {
        return new SingleValueFileSpec(
                charsetCoding,
                lineSeparator,
                textBefore,
                textAfter,
                consumerSkipNullValueLines,
                DEFAULT_PRODUCER_TRIM_TO_EMPTY,
                DEFAULT_PRODUCER_SKIP_EMPTY_LINES,
                DEFAULT_IGNORE_FIRST,
                DEFAULT_IGNORE_LAST,
                linePrefix
        );
    }

    @Override
    public SingleValueProducer producer(BufferedReader bufferedReader) {
        Objects.requireNonNull(bufferedReader);
        return new SingleValueProducer(bufferedReader, this);
    }

    @Override
    public SingleValueConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new SingleValueConsumer(bufferedWriter, this);
    }

}
