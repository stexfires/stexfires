package stexfires.util.function;

import org.jspecify.annotations.Nullable;
import stexfires.util.Strings;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
import java.util.*;
import java.util.function.*;
import java.util.zip.*;

/**
 * @see java.math.BigInteger
 * @see java.lang.String
 * @see java.nio.charset.Charset
 * @see java.util.Base64
 * @see java.util.HexFormat
 * @see java.util.function.Function
 * @see java.util.function.IntFunction
 * @see java.util.function.LongFunction
 * @see java.util.function.Predicate
 * @see java.util.function.UnaryOperator
 * @see java.util.zip.GZIPInputStream
 * @see java.util.zip.GZIPOutputStream
 * @since 0.1
 */
@SuppressWarnings("AssignmentToNull")
public final class ByteArrayFunctions {

    private ByteArrayFunctions() {
    }

    @SuppressWarnings("Convert2MethodRef")
    public static Predicate<byte @Nullable []> isNull() {
        return b -> b == null;
    }

    @SuppressWarnings("Convert2MethodRef")
    public static Predicate<byte @Nullable []> isNotNull() {
        return b -> b != null;
    }

    public static Predicate<byte @Nullable []> isNullOrEmpty() {
        return b -> (b == null) || (b.length == 0);
    }

    public static Predicate<byte @Nullable []> isNotNullAndEmpty() {
        return b -> (b != null) && (b.length == 0);
    }

    public static Predicate<byte @Nullable []> isNotNullAndNotEmpty() {
        return b -> (b != null) && (b.length > 0);
    }

    public static Function<@Nullable String, byte @Nullable []> fromStringStandard(Charset charset) {
        Objects.requireNonNull(charset);
        return s -> (s == null) ? null : s.getBytes(charset);
    }

    public static Function<@Nullable String, byte @Nullable []> fromStringIgnoreErrors(Charset charset) {
        Objects.requireNonNull(charset);

        CharsetEncoder charsetEncoder = charset.newEncoder()
                                               .onMalformedInput(CodingErrorAction.IGNORE)
                                               .onUnmappableCharacter(CodingErrorAction.IGNORE);
        return s -> {
            byte[] result;
            if (s == null) {
                result = null;
            } else if (s.isEmpty()) {
                result = new byte[0];
            } else {
                try {
                    ByteBuffer byteBuffer = charsetEncoder.encode(CharBuffer.wrap(s));
                    result = new byte[byteBuffer.limit()];
                    byteBuffer.get(result);
                } catch (CharacterCodingException e) {
                    // Should never happen
                    result = null;
                }
            }
            return result;
        };
    }

    public static Function<@Nullable String, byte @Nullable []> fromStringReplaceErrors(Charset charset, byte[] newReplacement) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(newReplacement);

