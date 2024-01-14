package stexfires.io.delimited.simple;

import org.jspecify.annotations.Nullable;
import stexfires.io.consumer.WritableRecordFileSpec;
import stexfires.io.producer.ProducerReadLineHandling;
import stexfires.io.producer.ReadableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public record SimpleDelimitedFileSpec(
        CharsetCoding charsetCoding,
        String fieldDelimiter,
        int producerSkipFirstLines,
        ProducerReadLineHandling producerReadLineHandling,
        int producerIgnoreFirstRecords,
        int producerIgnoreLastRecords,
        boolean producerSkipAllNullOrEmpty,
        LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        List<SimpleDelimitedFieldSpec> fieldSpecs
) implements ReadableRecordFileSpec<TextRecord, SimpleDelimitedProducer>, WritableRecordFileSpec<TextRecord, SimpleDelimitedConsumer> {

    public static final int DEFAULT_PRODUCER_SKIP_FIRST_LINES = 0;
    /**
     * Default for Markdown is {@code ProducerReadLineHandling.THROW_EXCEPTION_ON_BLANK_LINE}.
     */
    public static final ProducerReadLineHandling DEFAULT_PRODUCER_READ_LINE_HANDLING = ProducerReadLineHandling.THROW_EXCEPTION_ON_BLANK_LINE;
    public static final int DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS = 0;
    public static final int DEFAULT_PRODUCER_IGNORE_LAST_RECORDS = 0;
    public static final boolean DEFAULT_PRODUCER_SKIP_ALL_NULL_OR_EMPTY = false;
    public static final @Nullable String DEFAULT_CONSUMER_TEXT_BEFORE = null;
    public static final @Nullable String DEFAULT_CONSUMER_TEXT_AFTER = null;

    public static final String FIELD_DELIMITER_CHARACTER_TABULATION = "\t";
    public static final String FIELD_DELIMITER_SPACE = " ";
    public static final String FIELD_DELIMITER_COMMA = ",";
    public static final String FIELD_DELIMITER_SEMICOLON = ";";
    public static final String FIELD_DELIMITER_VERTICAL_LINE = "|";

    public SimpleDelimitedFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(fieldDelimiter);
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
        Objects.requireNonNull(fieldSpecs);

        fieldSpecs = new ArrayList<>(fieldSpecs);
    }

    public static SimpleDelimitedFileSpec producerFileSpec(CharsetCoding charsetCoding,
                                                           String fieldDelimiter,
                                                           List<SimpleDelimitedFieldSpec> fieldSpecs) {
        return new SimpleDelimitedFileSpec(
                charsetCoding,
                fieldDelimiter,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_READ_LINE_HANDLING,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                DEFAULT_PRODUCER_SKIP_ALL_NULL_OR_EMPTY,
                DEFAULT_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                fieldSpecs
        );
    }

    public static SimpleDelimitedFileSpec producerFileSpec(CharsetCoding charsetCoding,
                                                           String fieldDelimiter,
                                                           int producerSkipFirstLines,
                                                           ProducerReadLineHandling producerReadLineHandling,
                                                           int producerIgnoreFirstRecords,
                                                           int producerIgnoreLastRecords,
                                                           boolean producerSkipAllNullOrEmpty,
                                                           List<SimpleDelimitedFieldSpec> fieldSpecs) {
        return new SimpleDelimitedFileSpec(
                charsetCoding,
                fieldDelimiter,
                producerSkipFirstLines,
                producerReadLineHandling,
                producerIgnoreFirstRecords,
                producerIgnoreLastRecords,
                producerSkipAllNullOrEmpty,
                DEFAULT_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                fieldSpecs
        );
    }

    public static SimpleDelimitedFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                           String fieldDelimiter,
                                                           LineSeparator consumerLineSeparator,
                                                           List<SimpleDelimitedFieldSpec> fieldSpecs) {
        return new SimpleDelimitedFileSpec(
                charsetCoding,
                fieldDelimiter,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_READ_LINE_HANDLING,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                DEFAULT_PRODUCER_SKIP_ALL_NULL_OR_EMPTY,
                consumerLineSeparator,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                fieldSpecs
        );
    }

    public static SimpleDelimitedFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                           String fieldDelimiter,
                                                           LineSeparator consumerLineSeparator,
                                                           @Nullable String consumerTextBefore,
                                                           @Nullable String consumerTextAfter,
                                                           List<SimpleDelimitedFieldSpec> fieldSpecs) {
        return new SimpleDelimitedFileSpec(
                charsetCoding,
                fieldDelimiter,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_READ_LINE_HANDLING,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                DEFAULT_PRODUCER_SKIP_ALL_NULL_OR_EMPTY,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                fieldSpecs
        );
    }

    public static List<SimpleDelimitedFieldSpec> newFieldSpecs(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("number < 0");
        }
        var fieldSpec = new SimpleDelimitedFieldSpec();
        return Stream.generate(() -> fieldSpec).limit(number).toList();
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
    public List<SimpleDelimitedFieldSpec> fieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

}
