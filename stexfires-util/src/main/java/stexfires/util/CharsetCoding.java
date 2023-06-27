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
 * This record contains a {@link Charset} and an enum {@link CodingErrors} with replacement strings for decoding and encoding.
 * The methods can be used to create new {@link CharsetDecoder}, {@link CharsetEncoder}, {@link InputStreamReader},
 * {@link OutputStreamWriter}, {@link BufferedReader} and {@link BufferedWriter} objects.
 * <p>
 * There are three constants for common {@code CharsetCoding} objects with an {@link StandardCharsets#UTF_8} {@link Charset}.
 * <p>
 * There are some static factory methods to create new {@code CharsetCoding} objects.
 * <p>
 * Both replacement values can be {@code null}, which means that the default value from Java is used.
 *
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

    /**
     * A constant for a {@code UTF-8} {@link CharsetCoding} object with {@link CodingErrors#IGNORE}.
     */
    public static final CharsetCoding UTF_8_IGNORING = ignoringErrors(StandardCharsets.UTF_8);
    /**
     * A constant for a {@code UTF-8} {@link CharsetCoding} object with {@link CodingErrors#REPLACE} and default replacements.
     */
    public static final CharsetCoding UTF_8_REPLACING = replacingErrorsWithDefaults(StandardCharsets.UTF_8);
    /**
     * A constant for a {@code UTF-8} {@link CharsetCoding} object with {@link CodingErrors#REPORT}.
     */
    public static final CharsetCoding UTF_8_REPORTING = reportingErrors(StandardCharsets.UTF_8);

    /**
     * Creates a new CharsetCoding object.
     *
     * @param charset            The {@link Charset} that is wrapped by this record. Must not be null.
     * @param codingErrors       Defines the {@link CodingErrorAction} for new {@link CharsetDecoder} and {@link CharsetEncoder}. Must not be null.
     * @param decoderReplacement The replacement for new {@link CharsetDecoder} if the action is set to {@link CodingErrors#REPLACE}. Can be null.
     * @param encoderReplacement The replacement for new {@link CharsetEncoder} if the action is set to {@link CodingErrors#REPLACE}. Can be null.
     */
    public CharsetCoding {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(codingErrors);
    }

    /**
     * Creates a new CharsetCoding object with the given {@link Charset} and {@link CodingErrors#IGNORE}.
     *
     * @param charset The {@link Charset} that is wrapped by this record. Must not be null.
     * @return a new CharsetCoding object
     */
    public static CharsetCoding ignoringErrors(@NotNull Charset charset) {
        return new CharsetCoding(charset, CodingErrors.IGNORE, null, null);
    }

    /**
     * Creates a new CharsetCoding object with the charset of the given {@link CommonCharsetNames} and {@link CodingErrors#IGNORE}.
     *
     * @param commonCharsetNames The {@link CommonCharsetNames} whose charset is wrapped by this record. Must not be null.
     * @return a new CharsetCoding object
     * @see CommonCharsetNames#charset()
     */
    public static CharsetCoding ignoringErrors(@NotNull CommonCharsetNames commonCharsetNames) {
        return ignoringErrors(commonCharsetNames.charset());
    }

    /**
     * Creates a new CharsetCoding object with the given {@link Charset} and {@link CodingErrors#REPLACE}.
     *
     * @param charset            The {@link Charset} that is wrapped by this record. Must not be null.
     * @param decoderReplacement The replacement for new {@link CharsetDecoder}. Can be null.
     * @param encoderReplacement The replacement for new {@link CharsetEncoder}. Can be null.
     * @return a new CharsetCoding object
     */
    public static CharsetCoding replacingErrors(@NotNull Charset charset,
                                                @Nullable String decoderReplacement,
                                                @Nullable String encoderReplacement) {
        return new CharsetCoding(charset,
                CodingErrors.REPLACE,
                decoderReplacement,
                encoderReplacement);
    }

    /**
     * Creates a new CharsetCoding object with the charset of the given {@link CommonCharsetNames} and {@link CodingErrors#REPLACE}.
     *
     * @param commonCharsetNames The {@link CommonCharsetNames} whose charset is wrapped by this record. Must not be null.
     * @param decoderReplacement The replacement for new {@link CharsetDecoder}. Can be null.
     * @param encoderReplacement The replacement for new {@link CharsetEncoder}. Can be null.
     * @return a new CharsetCoding object
     * @see CommonCharsetNames#charset()
     */
    public static CharsetCoding replacingErrors(@NotNull CommonCharsetNames commonCharsetNames,
                                                @Nullable String decoderReplacement,
                                                @Nullable String encoderReplacement) {
        return replacingErrors(commonCharsetNames.charset(),
                decoderReplacement,
                encoderReplacement);
    }

    /**
     * Creates a new CharsetCoding object with the given {@link Charset} and {@link CodingErrors#REPLACE} and default replacements.
     *
     * @param charset The {@link Charset} that is wrapped by this record. Must not be null.
     * @return a new CharsetCoding object
     */
    public static CharsetCoding replacingErrorsWithDefaults(@NotNull Charset charset) {
        return replacingErrors(charset,
                null,
                null);
    }

    /**
     * Creates a new CharsetCoding object with the charset of the given {@link CommonCharsetNames} and {@link CodingErrors#REPLACE} and default replacements.
     *
     * @param commonCharsetNames The {@link CommonCharsetNames} whose charset is wrapped by this record. Must not be null.
     * @return a new CharsetCoding object
     * @see CommonCharsetNames#charset()
     */
    public static CharsetCoding replacingErrorsWithDefaults(@NotNull CommonCharsetNames commonCharsetNames) {
        return replacingErrors(commonCharsetNames.charset(),
                null,
                null);
    }

    /**
     * Creates a new CharsetCoding object with the given {@link Charset} and {@link CodingErrors#REPORT}.
     *
     * @param charset The {@link Charset} that is wrapped by this record. Must not be null.
     * @return a new CharsetCoding object
     */
    public static CharsetCoding reportingErrors(@NotNull Charset charset) {
        return new CharsetCoding(charset, CodingErrors.REPORT, null, null);
    }

    /**
     * Creates a new CharsetCoding object with the charset of the given {@link CommonCharsetNames} and {@link CodingErrors#REPORT}.
     *
     * @param commonCharsetNames The {@link CommonCharsetNames} whose charset is wrapped by this record. Must not be null.
     * @return a new CharsetCoding object
     * @see CommonCharsetNames#charset()
     */
    public static CharsetCoding reportingErrors(@NotNull CommonCharsetNames commonCharsetNames) {
        return reportingErrors(commonCharsetNames.charset());
    }

    /**
     * Creates a new {@link CharsetDecoder} object and initializes it with the {@code CodingErrorAction} of {@code codingErrors}
     * and the {@code decoderReplacement}.
     *
     * @return a new {@link CharsetDecoder} object
     * @see java.nio.charset.CharsetDecoder#onMalformedInput(CodingErrorAction)
     * @see java.nio.charset.CharsetDecoder#onUnmappableCharacter(CodingErrorAction)
     * @see java.nio.charset.CharsetDecoder#replaceWith(String)
     */
    public CharsetDecoder newDecoder() {
        CharsetDecoder charsetDecoder = charset.newDecoder()
                                               .onMalformedInput(codingErrors.codingErrorAction())
                                               .onUnmappableCharacter(codingErrors.codingErrorAction());
        if ((decoderReplacement != null) && (codingErrors == CodingErrors.REPLACE)) {
            charsetDecoder.replaceWith(decoderReplacement);
        }
        return charsetDecoder;
    }

    /**
     * Creates a new {@link CharsetEncoder} object and initializes it with the {@code CodingErrorAction} of {@code codingErrors}
     * and the {@code encoderReplacement}.
     *
     * @return a new {@link CharsetEncoder} object
     * @see java.nio.charset.CharsetEncoder#onMalformedInput(CodingErrorAction)
     * @see java.nio.charset.CharsetEncoder#onUnmappableCharacter(CodingErrorAction)
     * @see java.nio.charset.CharsetEncoder#replaceWith(byte[])
     */
    public CharsetEncoder newEncoder() {
        CharsetEncoder charsetEncoder = charset.newEncoder()
                                               .onMalformedInput(codingErrors.codingErrorAction())
                                               .onUnmappableCharacter(codingErrors.codingErrorAction());
        if ((encoderReplacement != null) && (codingErrors == CodingErrors.REPLACE)) {
            charsetEncoder.replaceWith(encoderReplacement.getBytes(charset));
        }
        return charsetEncoder;
    }

    /**
     * Creates a new {@link InputStreamReader} object for the given {@link InputStream} and initializes it with a new {@link CharsetDecoder}.
     *
     * @param inputStream The {@link InputStream} that is wrapped by the new {@link InputStreamReader} object. Must not be null.
     * @return a new {@link InputStreamReader} object
     * @see #newDecoder()
     * @see InputStreamReader#InputStreamReader(InputStream, CharsetDecoder)
     */
    public InputStreamReader newInputStreamReader(@NotNull InputStream inputStream) {
        Objects.requireNonNull(inputStream);
        return new InputStreamReader(inputStream, newDecoder());
    }

    /**
     * Creates a new {@link OutputStreamWriter} object for the given {@link OutputStream} and initializes it with a new {@link CharsetEncoder}.
     *
     * @param outputStream The {@link OutputStream} that is wrapped by the new {@link OutputStreamWriter} object. Must not be null.
     * @return a new {@link OutputStreamWriter} object
     * @see #newEncoder()
     * @see OutputStreamWriter#OutputStreamWriter(OutputStream, CharsetEncoder)
     */
    public OutputStreamWriter newOutputStreamWriter(@NotNull OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        return new OutputStreamWriter(outputStream, newEncoder());
    }

    /**
     * Creates a new {@link BufferedReader} object that wraps a new {@link InputStreamReader} object for the given {@link InputStream}.
     *
     * @param inputStream The {@link InputStream} that is wrapped by the new {@link BufferedReader} object. Must not be null.
     * @return a new {@link BufferedReader} object
     * @see #newInputStreamReader(InputStream)
     */
    public BufferedReader newBufferedReader(@NotNull InputStream inputStream) {
        Objects.requireNonNull(inputStream);
        return new BufferedReader(newInputStreamReader(inputStream));
    }

    /**
     * Creates a new {@link BufferedWriter} object that wraps a new {@link OutputStreamWriter} object for the given {@link OutputStream}.
     *
     * @param outputStream The {@link OutputStream} that is wrapped by the new {@link BufferedWriter} object. Must not be null.
     * @return a new {@link BufferedWriter} object
     * @see #newOutputStreamWriter(OutputStream)
     */
    public BufferedWriter newBufferedWriter(@NotNull OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        return new BufferedWriter(newOutputStreamWriter(outputStream));
    }

    /**
     * This enum contains the three possible {@link CodingErrorAction} values.
     * It determines how malformed-input and unmappable-character errors are handled.
     */
    public enum CodingErrors {

        /**
         * Is equivalent to {@link CodingErrorAction#IGNORE} and means that
         * the malformed-input and unmappable-character errors are ignored.
         */
        IGNORE,

        /**
         * Is equivalent to {@link CodingErrorAction#REPLACE} and means that
         * the malformed-input and unmappable-character errors are replaced.
         */
        REPLACE,

        /**
         * Is equivalent to {@link CodingErrorAction#REPORT} and means that
         * the malformed-input and unmappable-character errors are reported.
         */
        REPORT;

        /**
         * Returns the corresponding {@link CodingErrorAction} for this enum value.
         *
         * @return {@link CodingErrorAction} for this enum value
         */
        public final CodingErrorAction codingErrorAction() {
            return switch (this) {
                case IGNORE -> CodingErrorAction.IGNORE;
                case REPLACE -> CodingErrorAction.REPLACE;
                case REPORT -> CodingErrorAction.REPORT;
            };
        }

    }

}
