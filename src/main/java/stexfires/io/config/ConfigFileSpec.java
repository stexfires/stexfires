package stexfires.io.config;

import stexfires.util.LineSeparator;

import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ConfigFileSpec {

    public static final String DEFAULT_VALUE_DELIMITER = "=";

    public static final String NULL_CATEGORY = "";
    public static final String NULL_KEY = "";
    public static final String NULL_VALUE = "";

    public static final String CATEGORY_PREFIX = "[";
    public static final String CATEGORY_POSTFIX = "]";

    private final Charset charset;

    private final LineSeparator lineSeparator;
    private final String valueDelimiter;
    private final CodingErrorAction codingErrorAction;

    public ConfigFileSpec(Charset charset, LineSeparator lineSeparator, String valueDelimiter,
                          CodingErrorAction codingErrorAction) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(lineSeparator);
        Objects.requireNonNull(valueDelimiter);
        Objects.requireNonNull(codingErrorAction);
        this.charset = charset;
        this.lineSeparator = lineSeparator;
        this.valueDelimiter = valueDelimiter;
        this.codingErrorAction = codingErrorAction;
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

    public CodingErrorAction getCodingErrorAction() {
        return codingErrorAction;
    }

}
