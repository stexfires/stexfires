package stexfires.io.config;

import stexfires.util.LineSeparator;

import java.nio.charset.Charset;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ConfigFileSpec {

    public static final String DEFAULT_VALUE_DELIMITER = "=";

    private final Charset charset;

    private final LineSeparator lineSeparator;
    private final String valueDelimiter;

    public ConfigFileSpec(Charset charset, LineSeparator lineSeparator) {
        this(charset, lineSeparator, DEFAULT_VALUE_DELIMITER);
    }

    public ConfigFileSpec(Charset charset, LineSeparator lineSeparator, String valueDelimiter) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(lineSeparator);
        Objects.requireNonNull(valueDelimiter);
        this.charset = charset;
        this.lineSeparator = lineSeparator;
        this.valueDelimiter = valueDelimiter;
    }

    public Charset getCharset() {
        return charset;
    }

    public LineSeparator getLineSeparator() {
        return lineSeparator;
    }

    public String getValueDelimiter() {
        return valueDelimiter;
    }

}
