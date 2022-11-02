package stexfires.io.spec;

import org.jetbrains.annotations.Nullable;
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
    private final String decoderReplacement;
    private final byte[] encoderReplacement;
    private final LineSeparator lineSeparator;

    protected AbstractRecordFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                                     LineSeparator lineSeparator) {
        this(charset, codingErrorAction, null, null, lineSeparator);
    }

    protected AbstractRecordFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                                     @Nullable String decoderReplacement, @Nullable String encoderReplacement,
                                     LineSeparator lineSeparator) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(codingErrorAction);
        Objects.requireNonNull(lineSeparator);
        this.charset = charset;
        this.codingErrorAction = codingErrorAction;
        this.decoderReplacement = (codingErrorAction == CodingErrorAction.REPLACE
                && decoderReplacement != null && !decoderReplacement.isEmpty()) ? decoderReplacement : null;
        this.encoderReplacement = (codingErrorAction == CodingErrorAction.REPLACE
                && encoderReplacement != null && !encoderReplacement.isEmpty()) ? encoderReplacement.getBytes(charset) : null;
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

    protected final BufferedWriter newBufferedWriter(OutputStream outputStream) {
        return new BufferedWriter(new OutputStreamWriter(outputStream, newCharsetEncoder()));
    }

    protected final CharsetDecoder newCharsetDecoder() {
        CharsetDecoder charsetDecoder = charset.newDecoder().onMalformedInput(codingErrorAction);
        if (decoderReplacement != null) {
            charsetDecoder.replaceWith(decoderReplacement);
        }
        return charsetDecoder;
    }

    protected final CharsetEncoder newCharsetEncoder() {
        CharsetEncoder charsetEncoder = charset.newEncoder().onUnmappableCharacter(codingErrorAction);
        if (encoderReplacement != null) {
            charsetEncoder.replaceWith(encoderReplacement);
        }
        return charsetEncoder;
    }

}
