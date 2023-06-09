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
        @NotNull MarkdownListMarker listMarker,
        int producerSkipFirstLines,
        @NotNull ProducerReadLineHandling producerReadLineHandling,
        int producerIgnoreFirstRecords,
        int producerIgnoreLastRecords,
        boolean producerTrimValueToEmpty,
        boolean producerSkipEmptyValue,
        @NotNull LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        boolean consumerSkipNullValueLines
) implements ReadableRecordFileSpec<ValueRecord, MarkdownListProducer>, WritableRecordFileSpec<ValueRecord, MarkdownListConsumer> {

    public static final MarkdownListMarker DEFAULT_LIST_MARKER = MarkdownListMarker.BULLET_ASTERISK;
    public static final int DEFAULT_PRODUCER_SKIP_FIRST_LINES = 0;
    /**
     * Default for Markdown is {@code ProducerReadLineHandling.SKIP_BLANK_LINE}.
     */
    public static final ProducerReadLineHandling DEFAULT_PRODUCER_READ_LINE_HANDLING = ProducerReadLineHandling.SKIP_BLANK_LINE;
    public static final int DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS = 0;
    public static final int DEFAULT_PRODUCER_IGNORE_LAST_RECORDS = 0;
    public static final boolean DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY = false;
    public static final boolean DEFAULT_PRODUCER_SKIP_EMPTY_VALUE = false;
    public static final String DEFAULT_CONSUMER_TEXT_BEFORE = null;
    public static final String DEFAULT_CONSUMER_TEXT_AFTER = null;
    public static final boolean DEFAULT_CONSUMER_SKIP_NULL_VALUE_LINES = false;

    public static final long ORDERED_LIST_START_NUMBER = 1L;
    public static final String LIST_MARKER_SEPARATOR = Strings.SPACE;

    public MarkdownListFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(listMarker);
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

    public static MarkdownListFileSpec producerFileSpec(@NotNull CharsetCoding charsetCoding,
                                                        @NotNull MarkdownListMarker listMarker) {
        return new MarkdownListFileSpec(
                charsetCoding,
                listMarker,
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

    public static MarkdownListFileSpec producerFileSpec(@NotNull CharsetCoding charsetCoding,
                                                        @NotNull MarkdownListMarker listMarker,
                                                        int producerSkipFirstLines,
                                                        @NotNull ProducerReadLineHandling producerReadLineHandling,
                                                        int producerIgnoreFirstRecords,
                                                        int producerIgnoreLastRecords,
                                                        boolean producerTrimValueToEmpty,
                                                        boolean producerSkipEmptyValue) {
        return new MarkdownListFileSpec(
                charsetCoding,
                listMarker,
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

    public static MarkdownListFileSpec consumerFileSpec(@NotNull CharsetCoding charsetCoding,
                                                        @NotNull MarkdownListMarker listMarker,
                                                        @NotNull LineSeparator consumerLineSeparator) {
        return new MarkdownListFileSpec(
                charsetCoding,
                listMarker,
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

    public static MarkdownListFileSpec consumerFileSpec(@NotNull CharsetCoding charsetCoding,
                                                        @NotNull MarkdownListMarker listMarker,
                                                        @NotNull LineSeparator consumerLineSeparator,
                                                        @Nullable String consumerTextBefore,
                                                        @Nullable String consumerTextAfter,
                                                        boolean consumerSkipNullValueLines) {
        return new MarkdownListFileSpec(
                charsetCoding,
                listMarker,
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
