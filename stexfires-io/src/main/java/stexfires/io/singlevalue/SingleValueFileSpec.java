package stexfires.io.singlevalue;

import org.jspecify.annotations.Nullable;
import stexfires.io.consumer.WritableRecordFileSpec;
import stexfires.io.producer.ProducerReadLineHandling;
import stexfires.io.producer.ReadableRecordFileSpec;
import stexfires.record.ValueRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.*;

/**
 * @since 0.1
 */
public record SingleValueFileSpec(
        CharsetCoding charsetCoding,
        @Nullable String linePrefix,
        int producerSkipFirstLines,
        ProducerReadLineHandling producerReadLineHandling,
        int producerIgnoreFirstRecords,
        int producerIgnoreLastRecords,
        boolean producerTrimValueToEmpty,
        boolean producerSkipEmptyValue,
        LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        boolean consumerSkipNullValueLines
) implements ReadableRecordFileSpec<ValueRecord, SingleValueProducer>,
             WritableRecordFileSpec<ValueRecord, SingleValueConsumer> {

    public static final @Nullable String DEFAULT_LINE_PREFIX = null;
    public static final int DEFAULT_PRODUCER_SKIP_FIRST_LINES = 0;
    public static final ProducerReadLineHandling DEFAULT_PRODUCER_READ_LINE_HANDLING = ProducerReadLineHandling.NO_HANDLING;
    public static final int DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS = 0;
    public static final int DEFAULT_PRODUCER_IGNORE_LAST_RECORDS = 0;
    public static final boolean DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY = false;
    public static final boolean DEFAULT_PRODUCER_SKIP_EMPTY_VALUE = false;
    public static final @Nullable String DEFAULT_CONSUMER_TEXT_BEFORE = null;
    public static final @Nullable String DEFAULT_CONSUMER_TEXT_AFTER = null;
    public static final boolean DEFAULT_CONSUMER_SKIP_NULL_VALUE_LINES = false;

    public SingleValueFileSpec {
        Objects.requireNonNull(charsetCoding);
        if (producerSkipFirstLines < 0) {
            throw new IllegalArgumentException("producerSkipFirstLines < 0");
        }
        Objects.requireNonNull(producerReadLineHandling);
        if (producerIgnoreFirstRecords < 0) {
            throw new IllegalArgumentException("producerIgnoreFirstRecords < 0");
        }
        if (producerIgnoreLastRecords < 0) {
            throw new IllegalArgumentException("producerIgnoreLastRecords < 0");
        }
        Objects.requireNonNull(consumerLineSeparator);
    }

    public static SingleValueFileSpec producerFileSpec(CharsetCoding charsetCoding) {
        return new SingleValueFileSpec(
                charsetCoding,
                DEFAULT_LINE_PREFIX,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_READ_LINE_HANDLING,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY,
                DEFAULT_PRODUCER_SKIP_EMPTY_VALUE,
                DEFAULT_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                DEFAULT_CONSUMER_SKIP_NULL_VALUE_LINES
        );
    }

    public static SingleValueFileSpec producerFileSpec(CharsetCoding charsetCoding,
                                                       @Nullable String linePrefix,
                                                       int producerSkipFirstLines,
                                                       ProducerReadLineHandling producerReadLineHandling,
                                                       int producerIgnoreFirstRecords,
                                                       int producerIgnoreLastRecords,
                                                       boolean producerTrimValueToEmpty,
                                                       boolean producerSkipEmptyValue) {
        return new SingleValueFileSpec(
                charsetCoding,
                linePrefix,
                producerSkipFirstLines,
                producerReadLineHandling,
                producerIgnoreFirstRecords,
                producerIgnoreLastRecords,
                producerTrimValueToEmpty,
                producerSkipEmptyValue,
                DEFAULT_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                DEFAULT_CONSUMER_SKIP_NULL_VALUE_LINES
        );
    }

    public static SingleValueFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                       LineSeparator consumerLineSeparator) {
        return new SingleValueFileSpec(
                charsetCoding,
                DEFAULT_LINE_PREFIX,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_READ_LINE_HANDLING,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY,
                DEFAULT_PRODUCER_SKIP_EMPTY_VALUE,
                consumerLineSeparator,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                DEFAULT_CONSUMER_SKIP_NULL_VALUE_LINES
        );
    }

    public static SingleValueFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                       @Nullable String linePrefix,
                                                       LineSeparator consumerLineSeparator,
                                                       @Nullable String consumerTextBefore,
                                                       @Nullable String consumerTextAfter,
                                                       boolean consumerSkipNullValueLines) {
        return new SingleValueFileSpec(
                charsetCoding,
                linePrefix,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_READ_LINE_HANDLING,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY,
                DEFAULT_PRODUCER_SKIP_EMPTY_VALUE,
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
