package stexfires.io.singlevalue;

import stexfires.util.LineSeparator;

import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class SingleValueFileSpec {

    private final Charset charset;

    private final LineSeparator lineSeparator;

    private final int ignoreFirstLines;
    private final int ignoreLastLines;

    private final boolean skipNullValue;
    private final CodingErrorAction codingErrorAction;

    public SingleValueFileSpec(Charset charset, LineSeparator lineSeparator, int ignoreFirstLines, int ignoreLastLines, boolean skipNullValue, CodingErrorAction codingErrorAction) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(lineSeparator);
        if (ignoreFirstLines < 0) {
            throw new IllegalArgumentException("ignoreFirstLines < 0");
        }
        if (ignoreLastLines < 0) {
            throw new IllegalArgumentException("ignoreLastLines < 0");
        }
        this.charset = charset;
        this.lineSeparator = lineSeparator;
        this.ignoreFirstLines = ignoreFirstLines;
        this.ignoreLastLines = ignoreLastLines;
        this.skipNullValue = skipNullValue;
        this.codingErrorAction = codingErrorAction;
    }

    public Charset getCharset() {
        return charset;
    }

    public LineSeparator getLineSeparator() {
        return lineSeparator;
    }

    public int getIgnoreFirstLines() {
        return ignoreFirstLines;
    }

    public int getIgnoreLastLines() {
        return ignoreLastLines;
    }

    public boolean isSkipNullValue() {
        return skipNullValue;
    }

    public CodingErrorAction getCodingErrorAction() {
        return codingErrorAction;
    }

}
