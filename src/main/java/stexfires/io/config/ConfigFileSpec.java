package stexfires.io.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
        @NotNull LineSeparator lineSeparator,
        @NotNull String valueDelimiter
) implements ReadableRecordFileSpec<KeyValueRecord, ConfigProducer>, WritableRecordFileSpec<KeyValueRecord, ConfigConsumer> {

    public static final String NULL_CATEGORY = "";
    public static final String NULL_KEY = "";
    public static final String NULL_VALUE = "";
    public static final String CATEGORY_BEGIN_MARKER = "[";
    public static final String CATEGORY_END_MARKER = "]";
    public static final String DEFAULT_VALUE_DELIMITER = "=";

    public ConfigFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(lineSeparator);
        Objects.requireNonNull(valueDelimiter);
    }

    public static ConfigFileSpec read(CharsetCoding charsetCoding,
                                      String valueDelimiter) {
        return new ConfigFileSpec(
                charsetCoding,
                DEFAULT_LINE_SEPARATOR,
                valueDelimiter);
    }

    public static ConfigFileSpec write(CharsetCoding charsetCoding,
                                       LineSeparator lineSeparator,
                                       String valueDelimiter) {
        return new ConfigFileSpec(
                charsetCoding,
                lineSeparator,
                valueDelimiter);
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

    @Override
    public @Nullable String textBefore() {
        return WritableRecordFileSpec.DEFAULT_TEXT_BEFORE;
    }

    @Override
    public @Nullable String textAfter() {
        return WritableRecordFileSpec.DEFAULT_TEXT_AFTER;
    }

}
