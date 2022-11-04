package stexfires.io;

import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface RecordFileSpec {

    CodingErrorAction DEFAULT_CODING_ERROR_ACTION = CodingErrorAction.REPORT;

    LineSeparator DEFAULT_LINE_SEPARATOR = LineSeparator.LF;

    Charset charset();

    CodingErrorAction codingErrorAction();

    LineSeparator lineSeparator();

    CharsetEncoder newCharsetEncoder();

    CharsetDecoder newCharsetDecoder();

    default BufferedReader newBufferedReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, newCharsetDecoder()));
    }

    default BufferedWriter newBufferedWriter(OutputStream outputStream) {
        return new BufferedWriter(new OutputStreamWriter(outputStream, newCharsetEncoder()));
    }

}
