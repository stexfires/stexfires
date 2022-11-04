package stexfires.io;

import org.jetbrains.annotations.Nullable;
import stexfires.util.LineSeparator;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class BaseRecordFileSpec implements RecordFileSpec {

    private final Charset charset;
    private final CodingErrorAction codingErrorAction;
    private final String decoderReplacement;
    private final byte[] encoderReplacement;
    private final LineSeparator lineSeparator;

    public BaseRecordFileSpec(Charset charset, CodingErrorAction codingErrorAction,
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

    @Override
    public final Charset charset() {
        return charset;
    }

    @Override
    public final CodingErrorAction codingErrorAction() {
        return codingErrorAction;
    }

    @Override
    public final LineSeparator lineSeparator() {
        return lineSeparator;
    }

    @Override
    public final CharsetDecoder newCharsetDecoder() {
        CharsetDecoder charsetDecoder = charset.newDecoder().onMalformedInput(codingErrorAction);
        if (decoderReplacement != null) {
            charsetDecoder.replaceWith(decoderReplacement);
        }
        return charsetDecoder;
    }

    @Override
    public final CharsetEncoder newCharsetEncoder() {
        CharsetEncoder charsetEncoder = charset.newEncoder().onUnmappableCharacter(codingErrorAction);
        if (encoderReplacement != null) {
            charsetEncoder.replaceWith(encoderReplacement);
        }
        return charsetEncoder;
    }

}
