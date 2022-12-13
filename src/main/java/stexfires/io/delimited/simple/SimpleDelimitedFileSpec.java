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
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record SimpleDelimitedFileSpec(
        @NotNull CharsetCoding charsetCoding,
        @NotNull String fieldDelimiter,
        int producerSkipFirstLines,
        int producerIgnoreFirstRecords,
        int producerIgnoreLastRecords,
        boolean producerSkipEmptyLines,
        boolean producerSkipAllNullOrEmpty,
        @NotNull LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        @NotNull List<SimpleDelimitedFieldSpec> fieldSpecs
) implements ReadableRecordFileSpec<TextRecord, SimpleDelimitedProducer>, WritableRecordFileSpec<TextRecord, SimpleDelimitedConsumer> {

    public static final String FIELD_DELIMITER_CHARACTER_TABULATION = "\t";
    public static final String FIELD_DELIMITER_SPACE = " ";
    public static final String FIELD_DELIMITER_COMMA = ",";
    public static final String FIELD_DELIMITER_SEMICOLON = ";";
    public static final String FIELD_DELIMITER_VERTICAL_LINE = "|";

    public static final int DEFAULT_PRODUCER_SKIP_FIRST_LINES = 0;
    public static final int DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS = 0;
    public static final int DEFAULT_PRODUCER_IGNORE_LAST_RECORDS = 0;
    public static final boolean DEFAULT_PRODUCER_SKIP_EMPTY_LINES = false;
    public static final boolean DEFAULT_PRODUCER_SKIP_ALL_NULL_OR_EMPTY = false;
    public static final String DEFAULT_CONSUMER_TEXT_BEFORE = null;
    public static final String DEFAULT_CONSUMER_TEXT_AFTER = null;

    public SimpleDelimitedFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(fieldDelimiter);
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
        Objects.requireNonNull(fieldSpecs);

        fieldSpecs = new ArrayList<>(fieldSpecs);
    }

    public static SimpleDelimitedFileSpec read(@NotNull CharsetCoding charsetCoding,
                                               @NotNull String fieldDelimiter,
                                               int producerSkipFirstLines,
                                               int producerIgnoreFirstRecords,
                                               int producerIgnoreLastRecords,
                                               boolean producerSkipEmptyLines,
                                               boolean producerSkipAllNullOrEmpty,
                                               @NotNull List<SimpleDelimitedFieldSpec> fieldSpecs) {
        return new SimpleDelimitedFileSpec(
                charsetCoding,
                fieldDelimiter,
                producerSkipFirstLines,
                producerIgnoreFirstRecords,
                producerIgnoreLastRecords,
                producerSkipEmptyLines,
                producerSkipAllNullOrEmpty,
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
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                DEFAULT_PRODUCER_SKIP_EMPTY_LINES,
                DEFAULT_PRODUCER_SKIP_ALL_NULL_OR_EMPTY,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                fieldSpecs
        );
    }

    public static @NotNull List<SimpleDelimitedFieldSpec> newFieldSpecs(int number) {
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
    public @NotNull List<SimpleDelimitedFieldSpec> fieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

}
