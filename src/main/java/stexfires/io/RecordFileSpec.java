package stexfires.io;

import org.jetbrains.annotations.Nullable;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface RecordFileSpec {

    CharsetCoding DEFAULT_CHARSET_CODING = CharsetCoding.UTF_8_REPORTING;

    LineSeparator DEFAULT_LINE_SEPARATOR = LineSeparator.LF;

    CharsetCoding charsetCoding();

    LineSeparator lineSeparator();

    @Nullable String textBefore();

    @Nullable String textAfter();

}
