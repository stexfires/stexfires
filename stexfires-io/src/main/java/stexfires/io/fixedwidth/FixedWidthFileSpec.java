package stexfires.io.fixedwidth;

import org.jspecify.annotations.Nullable;
import stexfires.io.consumer.WritableRecordFileSpec;
import stexfires.io.producer.ReadableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.util.Alignment;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.*;

/**
 * @since 0.1
 */
public record FixedWidthFileSpec(
        CharsetCoding charsetCoding,
        int recordWidth,
        boolean separateRecordsByLineSeparator,
        Alignment alignment,
        Character fillCharacter,
        int producerSkipFirstLines,
        int producerIgnoreFirstRecords,
        int producerIgnoreLastRecords,
        boolean producerSkipEmptyLines,
        boolean producerSkipAllNullOrEmpty,
        LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        List<FixedWidthFieldSpec> fieldSpecs
) implements ReadableRecordFileSpec<TextRecord, FixedWidthProducer>,
             WritableRecordFileSpec<TextRecord, FixedWidthConsumer> {

    public static final boolean DEFAULT_SEPARATE_RECORDS_BY_LINE_SEPARATOR = true;
    public static final int DEFAULT_PRODUCER_SKIP_FIRST_LINES = 0;
    public static final int DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS = 0;
    public static final int DEFAULT_PRODUCER_IGNORE_LAST_RECORDS = 0;
    public static final boolean DEFAULT_PRODUCER_SKIP_EMPTY_LINES = false;
    public static final boolean DEFAULT_PRODUCER_SKIP_ALL_NULL_OR_EMPTY = false;
    public static final @Nullable String DEFAULT_CONSUMER_TEXT_BEFORE = null;
    public static final @Nullable String DEFAULT_CONSUMER_TEXT_AFTER = null;

    public FixedWidthFileSpec {
        Objects.requireNonNull(charsetCoding);
        if (recordWidth < 0) {
            throw new IllegalArgumentException("recordWidth < 0");
        }
        Objects.requireNonNull(alignment);
        Objects.requireNonNull(fillCharacter);
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
        fieldSpecs = List.copyOf(fieldSpecs);
    }

    public static FixedWidthFileSpec producerFileSpec(CharsetCoding charsetCoding,
                                                      int recordWidth,
                                                      Alignment alignment,
                                                      Character fillCharacter,
                                                      List<FixedWidthFieldSpec> fieldSpecs) {
        return new FixedWidthFileSpec(
                charsetCoding,
                recordWidth,
                DEFAULT_SEPARATE_RECORDS_BY_LINE_SEPARATOR,
                alignment,
                fillCharacter,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                DEFAULT_PRODUCER_SKIP_EMPTY_LINES,
                DEFAULT_PRODUCER_SKIP_ALL_NULL_OR_EMPTY,
                DEFAULT_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                fieldSpecs
        );
    }

    public static FixedWidthFileSpec producerFileSpec(CharsetCoding charsetCoding,
                                                      int recordWidth,
                                                      boolean separateRecordsByLineSeparator,
                                                      Alignment alignment,
                                                      Character fillCharacter,
                                                      int producerSkipFirstLines,
                                                      int producerIgnoreFirstRecords,
                                                      int producerIgnoreLastRecords,
                                                      boolean producerSkipEmptyLines,
                                                      boolean producerSkipAllNullOrEmpty,
                                                      List<FixedWidthFieldSpec> fieldSpecs) {
        return new FixedWidthFileSpec(
                charsetCoding,
                recordWidth,
                separateRecordsByLineSeparator,
                alignment,
                fillCharacter,
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

    public static FixedWidthFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                      int recordWidth,
                                                      Alignment alignment,
                                                      Character fillCharacter,
                                                      LineSeparator consumerLineSeparator,
                                                      List<FixedWidthFieldSpec> fieldSpecs) {
        return new FixedWidthFileSpec(
                charsetCoding,
                recordWidth,
                DEFAULT_SEPARATE_RECORDS_BY_LINE_SEPARATOR,
                alignment,
                fillCharacter,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                DEFAULT_PRODUCER_SKIP_EMPTY_LINES,
                DEFAULT_PRODUCER_SKIP_ALL_NULL_OR_EMPTY,
                consumerLineSeparator,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                fieldSpecs
        );
    }

    public static FixedWidthFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                      int recordWidth,
                                                      boolean separateRecordsByLineSeparator,
                                                      Alignment alignment,
                                                      Character fillCharacter,
                                                      LineSeparator consumerLineSeparator,
                                                      @Nullable String consumerTextBefore,
                                                      @Nullable String consumerTextAfter,
                                                      List<FixedWidthFieldSpec> fieldSpecs) {
        return new FixedWidthFileSpec(
                charsetCoding,
                recordWidth,
                separateRecordsByLineSeparator,
                alignment,
                fillCharacter,
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

    @Override
    public FixedWidthProducer producer(BufferedReader bufferedReader) {
        Objects.requireNonNull(bufferedReader);
        return new FixedWidthProducer(bufferedReader, this, fieldSpecs);
    }

    @Override
    public FixedWidthConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new FixedWidthConsumer(bufferedWriter, this, fieldSpecs);
    }

}
