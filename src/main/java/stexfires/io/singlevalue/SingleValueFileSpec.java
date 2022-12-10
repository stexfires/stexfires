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
        @Nullable String linePrefix,
        int producerSkipFirstLines,
        int producerIgnoreFirstRecords,
        int producerIgnoreLastRecords,
        boolean producerTrimToEmpty,
        boolean producerSkipEmptyLines,
        @NotNull LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        boolean consumerSkipNullValueLines
) implements ReadableRecordFileSpec<ValueRecord, SingleValueProducer>, WritableRecordFileSpec<ValueRecord, SingleValueConsumer> {

    public static final String DEFAULT_LINE_PREFIX = null;
    public static final int DEFAULT_PRODUCER_SKIP_FIRST_LINES = 0;
    public static final int DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS = 0;
    public static final int DEFAULT_PRODUCER_IGNORE_LAST_RECORDS = 0;
    public static final boolean DEFAULT_PRODUCER_TRIM_TO_EMPTY = false;
    public static final boolean DEFAULT_PRODUCER_SKIP_EMPTY_LINES = false;
    public static final String DEFAULT_CONSUMER_TEXT_BEFORE = null;
    public static final String DEFAULT_CONSUMER_TEXT_AFTER = null;
    public static final boolean DEFAULT_CONSUMER_SKIP_NULL_VALUE_LINES = false;

    public SingleValueFileSpec {
        Objects.requireNonNull(charsetCoding);
        if (producerSkipFirstLines < 0) {
            throw new IllegalArgumentException("producerSkipFirstLines < 0");
        }
        if (producerIgnoreFirstRecords < 0) {
            throw new IllegalArgumentException("producerIgnoreFirstRecords < 0");
        }
        if (producerIgnoreLastRecords < 0) {
            throw new IllegalArgumentException("producerIgnoreLastRecords < 0");
        }
        Objects.requireNonNull(consumerLineSeparator);
    }

    public static SingleValueFileSpec read(@NotNull CharsetCoding charsetCoding,
                                           @Nullable String linePrefix,
                                           int producerSkipFirstLines,
                                           int producerIgnoreFirstRecords,
                                           int producerIgnoreLastRecords,
                                           boolean producerTrimToEmpty,
                                           boolean producerSkipEmptyLines) {
        return new SingleValueFileSpec(
                charsetCoding,
                linePrefix,
                producerSkipFirstLines,
                producerIgnoreFirstRecords,
                producerIgnoreLastRecords,
                producerTrimToEmpty,
                producerSkipEmptyLines,
                DEFAULT_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                DEFAULT_CONSUMER_SKIP_NULL_VALUE_LINES
        );
    }

    public static SingleValueFileSpec write(@NotNull CharsetCoding charsetCoding,
                                            @Nullable String linePrefix,
                                            @NotNull LineSeparator consumerLineSeparator,
                                            @Nullable String consumerTextBefore,
                                            @Nullable String consumerTextAfter,
                                            boolean consumerSkipNullValueLines) {
        return new SingleValueFileSpec(
                charsetCoding,
                linePrefix,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                DEFAULT_PRODUCER_TRIM_TO_EMPTY,
                DEFAULT_PRODUCER_SKIP_EMPTY_LINES,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                consumerSkipNullValueLines
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
