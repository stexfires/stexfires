package stexfires.io.markdown.list;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.io.consumer.WritableRecordFileSpec;
import stexfires.io.producer.ProducerReadLineHandling;
import stexfires.io.producer.ReadableRecordFileSpec;
import stexfires.record.ValueRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;
import stexfires.util.Strings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Objects;

/**
 * @since 0.1
 */
public record MarkdownListFileSpec(
        @NotNull CharsetCoding charsetCoding,
        int producerSkipFirstLines,
        @NotNull ProducerReadLineHandling producerReadLineHandling,
        int producerIgnoreFirstRecords,
        int producerIgnoreLastRecords,
        boolean producerTrimValueToEmpty,
        boolean producerSkipEmptyValue,
        boolean producerLinePrefixAsCategory,
        @NotNull LineSeparator consumerLineSeparator,
        @NotNull MarkdownListMarker consumerListMarker,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        boolean consumerSkipNullValue
) implements ReadableRecordFileSpec<ValueRecord, MarkdownListProducer>, WritableRecordFileSpec<ValueRecord, MarkdownListConsumer> {

    public static final int DEFAULT_PRODUCER_SKIP_FIRST_LINES = 0;
    /**
     * Default for Markdown is {@code ProducerReadLineHandling.SKIP_BLANK_LINE}.
     */
    public static final ProducerReadLineHandling DEFAULT_PRODUCER_READ_LINE_HANDLING = ProducerReadLineHandling.SKIP_BLANK_LINE;
    public static final int DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS = 0;
    public static final int DEFAULT_PRODUCER_IGNORE_LAST_RECORDS = 0;
    public static final boolean DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY = false;
    public static final boolean DEFAULT_PRODUCER_SKIP_EMPTY_VALUE = false;
    public static final boolean DEFAULT_PRODUCER_LINE_PREFIX_AS_CATEGORY = false;
    public static final MarkdownListMarker DEFAULT_CONSUMER_LIST_MARKER = MarkdownListMarker.BULLET_ASTERISK;
    public static final String DEFAULT_CONSUMER_TEXT_BEFORE = null;
    public static final String DEFAULT_CONSUMER_TEXT_AFTER = null;
    public static final boolean DEFAULT_CONSUMER_SKIP_NULL_VALUE = false;

    public static final long ORDERED_LIST_START_NUMBER = 1L;
    public static final String LIST_MARKER_SEPARATOR = Strings.SPACE;

    public MarkdownListFileSpec {
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
        Objects.requireNonNull(consumerListMarker);
    }

    public static MarkdownListFileSpec producerFileSpec(@NotNull CharsetCoding charsetCoding) {
        return new MarkdownListFileSpec(
                charsetCoding,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_READ_LINE_HANDLING,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY,
                DEFAULT_PRODUCER_SKIP_EMPTY_VALUE,
                DEFAULT_PRODUCER_LINE_PREFIX_AS_CATEGORY,
                DEFAULT_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_LIST_MARKER,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                DEFAULT_CONSUMER_SKIP_NULL_VALUE
        );
    }

    public static MarkdownListFileSpec producerFileSpec(@NotNull CharsetCoding charsetCoding,
                                                        int producerSkipFirstLines,
                                                        @NotNull ProducerReadLineHandling producerReadLineHandling,
                                                        int producerIgnoreFirstRecords,
                                                        int producerIgnoreLastRecords,
                                                        boolean producerTrimValueToEmpty,
                                                        boolean producerSkipEmptyValue,
                                                        boolean producerLinePrefixAsCategory) {
        return new MarkdownListFileSpec(
                charsetCoding,
                producerSkipFirstLines,
                producerReadLineHandling,
                producerIgnoreFirstRecords,
                producerIgnoreLastRecords,
                producerTrimValueToEmpty,
                producerSkipEmptyValue,
                producerLinePrefixAsCategory,
                DEFAULT_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_LIST_MARKER,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                DEFAULT_CONSUMER_SKIP_NULL_VALUE
        );
    }

    public static MarkdownListFileSpec consumerFileSpec(@NotNull CharsetCoding charsetCoding,
                                                        @NotNull LineSeparator consumerLineSeparator,
                                                        @NotNull MarkdownListMarker consumerListMarker) {
        return new MarkdownListFileSpec(
                charsetCoding,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_READ_LINE_HANDLING,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY,
                DEFAULT_PRODUCER_SKIP_EMPTY_VALUE,
                DEFAULT_PRODUCER_LINE_PREFIX_AS_CATEGORY,
                consumerLineSeparator,
                consumerListMarker,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                DEFAULT_CONSUMER_SKIP_NULL_VALUE
        );
    }

    public static MarkdownListFileSpec consumerFileSpec(@NotNull CharsetCoding charsetCoding,
                                                        @NotNull LineSeparator consumerLineSeparator,
                                                        @NotNull MarkdownListMarker consumerListMarker,
                                                        @Nullable String consumerTextBefore,
                                                        @Nullable String consumerTextAfter,
                                                        boolean consumerSkipNullValue) {
        return new MarkdownListFileSpec(
                charsetCoding,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_READ_LINE_HANDLING,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY,
                DEFAULT_PRODUCER_SKIP_EMPTY_VALUE,
                DEFAULT_PRODUCER_LINE_PREFIX_AS_CATEGORY,
                consumerLineSeparator,
                consumerListMarker,
                consumerTextBefore,
                consumerTextAfter,
                consumerSkipNullValue
        );
    }

    @Override
    public MarkdownListProducer producer(BufferedReader bufferedReader) {
        Objects.requireNonNull(bufferedReader);
        return new MarkdownListProducer(bufferedReader, this);
    }

    @Override
    public MarkdownListConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new MarkdownListConsumer(bufferedWriter, this);
    }

}
