package stexfires.io.config;

import stexfires.io.ReadableWritableRecordFileSpec;
import stexfires.record.KeyValueRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ConfigFileSpec extends ReadableWritableRecordFileSpec<KeyValueRecord, KeyValueRecord> {

    public static final String NULL_CATEGORY = "";
    public static final String NULL_KEY = "";
    public static final String NULL_VALUE = "";

    public static final String CATEGORY_BEGIN_MARKER = "[";
    public static final String CATEGORY_END_MARKER = "]";

    // DEFAULT - both
    public static final String DEFAULT_VALUE_DELIMITER = "=";

    // FIELD - both
    private final String valueDelimiter;

    public ConfigFileSpec(CharsetCoding charsetCoding,
                          LineSeparator lineSeparator,
                          String valueDelimiter) {
        super(charsetCoding,
                lineSeparator,
                null,
                null);
        Objects.requireNonNull(valueDelimiter);

        // both
        this.valueDelimiter = valueDelimiter;
    }

    public static ConfigFileSpec read(CharsetCoding charsetCoding,
                                      String valueDelimiter) {
        return new ConfigFileSpec(charsetCoding,
                DEFAULT_LINE_SEPARATOR,
                valueDelimiter);
    }

    public static ConfigFileSpec write(CharsetCoding charsetCoding,
                                       LineSeparator lineSeparator,
                                       String valueDelimiter) {
        return new ConfigFileSpec(charsetCoding,
                lineSeparator,
                valueDelimiter);
    }

    @Override
    public ConfigProducer producer(InputStream inputStream) {
        return new ConfigProducer(charsetCoding().newBufferedReader(inputStream), this);
    }

    @Override
    public ConfigConsumer consumer(OutputStream outputStream) {
        return new ConfigConsumer(charsetCoding().newBufferedWriter(outputStream), this);
    }

    public String valueDelimiter() {
        return valueDelimiter;
    }

}
