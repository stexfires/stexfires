package stexfires.io.spec;

import stexfires.util.LineSeparator;

import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface RecordFileSpec {

    CodingErrorAction DEFAULT_CODING_ERROR_ACTION = CodingErrorAction.REPORT;

    LineSeparator DEFAULT_LINE_SEPARATOR = LineSeparator.LF;


    Charset getCharset();

    CodingErrorAction getCodingErrorAction();

    LineSeparator getLineSeparator();

}
