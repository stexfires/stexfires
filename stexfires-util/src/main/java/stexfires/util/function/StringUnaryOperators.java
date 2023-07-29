package stexfires.util.function;

import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.Base64;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static stexfires.util.Strings.EMPTY;
import static stexfires.util.Strings.REGEX_TAB;
import static stexfires.util.Strings.REGEX_WHITESPACE;

/**
 * @see java.lang.String
 * @see java.lang.Character
 * @see java.lang.StringBuilder
 * @see java.text.Normalizer
 * @see java.util.function.Function
 * @see java.util.function.UnaryOperator
 * @see stexfires.util.Strings
 * @since 0.1
 */
public final class StringUnaryOperators {

    private StringUnaryOperators() {
    }

    public static UnaryOperator<String> of(Function<String, String> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    public static UnaryOperator<String> concat(UnaryOperator<String> first, UnaryOperator<String> second) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        return (s) -> second.apply(first.apply(s));
    }

    public static UnaryOperator<String> concat(UnaryOperator<String> first, UnaryOperator<String> second, UnaryOperator<String> third) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(third);
        return (s) -> third.apply(second.apply(first.apply(s)));
    }

    public static UnaryOperator<String> conditionalOperator(Predicate<String> condition, UnaryOperator<String> trueOperator, UnaryOperator<String> falseOperator) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueOperator);
        Objects.requireNonNull(falseOperator);
        return (s) -> condition.test(s) ? trueOperator.apply(s) : falseOperator.apply(s);
    }

    private static boolean nullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static UnaryOperator<String> identity() {
        return s -> s;
    }

    public static UnaryOperator<String> constant(String constant) {
        return s -> constant;
    }

    public static UnaryOperator<String> duplicate() {
        return s -> nullOrEmpty(s) ? s : s.concat(s);
    }

    public static UnaryOperator<String> reverse() {
        return s -> nullOrEmpty(s) ? s : new StringBuilder(s).reverse().toString();
    }

    @SuppressWarnings("ReturnOfNull")
    public static UnaryOperator<String> toNull() {
        return s -> null;
    }

    public static UnaryOperator<String> toEmpty() {
        return s -> EMPTY;
    }

    @SuppressWarnings("ReturnOfNull")
    public static UnaryOperator<String> emptyToNull() {
        return s -> nullOrEmpty(s) ? null : s;
    }

    public static UnaryOperator<String> nullToEmpty() {
        return s -> nullOrEmpty(s) ? EMPTY : s;
    }

    public static UnaryOperator<String> nullToConstant(String constant) {
        return s -> s == null ? constant : s;
    }

    @SuppressWarnings("ReturnOfNull")
    public static UnaryOperator<String> trimToNull() {
        return s -> nullOrEmpty(s) ? null : emptyToNull().apply(s.trim());
    }

    public static UnaryOperator<String> trimToEmpty() {
        return s -> nullOrEmpty(s) ? EMPTY : s.trim();
    }

    public static UnaryOperator<String> strip() {
        return s -> nullOrEmpty(s) ? s : s.strip();
    }

    public static UnaryOperator<String> stripIndent() {
        return s -> nullOrEmpty(s) ? s : s.stripIndent();
    }

    public static UnaryOperator<String> stripTrailing() {
        return s -> nullOrEmpty(s) ? s : s.stripTrailing();
    }

    public static UnaryOperator<String> stripLeading() {
        return s -> nullOrEmpty(s) ? s : s.stripLeading();
    }

    public static UnaryOperator<String> translateEscapes() {
        return s -> nullOrEmpty(s) ? s : s.translateEscapes();
    }

    public static UnaryOperator<String> removeHorizontalWhitespaces() {
        return replaceAll("\\h", EMPTY);
    }

    public static UnaryOperator<String> removeWhitespaces() {
        return replaceAll(REGEX_WHITESPACE, EMPTY);
    }

    public static UnaryOperator<String> removeVerticalWhitespaces() {
        return replaceAll("\\v", EMPTY);
    }

    public static UnaryOperator<String> removeLeadingHorizontalWhitespaces() {
        return replaceFirst("^\\h+", EMPTY);
    }

    public static UnaryOperator<String> removeLeadingWhitespaces() {
        return replaceFirst("^\\s+", EMPTY);
    }

    public static UnaryOperator<String> removeLeadingVerticalWhitespaces() {
        return replaceFirst("^\\v+", EMPTY);
    }

    public static UnaryOperator<String> removeTrailingHorizontalWhitespaces() {
        return replaceFirst("\\h+$", EMPTY);
    }

    public static UnaryOperator<String> removeTrailingWhitespaces() {
        return replaceFirst("\\s+$", EMPTY);
    }

    public static UnaryOperator<String> removeTrailingVerticalWhitespaces() {
        return replaceFirst("\\v+$", EMPTY);
    }

    public static UnaryOperator<String> tabToSpace() {
        return replaceAll(REGEX_TAB, " ");
    }

    public static UnaryOperator<String> tabToSpaces2() {
        return replaceAll(REGEX_TAB, "  ");
    }

    public static UnaryOperator<String> tabToSpaces4() {
        return replaceAll(REGEX_TAB, "    ");
    }

    public static UnaryOperator<String> tabToSpaces8() {
        return replaceAll(REGEX_TAB, "        ");
    }

    public static UnaryOperator<String> normalizeNFD() {
        return s -> nullOrEmpty(s) ? s : Normalizer.normalize(s, Normalizer.Form.NFD);
    }

    public static UnaryOperator<String> normalizeNFC() {
        return s -> nullOrEmpty(s) ? s : Normalizer.normalize(s, Normalizer.Form.NFC);
    }

    public static UnaryOperator<String> normalizeNFKD() {
        return s -> nullOrEmpty(s) ? s : Normalizer.normalize(s, Normalizer.Form.NFKD);
    }

    public static UnaryOperator<String> normalizeNFKC() {
        return s -> nullOrEmpty(s) ? s : Normalizer.normalize(s, Normalizer.Form.NFKC);
    }

    public static UnaryOperator<String> lengthAsString() {
        return s -> nullOrEmpty(s) ? "0" : String.valueOf(s.length());
    }

    public static UnaryOperator<String> lowerCase(Locale locale) {
        Objects.requireNonNull(locale);
        return s -> nullOrEmpty(s) ? s : s.toLowerCase(locale);
    }

    public static UnaryOperator<String> upperCase(Locale locale) {
        Objects.requireNonNull(locale);
        return s -> nullOrEmpty(s) ? s : s.toUpperCase(locale);
    }

    public static UnaryOperator<String> repeat(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count is negative: " + count);
        }
        return s -> nullOrEmpty(s) ? s : s.repeat(count);
    }

    public static UnaryOperator<String> replaceAll(String regex, String replacement) {
        Objects.requireNonNull(regex);
        Objects.requireNonNull(replacement);
        return s -> nullOrEmpty(s) ? s : s.replaceAll(regex, replacement);
    }

    public static UnaryOperator<String> replaceFirst(String regex, String replacement) {
        Objects.requireNonNull(regex);
        Objects.requireNonNull(replacement);
        return s -> nullOrEmpty(s) ? s : s.replaceFirst(regex, replacement);
    }

    public static UnaryOperator<String> removeAll(String regex) {
        Objects.requireNonNull(regex);
        return replaceAll(regex, EMPTY);
    }

    public static UnaryOperator<String> removeFirst(String regex) {
        Objects.requireNonNull(regex);
        return replaceFirst(regex, EMPTY);
    }

    public static UnaryOperator<String> indent(int n) {
        return s -> nullOrEmpty(s) ? s : s.indent(n);
    }

    public static UnaryOperator<String> prefix(String prefix) {
        Objects.requireNonNull(prefix);
        return s -> nullOrEmpty(s) ? prefix : prefix.concat(s);
    }

    public static UnaryOperator<String> suffix(String suffix) {
        Objects.requireNonNull(suffix);
        return s -> nullOrEmpty(s) ? suffix : s.concat(suffix);
    }

    public static UnaryOperator<String> surround(String prefix, String suffix) {
        Objects.requireNonNull(prefix);
        Objects.requireNonNull(suffix);
        return s -> nullOrEmpty(s) ? prefix.concat(suffix) : prefix.concat(s).concat(suffix);
    }

    public static UnaryOperator<String> supplier(Supplier<String> supplier) {
        Objects.requireNonNull(supplier);
        return s -> supplier.get();
    }

    public static UnaryOperator<String> codePointAt(int index, @Nullable String alternative) {
        return s -> (nullOrEmpty(s) || index < 0 || index >= s.length()) ? alternative : Character.toString(s.codePointAt(index));
    }

    public static UnaryOperator<String> codePointAt(int index, Supplier<String> alternative) {
        Objects.requireNonNull(alternative);
        return s -> (nullOrEmpty(s) || index < 0 || index >= s.length()) ? alternative.get() : Character.toString(s.codePointAt(index));
    }

    public static UnaryOperator<String> charAt(int index, @Nullable String alternative) {
        return s -> (nullOrEmpty(s) || index < 0 || index >= s.length()) ? alternative : String.valueOf(s.charAt(index));
    }

    public static UnaryOperator<String> charAt(int index, Supplier<String> alternative) {
        Objects.requireNonNull(alternative);
        return s -> (nullOrEmpty(s) || index < 0 || index >= s.length()) ? alternative.get() : String.valueOf(s.charAt(index));
    }

    public static UnaryOperator<String> formattedWithArguments(@Nullable Locale locale, Object... args) {
        return s -> nullOrEmpty(s) ? s : String.format(locale, s, args);
    }

    public static UnaryOperator<String> formatAsArgument(@Nullable Locale locale, String format) {
        Objects.requireNonNull(format);
        return s -> String.format(locale, format, s);
    }

    public static UnaryOperator<String> substring(int beginIndex) {
        if (beginIndex < 0) throw new IllegalArgumentException("beginIndex is negative: " + beginIndex);

        return s -> nullOrEmpty(s) ? s : s.substring(Math.min(s.length(), beginIndex));
    }

    public static UnaryOperator<String> substring(int beginIndex, int endIndex) {
        if (beginIndex < 0) throw new IllegalArgumentException("beginIndex is negative: " + beginIndex);
        if (beginIndex > endIndex)
            throw new IllegalArgumentException("beginIndex is larger than endIndex: " + beginIndex + " " + endIndex);

        return s -> nullOrEmpty(s) ? s : s.substring(Math.min(s.length(), beginIndex), Math.min(s.length(), endIndex));
    }

    public static UnaryOperator<String> removeFromStart(int length) {
        if (length < 0) throw new IllegalArgumentException("length is negative: " + length);

        return substring(length);
    }

    public static UnaryOperator<String> removeFromEnd(int length) {
        if (length < 0) throw new IllegalArgumentException("length is negative: " + length);

        return s -> nullOrEmpty(s) ? s : s.substring(0, Math.max(0, s.length() - length));
    }

    public static UnaryOperator<String> removeStringFromStart(String string) {
        Objects.requireNonNull(string);
        return s -> nullOrEmpty(s) || !s.startsWith(string) ? s : removeFromStart(string.length()).apply(s);
    }

    public static UnaryOperator<String> removeStringFromEnd(String string) {
        Objects.requireNonNull(string);
        return s -> nullOrEmpty(s) || !s.endsWith(string) ? s : removeFromEnd(string.length()).apply(s);
    }

    public static UnaryOperator<String> keepFromStart(int length) {
        if (length < 0) throw new IllegalArgumentException("length is negative: " + length);

        return substring(0, length);
    }

    public static UnaryOperator<String> keepFromEnd(int length) {
        if (length < 0) throw new IllegalArgumentException("length is negative: " + length);

        return s -> nullOrEmpty(s) ? s : s.substring(Math.max(0, s.length() - length));
    }

    public static UnaryOperator<String> padStart(String character, int length) {
        if (nullOrEmpty(character)) {
            throw new IllegalArgumentException("character is null or empty:" + character);
        }
        if (length < 0) throw new IllegalArgumentException("length is negative: " + length);

        return s -> {
            String result;
            if (nullOrEmpty(s)) {
                if (length % character.length() == 0) {
                    result = character.repeat(length / character.length());
                } else {
                    result = character.repeat(length / character.length() + 1).substring(character.length() - (length % character.length()));
                }
            } else if (s.length() < length) {
                int padLength = length - s.length();
                if (padLength % character.length() == 0) {
                    result = character.repeat(padLength / character.length()).concat(s);
                } else {
                    result = character.repeat(padLength / character.length() + 1).substring(character.length() - (padLength % character.length())).concat(s);
                }
            } else {
                result = s;
            }
            return result;
        };
    }

    public static UnaryOperator<String> padEnd(String character, int length) {
        if (nullOrEmpty(character)) {
            throw new IllegalArgumentException("character is null or empty:" + character);
        }
        if (length < 0) throw new IllegalArgumentException("length is negative: " + length);
        return s -> {
            String result;
            if (nullOrEmpty(s)) {
                if (length % character.length() == 0) {
                    result = character.repeat(length / character.length());
                } else {
                    result = character.repeat(length / character.length() + 1).substring(0, length);
                }
            } else if (s.length() < length) {
                int padLength = length - s.length();
                if (padLength % character.length() == 0) {
                    result = s.concat(character.repeat(padLength / character.length()));
                } else {
                    result = s.concat(character.repeat(padLength / character.length() + 1).substring(0, padLength));
                }
            } else {
                result = s;
            }
            return result;
        };
    }

    public static UnaryOperator<String> encodeBase64(Base64.Encoder encoder, Charset charset) {
        Objects.requireNonNull(encoder);
        Objects.requireNonNull(charset);
        return s -> nullOrEmpty(s) ? s : encoder.encodeToString(s.getBytes(charset));
    }

    public static UnaryOperator<String> decodeBase64(Base64.Decoder decoder, Charset charset) {
        Objects.requireNonNull(decoder);
        Objects.requireNonNull(charset);
        return s -> nullOrEmpty(s) ? s : new String(decoder.decode(s), charset);
    }

    public static UnaryOperator<String> splitCollect(Function<? super String, Stream<String>> splitStringFunction,
                                                     Collector<? super String, ?, String> stringCollector) {
        Objects.requireNonNull(splitStringFunction);
        Objects.requireNonNull(stringCollector);
        return s -> nullOrEmpty(s) ? s :
                splitStringFunction.apply(s)
                                   .collect(stringCollector);
    }

    public static UnaryOperator<String> splitFilterCollect(Function<? super String, Stream<String>> splitStringFunction,
                                                           Predicate<? super String> filterStringPredicate,
                                                           Collector<? super String, ?, String> stringCollector) {
        Objects.requireNonNull(splitStringFunction);
        Objects.requireNonNull(filterStringPredicate);
        Objects.requireNonNull(stringCollector);
        return s -> nullOrEmpty(s) ? s :
                splitStringFunction.apply(s)
                                   .filter(filterStringPredicate)
                                   .collect(stringCollector);
    }

    public static UnaryOperator<String> splitMapCollect(Function<? super String, Stream<String>> splitStringFunction,
                                                        UnaryOperator<String> stringUnaryOperator,
                                                        Collector<? super String, ?, String> stringCollector) {
        Objects.requireNonNull(splitStringFunction);
        Objects.requireNonNull(stringUnaryOperator);
        Objects.requireNonNull(stringCollector);
        return s -> nullOrEmpty(s) ? s :
                splitStringFunction.apply(s)
                                   .map(stringUnaryOperator)
                                   .collect(stringCollector);
    }

    /**
     * @see stexfires.util.function.ByteArrayFunctions
     */
    public static UnaryOperator<String> convertUsingByteArray(Function<String, byte[]> stringToBytesFunction,
                                                              Function<byte[], String> bytesToStringFunction) {
        Objects.requireNonNull(stringToBytesFunction);
        Objects.requireNonNull(bytesToStringFunction);
        return s -> bytesToStringFunction.apply(stringToBytesFunction.apply(s));
    }

}
