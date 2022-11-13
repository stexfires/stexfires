package stexfires.io;

import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface RecordFileSpec {

    LineSeparator DEFAULT_LINE_SEPARATOR = LineSeparator.LF;

    CharsetCoding charsetCoding();

    LineSeparator lineSeparator();

}
