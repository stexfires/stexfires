package stexfires.io.config;

import org.jetbrains.annotations.NotNull;
import stexfires.io.ReadableRecordFileSpec;
import stexfires.io.WritableRecordFileSpec;
import stexfires.record.KeyValueRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record ConfigFileSpec(
        @NotNull CharsetCoding charsetCoding,
        @NotNull String valueDelimiter,
        @NotNull LineSeparator consumerLineSeparator
) implements ReadableRecordFileSpec<KeyValueRecord, ConfigProducer>, WritableRecordFileSpec<KeyValueRecord, ConfigConsumer> {

    public static final String DEFAULT_VALUE_DELIMITER = "=";

    public static final String NULL_CATEGORY = "";
    public static final String NULL_KEY = "";
    public static final String NULL_VALUE = "";
    public static final String CATEGORY_BEGIN_MARKER = "[";
    public static final String CATEGORY_END_MARKER = "]";

    public ConfigFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(valueDelimiter);
        Objects.requireNonNull(consumerLineSeparator);
    }

    public static ConfigFileSpec read(@NotNull CharsetCoding charsetCoding,
                                      @NotNull String valueDelimiter) {
        return new ConfigFileSpec(
                charsetCoding,
                valueDelimiter,
                DEFAULT_CONSUMER_LINE_SEPARATOR
        );
    }

    public static ConfigFileSpec write(@NotNull CharsetCoding charsetCoding,
                                       @NotNull String valueDelimiter,
                                       @NotNull LineSeparator consumerLineSeparator) {
        return new ConfigFileSpec(
                charsetCoding,
                valueDelimiter,
                consumerLineSeparator
        );
    }

    @Override
    public ConfigProducer producer(BufferedReader bufferedReader) {
        Objects.requireNonNull(bufferedReader);
        return new ConfigProducer(bufferedReader, this);
    }

    @Override
    public ConfigConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new ConfigConsumer(bufferedWriter, this);
    }

}
