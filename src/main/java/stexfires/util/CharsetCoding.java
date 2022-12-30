package stexfires.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @see java.nio.charset.Charset
 * @see java.nio.charset.CodingErrorAction
 * @see java.nio.charset.CharsetDecoder
 * @see java.nio.charset.CharsetEncoder
 * @see stexfires.util.CommonCharsetNames
 * @since 0.1
 */
public record CharsetCoding(@NotNull Charset charset,
                            @NotNull CodingErrors codingErrors,
                            @Nullable String decoderReplacement,
                            @Nullable String encoderReplacement) {

    public static final CharsetCoding UTF_8_IGNORING = ignoringErrors(StandardCharsets.UTF_8);
    public static final CharsetCoding UTF_8_REPLACING = replacingErrorsWithDefaults(StandardCharsets.UTF_8);
    public static final CharsetCoding UTF_8_REPORTING = reportingErrors(StandardCharsets.UTF_8);

    public CharsetCoding {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(codingErrors);
    }

    public static CharsetCoding ignoringErrors(@NotNull Charset charset) {
        return new CharsetCoding(charset, CodingErrors.IGNORE, null, null);
    }

    public static CharsetCoding ignoringErrors(@NotNull CommonCharsetNames commonCharsetNames) {
        return ignoringErrors(commonCharsetNames.charset());
    }

    public static CharsetCoding reportingErrors(@NotNull Charset charset) {
        return new CharsetCoding(charset, CodingErrors.REPORT, null, null);
    }

    public static CharsetCoding reportingErrors(@NotNull CommonCharsetNames commonCharsetNames) {
        return reportingErrors(commonCharsetNames.charset());
    }

    public static CharsetCoding replacingErrors(@NotNull Charset charset,
                                                @Nullable String decoderReplacement,
                                                @Nullable String encoderReplacement) {
        return new CharsetCoding(charset,
                CodingErrors.REPLACE,
                decoderReplacement,
                encoderReplacement);
    }

    public static CharsetCoding replacingErrors(@NotNull CommonCharsetNames commonCharsetNames,
                                                @Nullable String decoderReplacement,
                                                @Nullable String encoderReplacement) {
        return replacingErrors(commonCharsetNames.charset(),
                decoderReplacement,
                encoderReplacement);
    }

    public static CharsetCoding replacingErrorsWithDefaults(@NotNull Charset charset) {
        return replacingErrors(charset,
                null,
                null);
    }

    public static CharsetCoding replacingErrorsWithDefaults(@NotNull CommonCharsetNames commonCharsetNames) {
        return replacingErrors(commonCharsetNames.charset(),
                null,
                null);
    }

    public CharsetDecoder newDecoder() {
        CharsetDecoder charsetDecoder = charset.newDecoder()
                                               .onMalformedInput(codingErrors.codingErrorAction())
                                               .onUnmappableCharacter(codingErrors.codingErrorAction());
        if ((decoderReplacement != null) && (codingErrors == CodingErrors.REPLACE)) {
            charsetDecoder.replaceWith(decoderReplacement);
        }
        return charsetDecoder;
    }

    public CharsetEncoder newEncoder() {
        CharsetEncoder charsetEncoder = charset.newEncoder()
                                               .onMalformedInput(codingErrors.codingErrorAction())
                                               .onUnmappableCharacter(codingErrors.codingErrorAction());
        if ((encoderReplacement != null) && (codingErrors == CodingErrors.REPLACE)) {
            charsetEncoder.replaceWith(encoderReplacement.getBytes(charset));
        }
        return charsetEncoder;
    }

    public InputStreamReader newInputStreamReader(@NotNull InputStream inputStream) {
        Objects.requireNonNull(inputStream);
        return new InputStreamReader(inputStream, newDecoder());
    }

    public OutputStreamWriter newOutputStreamWriter(@NotNull OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        return new OutputStreamWriter(outputStream, newEncoder());
    }

    public BufferedReader newBufferedReader(@NotNull InputStream inputStream) {
        Objects.requireNonNull(inputStream);
        return new BufferedReader(newInputStreamReader(inputStream));
    }

    public BufferedWriter newBufferedWriter(@NotNull OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        return new BufferedWriter(newOutputStreamWriter(outputStream));
    }

    public enum CodingErrors {
        IGNORE, REPLACE, REPORT;

        public final CodingErrorAction codingErrorAction() {
            return switch (this) {
                case IGNORE -> CodingErrorAction.IGNORE;
                case REPLACE -> CodingErrorAction.REPLACE;
                case REPORT -> CodingErrorAction.REPORT;
            };
        }

    }

}