        CharsetEncoder charsetEncoder = charset.newEncoder()
                                               .onMalformedInput(CodingErrorAction.REPLACE)
                                               .onUnmappableCharacter(CodingErrorAction.REPLACE)
                                               .replaceWith(newReplacement);
        return s -> {
            byte[] result;
            if (s == null) {
                result = null;
            } else if (s.isEmpty()) {
                result = new byte[0];
            } else {
                try {
                    ByteBuffer byteBuffer = charsetEncoder.encode(CharBuffer.wrap(s));
                    result = new byte[byteBuffer.limit()];
                    byteBuffer.get(result);
                } catch (CharacterCodingException e) {
                    // Should never happen
                    result = null;
                }
            }
            return result;
        };
    }

    public static Function<@Nullable String, byte @Nullable []> fromStringReplaceErrors(Charset charset, String newReplacement) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(newReplacement);

        return fromStringReplaceErrors(charset, newReplacement.getBytes(charset));
    }

    public static Function<@Nullable String, byte @Nullable []> fromStringAlternativeForError(Charset charset, byte[] errorAlternative) {
        Objects.requireNonNull(charset);

        CharsetEncoder charsetEncoder = charset.newEncoder()
                                               .onMalformedInput(CodingErrorAction.REPORT)
                                               .onUnmappableCharacter(CodingErrorAction.REPORT);
        return s -> {
            byte[] result;
            if (s == null) {
                result = null;
            } else if (s.isEmpty()) {
                result = new byte[0];
            } else {
                try {
                    ByteBuffer byteBuffer = charsetEncoder.encode(CharBuffer.wrap(s));
                    result = new byte[byteBuffer.limit()];
                    byteBuffer.get(result);
                } catch (CharacterCodingException e) {
                    // Catch any exception and replace the complete result with the alternative.
                    result = errorAlternative;
                }
            }
            return result;
        };
    }

    public static Function<@Nullable String, byte @Nullable []> fromBase64(Base64.Decoder decoder) {
        Objects.requireNonNull(decoder);
        return s -> (s == null) ? null : decoder.decode(s);
    }

    public static Function<@Nullable String, byte @Nullable []> fromHex(HexFormat hexFormat) {
        Objects.requireNonNull(hexFormat);
        return s -> (s == null) ? null : hexFormat.parseHex(s);
    }

    public static Function<@Nullable String, byte @Nullable []> fromHex() {
        return fromHex(HexFormat.of());
    }

    public static IntFunction<byte[]> fromPrimitiveInt() {
        return n -> BigInteger.valueOf(n).toByteArray();
    }

    public static LongFunction<byte[]> fromPrimitiveLong() {
        return n -> BigInteger.valueOf(n).toByteArray();
    }

    public static Function<@Nullable Integer, byte @Nullable []> fromInteger() {
        return n -> (n == null) ? null : BigInteger.valueOf(n).toByteArray();
    }

    public static Function<@Nullable Long, byte @Nullable []> fromLong() {
        return n -> (n == null) ? null : BigInteger.valueOf(n).toByteArray();
    }

    public static Function<@Nullable BigInteger, byte @Nullable []> fromBigInteger() {
        return n -> (n == null) ? null : n.toByteArray();
    }

    public static Function<byte @Nullable [], @Nullable String> toStringRepresentation() {
        return Arrays::toString;
    }

    public static Function<byte @Nullable [], @Nullable String> toStringStandard(Charset charset) {
        Objects.requireNonNull(charset);
        return b -> (b == null) ? null : new String(b, charset);
    }

    public static Function<byte @Nullable [], @Nullable String> toStringIgnoreErrors(Charset charset) {
        Objects.requireNonNull(charset);

        CharsetDecoder charsetDecoder = charset.newDecoder()
                                               .onMalformedInput(CodingErrorAction.IGNORE)
                                               .onUnmappableCharacter(CodingErrorAction.IGNORE);
        return b -> {
            String result;
            if (b == null) {
                result = null;
            } else if (b.length == 0) {
                result = Strings.EMPTY;
            } else {
                try {
                    result = charsetDecoder.decode(ByteBuffer.wrap(b)).toString();
                } catch (CharacterCodingException e) {
                    // Should never happen
                    result = null;
                }
            }
            return result;
        };
    }

    public static Function<byte @Nullable [], @Nullable String> toStringReplaceErrors(Charset charset, String newReplacement) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(newReplacement);

        CharsetDecoder charsetDecoder = charset.newDecoder()
                                               .onMalformedInput(CodingErrorAction.REPLACE)
                                               .onUnmappableCharacter(CodingErrorAction.REPLACE)
                                               .replaceWith(newReplacement);
        return b -> {
            String result;
            if (b == null) {
                result = null;
            } else if (b.length == 0) {
                result = Strings.EMPTY;
            } else {
                try {
                    result = charsetDecoder.decode(ByteBuffer.wrap(b)).toString();
                } catch (CharacterCodingException e) {
                    // Should never happen
                    result = null;
                }
            }
            return result;
        };
    }

    public static Function<byte @Nullable [], @Nullable String> toStringAlternativeForError(Charset charset, String errorAlternative) {
        Objects.requireNonNull(charset);

        CharsetDecoder charsetDecoder = charset.newDecoder()
                                               .onMalformedInput(CodingErrorAction.REPORT)
                                               .onUnmappableCharacter(CodingErrorAction.REPORT);
        return b -> {
            String result;
            if (b == null) {
                result = null;
            } else if (b.length == 0) {
                result = Strings.EMPTY;
            } else {
                try {
                    result = charsetDecoder.decode(ByteBuffer.wrap(b)).toString();
                } catch (CharacterCodingException e) {
                    // Catch any exception and replace the complete result with the alternative.
                    result = errorAlternative;
                }
            }
            return result;
        };
    }

    public static Function<byte @Nullable [], @Nullable String> toBase64(Base64.Encoder encoder) {
        Objects.requireNonNull(encoder);
        return b -> (b == null) ? null : encoder.encodeToString(b);
    }

    public static Function<byte @Nullable [], @Nullable String> toHex(HexFormat hexFormat) {
        Objects.requireNonNull(hexFormat);
        return b -> (b == null) ? null : hexFormat.formatHex(b);
    }

    public static Function<byte @Nullable [], @Nullable String> toHex() {
        return toHex(HexFormat.of());
    }

    public static Function<byte @Nullable [], @Nullable Integer> toInteger() {
        return b -> ((b == null) || (b.length == 0)) ? null : new BigInteger(b).intValueExact();
    }

    public static Function<byte @Nullable [], @Nullable Long> toLong() {
        return b -> ((b == null) || (b.length == 0)) ? null : new BigInteger(b).longValueExact();
    }

    public static Function<byte @Nullable [], @Nullable BigInteger> toBigInteger() {
        return b -> ((b == null) || (b.length == 0)) ? null : new BigInteger(b);
    }

    public static UnaryOperator<byte @Nullable []> encodeBase64(Base64.Encoder encoder) {
        Objects.requireNonNull(encoder);
        return b -> (b == null) ? null : encoder.encode(b);
    }

    public static UnaryOperator<byte @Nullable []> decodeBase64(Base64.Decoder decoder) {
        Objects.requireNonNull(decoder);
        return b -> (b == null) ? null : decoder.decode(b);
    }

    public static UnaryOperator<byte @Nullable []> compressGZIP() {
        return b -> {
            byte[] result;
            if (b == null) {
                result = null;
            } else {
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                try (GZIPOutputStream gzipStream = new GZIPOutputStream(byteStream)) {
                    gzipStream.write(b);
                } catch (IOException e) {
                    // Should not normally occur.
                    throw new UncheckedIOException("Error occurred while compressing data.", e);
                }
                // 'toByteArray' should be called after closing GZIPOutputStream
                result = byteStream.toByteArray();
            }
            return result;
        };
    }

    public static UnaryOperator<byte @Nullable []> decompressGZIP() {
        return b -> {
            byte[] result;
            if (b == null) {
                result = null;
            } else {
                ByteArrayInputStream byteStream = new ByteArrayInputStream(b);
                try (GZIPInputStream gzipStream = new GZIPInputStream(byteStream)) {
                    result = gzipStream.readAllBytes();
                } catch (IOException e) {
                    throw new UncheckedIOException("Error occurred while decompressing data.", e);
                }
            }
            return result;
        };
    }

}
