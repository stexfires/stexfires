package stexfires.io.json;

import org.jspecify.annotations.Nullable;
import stexfires.io.producer.ProducerReadLineHandling;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.*;

/**
 * @since 0.1
 */
public record JsonStreamingFileSpec(
        CharsetCoding charsetCoding,
        RecordJsonType recordJsonType,
        boolean recordSeparatorBefore,
        int producerSkipFirstLines,
        ProducerReadLineHandling producerReadLineHandling,
        int producerIgnoreFirstRecords,
        int producerIgnoreLastRecords,
        LineSeparator consumerLineSeparator,
        @Nullable String consumerTextBefore,
        @Nullable String consumerTextAfter,
        boolean consumerSpaceAfterValueSeparator,
        List<JsonFieldSpec> fieldSpecs
) implements JsonFileSpec {

    public static final int DEFAULT_PRODUCER_SKIP_FIRST_LINES = 0;
    public static final ProducerReadLineHandling DEFAULT_PRODUCER_READ_LINE_HANDLING = ProducerReadLineHandling.SKIP_BLANK_LINE;
    public static final int DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS = 0;
    public static final int DEFAULT_PRODUCER_IGNORE_LAST_RECORDS = 0;

    public JsonStreamingFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(recordJsonType);
        Objects.requireNonNull(producerReadLineHandling);
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

    public static JsonStreamingFileSpec producerFileSpec(RecordJsonType recordJsonType,
                                                         boolean recordSeparatorBefore,
                                                         ProducerReadLineHandling producerReadLineHandling,
                                                         List<JsonFieldSpec> fieldSpecs) {
        return new JsonStreamingFileSpec(
                JSON_CHARSET_CODING,
                recordJsonType,
                recordSeparatorBefore,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                producerReadLineHandling,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                JSON_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                DEFAULT_CONSUMER_SPACE_AFTER_VALUE_SEPARATOR,
                fieldSpecs
        );
    }

    public static JsonStreamingFileSpec consumerFileSpec(RecordJsonType recordJsonType,
                                                         boolean recordSeparatorBefore,
                                                         boolean consumerSpaceAfterValueSeparator,
                                                         List<JsonFieldSpec> fieldSpecs) {
        return new JsonStreamingFileSpec(
                JSON_CHARSET_CODING,
                recordJsonType,
                recordSeparatorBefore,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_READ_LINE_HANDLING,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                JSON_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_TEXT_BEFORE,
                DEFAULT_CONSUMER_TEXT_AFTER,
                consumerSpaceAfterValueSeparator,
                fieldSpecs
        );
    }

    public static JsonStreamingFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                         RecordJsonType recordJsonType,
                                                         boolean recordSeparatorBefore,
                                                         LineSeparator consumerLineSeparator,
                                                         @Nullable String consumerTextBefore,
                                                         @Nullable String consumerTextAfter,
                                                         boolean consumerSpaceAfterValueSeparator,
                                                         List<JsonFieldSpec> fieldSpecs) {
        return new JsonStreamingFileSpec(
                charsetCoding,
                recordJsonType,
                recordSeparatorBefore,
                DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                DEFAULT_PRODUCER_READ_LINE_HANDLING,
                DEFAULT_PRODUCER_IGNORE_FIRST_RECORDS,
                DEFAULT_PRODUCER_IGNORE_LAST_RECORDS,
                consumerLineSeparator,
                consumerTextBefore,
                consumerTextAfter,
                consumerSpaceAfterValueSeparator,
                fieldSpecs
        );
    }

    @Override
    public JsonProducer producer(BufferedReader bufferedReader) {
        Objects.requireNonNull(bufferedReader);
        return new JsonProducer(bufferedReader, this);
    }

    @Override
    public JsonConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new JsonConsumer(bufferedWriter, this, fieldSpecs);
    }

    @Override
    public boolean embeddedInJsonObject() {
        return false;
    }

}

