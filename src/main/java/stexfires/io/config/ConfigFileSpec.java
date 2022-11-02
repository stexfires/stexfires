package stexfires.io.config;

import org.jetbrains.annotations.Nullable;
import stexfires.io.spec.AbstractRecordFileSpec;
import stexfires.util.LineSeparator;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ConfigFileSpec extends AbstractRecordFileSpec {

    public static final String NULL_CATEGORY = "";
    public static final String NULL_KEY = "";
    public static final String NULL_VALUE = "";

    public static final String CATEGORY_PREFIX = "[";

    // TODO Rename POSTFIX into SUFFIX or "end marker" or "end border"
    public static final String CATEGORY_POSTFIX = "]";

    // DEFAULT - both
    public static final String DEFAULT_VALUE_DELIMITER = "=";

    // FIELD - both
    private final String valueDelimiter;

    public ConfigFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                          @Nullable String decoderReplacement, @Nullable String encoderReplacement,
                          String valueDelimiter,
                          LineSeparator lineSeparator) {
        super(charset, codingErrorAction, decoderReplacement, encoderReplacement, lineSeparator);
        Objects.requireNonNull(valueDelimiter);

        // both
        this.valueDelimiter = valueDelimiter;
    }

    public static ConfigFileSpec read(Charset charset, String valueDelimiter) {
        return new ConfigFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                null, null,
                valueDelimiter, DEFAULT_LINE_SEPARATOR);
    }

    public static ConfigFileSpec read(Charset charset, CodingErrorAction codingErrorAction,
                                      @Nullable String decoderReplacement,
                                      String valueDelimiter) {
        return new ConfigFileSpec(charset, codingErrorAction,
                decoderReplacement, null,
                valueDelimiter, DEFAULT_LINE_SEPARATOR);
    }

    public static ConfigFileSpec write(Charset charset, String valueDelimiter,
                                       LineSeparator lineSeparator) {
        return new ConfigFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                null, null,
                valueDelimiter, lineSeparator);
    }

    public static ConfigFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                       @Nullable String encoderReplacement,
                                       String valueDelimiter,
                                       LineSeparator lineSeparator) {
        return new ConfigFileSpec(charset, codingErrorAction,
                null, encoderReplacement,
                valueDelimiter, lineSeparator);
    }

    @Override
    public ConfigFile file(Path path) {
        return new ConfigFile(path, this);
    }

    public ConfigProducer producer(InputStream inputStream) {
        return new ConfigProducer(newBufferedReader(inputStream), this);
    }

    public ConfigConsumer consumer(OutputStream outputStream) {
        return new ConfigConsumer(newBufferedWriter(outputStream), this);
    }

    public String getValueDelimiter() {
        return valueDelimiter;
    }

}
