package stexfires.io.delimited.simple;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableRecordFileSpec;
import stexfires.io.WritableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record SimpleDelimitedFileSpec(
        @NotNull CharsetCoding charsetCoding,
        @NotNull String fieldDelimiter,
        int producerIgnoreFirstRecords,
        int producerIgnoreLastRecords,
        boolean producerSkipEmptyLines,
        boolean producerSkipAllNull,
        @NotNull LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        @NotNull List<SimpleDelimitedFieldSpec> fieldSpecs
) implements ReadableRecordFileSpec<TextRecord, SimpleDelimitedProducer>, WritableRecordFileSpec<TextRecord, SimpleDelimitedConsumer> {

    public static final int DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS = 0;
    public static final int DEFAULT_PRODUCER_IGNORE_LAST_RECORDS = 0;
    public static final boolean DEFAULT_PRODUCER_SKIP_EMPTY_LINES = false;
    public static final boolean DEFAULT_PRODUCER_SKIP_ALL_NULL = false;
    public static final String DEFAULT_CONSUMER_TEXT_BEFORE = null;
    public static final String DEFAULT_CONSUMER_TEXT_AFTER = null;

    public SimpleDelimitedFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(fieldDelimiter);
        if (producerIgnoreFirstRecords < 0) {
            throw new IllegalArgumentException("producerIgnoreFirstRecords < 0");
        }
        if (producerIgnoreLastRecords < 0) {
            throw new IllegalArgumentException("producerIgnoreLastRecords < 0");
        }
        Objects.requireNonNull(consumerLineSeparator);
        Objects.requireNonNull(fieldSpecs);

        fieldSpecs = new ArrayList<>(fieldSpecs);
    }

    public static SimpleDelimitedFileSpec read(@NotNull CharsetCoding charsetCoding,
                                               @NotNull String fieldDelimiter,
                                               int producerIgnoreFirstRecords,
                                               int producerIgnoreLastRecords,
                                               boolean producerSkipEmptyLines,
                                               boolean producerSkipAllNull,
                                               @NotNull List<SimpleDelimitedFieldSpec> fieldSpecs) {
        return new SimpleDelimitedFileSpec(
                charsetCoding,
                fieldDelimiter,
                producerIgnoreFirstRecords,
                producerIgnoreLastRecords,
                producerSkipEmptyLines,
                producerSkipAllNull,
                DEFAULT_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                fieldSpecs
        );
    }

    public static SimpleDelimitedFileSpec write(@NotNull CharsetCoding charsetCoding,
                                                @NotNull String fieldDelimiter,
                                                @NotNull LineSeparator consumerLineSeparator,
                                                @Nullable String consumerTextBefore,
                                                @Nullable String consumerTextAfter,
                                                @NotNull List<SimpleDelimitedFieldSpec> fieldSpecs) {
        return new SimpleDelimitedFileSpec(
                charsetCoding,
                fieldDelimiter,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                DEFAULT_PRODUCER_SKIP_EMPTY_LINES,
                DEFAULT_PRODUCER_SKIP_ALL_NULL,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                fieldSpecs
        );
    }

    @Override
    public SimpleDelimitedProducer producer(BufferedReader bufferedReader) {
        Objects.requireNonNull(bufferedReader);
        return new SimpleDelimitedProducer(bufferedReader, this);
    }

    @Override
    public SimpleDelimitedConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new SimpleDelimitedConsumer(bufferedWriter, this);
    }

    @Override
    public @NotNull List<SimpleDelimitedFieldSpec> fieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

}
