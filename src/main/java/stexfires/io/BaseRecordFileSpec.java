package stexfires.io;

import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class BaseRecordFileSpec implements RecordFileSpec {

    private final CharsetCoding charsetCoding;

    private final LineSeparator lineSeparator;

    public BaseRecordFileSpec(CharsetCoding charsetCoding,
                              LineSeparator lineSeparator) {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(lineSeparator);
        this.charsetCoding = charsetCoding;
        this.lineSeparator = lineSeparator;
    }

    @Override
    public final CharsetCoding charsetCoding() {
        return charsetCoding;
    }

    @Override
    public final LineSeparator lineSeparator() {
        return lineSeparator;
    }

}
