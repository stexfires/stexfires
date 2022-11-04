package stexfires.io.spec;

import stexfires.io.RecordFile;
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
import java.nio.file.Path;

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

    CharsetEncoder newCharsetEncoder();

    CharsetDecoder newCharsetDecoder();

    RecordFile<?> newRecordFile(Path path);

    default BufferedReader newBufferedReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, newCharsetDecoder()));
    }

    default BufferedWriter newBufferedWriter(OutputStream outputStream) {
        return new BufferedWriter(new OutputStreamWriter(outputStream, newCharsetEncoder()));
    }

}
