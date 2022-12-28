package stexfires.util.function;

import stexfires.util.Strings;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongFunction;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @see java.math.BigInteger
 * @see java.lang.String
 * @see java.nio.charset.Charset
 * @see java.util.Base64
 * @see java.util.HexFormat
 * @see java.util.function.Function
 * @see java.util.function.IntFunction
 * @see java.util.function.LongFunction
 * @see java.util.function.Predicate
 * @since 0.1
 */
@SuppressWarnings("ReturnOfNull")
public final class ByteArrayFunctions {

    private ByteArrayFunctions() {
    }

    @SuppressWarnings("Convert2MethodRef")
    public static Predicate<byte[]> isNull() {
        return b -> b == null;
    }

    @SuppressWarnings("Convert2MethodRef")
    public static Predicate<byte[]> isNotNull() {
        return b -> b != null;
    }

    public static Predicate<byte[]> isNullOrEmpty() {
        return b -> b == null || b.length == 0;
    }

    public static Predicate<byte[]> isNotNullAndEmpty() {
        return b -> b != null && b.length == 0;
    }

    public static Predicate<byte[]> isNotNullAndNotEmpty() {
        return b -> b != null && b.length > 0;
    }

    public static Function<String, byte[]> fromStringStandard(Charset charset) {
        Objects.requireNonNull(charset);
        return s -> s == null ? null : s.getBytes(charset);
    }

    public static Function<String, byte[]> fromStringIgnoreErrors(Charset charset) {
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

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static Function<String, byte[]> fromStringReplaceErrors(Charset charset, byte[] newReplacement) {
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

    public static Function<String, byte[]> fromStringReplaceErrors(Charset charset, String newReplacement) {
        Objects.requireNonNull(charset);
        Objects.requireNonNull(newReplacement);

        return fromStringReplaceErrors(charset, newReplacement.getBytes(charset));
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static Function<String, byte[]> fromStringAlternativeForError(Charset charset, byte[] errorAlternative) {
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

    public static Function<String, byte[]> fromBase64(Base64.Decoder decoder) {
        Objects.requireNonNull(decoder);
        return s -> s == null ? null : decoder.decode(s);
    }

    public static Function<String, byte[]> fromHex(HexFormat hexFormat) {
        Objects.requireNonNull(hexFormat);
        return s -> s == null ? null : hexFormat.parseHex(s);
    }

    public static Function<String, byte[]> fromHex() {
        return fromHex(HexFormat.of());
    }

    public static IntFunction<byte[]> fromPrimitiveInt() {
        return n -> BigInteger.valueOf(n).toByteArray();
    }

    public static LongFunction<byte[]> fromPrimitiveLong() {
        return n -> BigInteger.valueOf(n).toByteArray();
    }

    public static Function<Integer, byte[]> fromInteger() {
        return n -> n == null ? null : BigInteger.valueOf(n).toByteArray();
    }

    public static Function<Long, byte[]> fromLong() {
        return n -> n == null ? null : BigInteger.valueOf(n).toByteArray();
    }

    public static Function<BigInteger, byte[]> fromBigInteger() {
        return n -> n == null ? null : n.toByteArray();
    }

    public static Function<byte[], String> toStringRepresentation() {
        return Arrays::toString;
    }

    public static Function<byte[], String> toStringStandard(Charset charset) {
        Objects.requireNonNull(charset);
        return b -> b == null ? null : new String(b, charset);
    }

    public static Function<byte[], String> toStringIgnoreErrors(Charset charset) {
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

    public static Function<byte[], String> toStringReplaceErrors(Charset charset, String newReplacement) {
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

    public static Function<byte[], String> toStringAlternativeForError(Charset charset, String errorAlternative) {
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

    public static Function<byte[], String> toBase64(Base64.Encoder encoder) {
        Objects.requireNonNull(encoder);
        return b -> b == null ? null : encoder.encodeToString(b);
    }

    public static Function<byte[], String> toHex(HexFormat hexFormat) {
        Objects.requireNonNull(hexFormat);
        return b -> b == null ? null : hexFormat.formatHex(b);
    }

    public static Function<byte[], String> toHex() {
        return toHex(HexFormat.of());
    }

    public static Function<byte[], Integer> toInteger() {
        return b -> b == null || b.length == 0 ? null : new BigInteger(b).intValueExact();
    }

    public static Function<byte[], Long> toLong() {
        return b -> b == null || b.length == 0 ? null : new BigInteger(b).longValueExact();
    }

    public static Function<byte[], BigInteger> toBigInteger() {
        return b -> b == null || b.length == 0 ? null : new BigInteger(b);
    }

}