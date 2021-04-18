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
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public abstract class AbstractRecordFileSpec implements RecordFileSpec {

    private final Charset charset;
    private final CodingErrorAction codingErrorAction;
    private final LineSeparator lineSeparator;

    protected AbstractRecordFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                                     LineSeparator lineSeparator) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(codingErrorAction);
        Objects.requireNonNull(lineSeparator);
        this.charset = charset;
        this.codingErrorAction = codingErrorAction;
        this.lineSeparator = lineSeparator;
    }

    public abstract RecordFile<?> file(Path path);

    @Override
    public final Charset getCharset() {
        return charset;
    }

    @Override
    public final CodingErrorAction getCodingErrorAction() {
        return codingErrorAction;
    }

    @Override
    public final LineSeparator getLineSeparator() {
        return lineSeparator;
    }

    protected final BufferedReader newBufferedReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, newCharsetDecoder()));
    }

    protected final BufferedReader newBufferedReader(InputStream inputStream, String decoderReplacementValue) {
        return new BufferedReader(new InputStreamReader(inputStream, newCharsetDecoder(decoderReplacementValue)));
    }

    protected final BufferedWriter newBufferedWriter(OutputStream outputStream, byte... encoderReplacementValue) {
        return new BufferedWriter(new OutputStreamWriter(outputStream, newCharsetEncoder(encoderReplacementValue)));
    }

    protected final CharsetDecoder newCharsetDecoder() {
        return newCharsetDecoder(null);
    }

    protected final CharsetDecoder newCharsetDecoder(String decoderReplacementValue) {
        CharsetDecoder charsetDecoder = charset.newDecoder().onMalformedInput(codingErrorAction);
        if ((codingErrorAction == CodingErrorAction.REPLACE) && (decoderReplacementValue != null)) {
            charsetDecoder.replaceWith(decoderReplacementValue);
        }
        return charsetDecoder;
    }

    protected final CharsetEncoder newCharsetEncoder(byte... encoderReplacementValue) {
        CharsetEncoder charsetEncoder = charset.newEncoder().onUnmappableCharacter(codingErrorAction);
        if ((codingErrorAction == CodingErrorAction.REPLACE) && (encoderReplacementValue != null)) {
            charsetEncoder.replaceWith(encoderReplacementValue);
        }
        return charsetEncoder;
    }

}
