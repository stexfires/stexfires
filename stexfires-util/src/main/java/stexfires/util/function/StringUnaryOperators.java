package stexfires.util.function;

import org.jspecify.annotations.Nullable;

import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static stexfires.util.Strings.*;

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

    public static UnaryOperator<@Nullable String> of(Function<@Nullable String, @Nullable String> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    public static UnaryOperator<@Nullable String> concat(UnaryOperator<@Nullable String> first, UnaryOperator<@Nullable String> second) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        return (s) -> second.apply(first.apply(s));
    }

    public static UnaryOperator<@Nullable String> concat(UnaryOperator<@Nullable String> first, UnaryOperator<@Nullable String> second, UnaryOperator<@Nullable String> third) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(third);
        return (s) -> third.apply(second.apply(first.apply(s)));
    }

    public static UnaryOperator<@Nullable String> conditionalOperator(Predicate<@Nullable String> condition, UnaryOperator<@Nullable String> trueOperator, UnaryOperator<@Nullable String> falseOperator) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueOperator);
        Objects.requireNonNull(falseOperator);
        return (s) -> condition.test(s) ? trueOperator.apply(s) : falseOperator.apply(s);
    }

    private static boolean nullOrEmpty(@Nullable String s) {
        return (s == null) || s.isEmpty();
    }

    public static UnaryOperator<@Nullable String> identity() {
        return s -> s;
    }

    public static UnaryOperator<@Nullable String> constant(@Nullable String constant) {
        return s -> constant;
    }

    public static UnaryOperator<@Nullable String> duplicate() {
        return s -> nullOrEmpty(s) ? s : s.concat(s);
    }

    public static UnaryOperator<@Nullable String> reverse() {
        return s -> nullOrEmpty(s) ? s : new StringBuilder(s).reverse().toString();
    }

    public static UnaryOperator<@Nullable String> toNull() {
        return s -> null;
    }

    public static UnaryOperator<@Nullable String> toEmpty() {
        return s -> EMPTY;
    }

    public static UnaryOperator<@Nullable String> emptyToNull() {
        return s -> nullOrEmpty(s) ? null : s;
    }

    public static UnaryOperator<@Nullable String> nullToEmpty() {
        return s -> nullOrEmpty(s) ? EMPTY : s;
    }

    public static UnaryOperator<@Nullable String> nullToConstant(@Nullable String constant) {
        return s -> (s == null) ? constant : s;
    }

    public static UnaryOperator<@Nullable String> trimToNull() {
        return s -> nullOrEmpty(s) ? null : emptyToNull().apply(s.trim());
    }

    public static UnaryOperator<@Nullable String> trimToEmpty() {
        return s -> nullOrEmpty(s) ? EMPTY : s.trim();
    }

    public static UnaryOperator<@Nullable String> strip() {
        return s -> nullOrEmpty(s) ? s : s.strip();
    }

    public static UnaryOperator<@Nullable String> stripIndent() {
        return s -> nullOrEmpty(s) ? s : s.stripIndent();
    }

    public static UnaryOperator<@Nullable String> stripTrailing() {
        return s -> nullOrEmpty(s) ? s : s.stripTrailing();
    }

    public static UnaryOperator<@Nullable String> stripLeading() {
        return s -> nullOrEmpty(s) ? s : s.stripLeading();
    }

    public static UnaryOperator<@Nullable String> translateEscapes() {
        return s -> nullOrEmpty(s) ? s : s.translateEscapes();
    }

    public static UnaryOperator<@Nullable String> removeHorizontalWhitespaces() {
        return replaceAll("\\h", EMPTY);
    }

    public static UnaryOperator<@Nullable String> removeWhitespaces() {
        return replaceAll(REGEX_WHITESPACE, EMPTY);
    }

    public static UnaryOperator<@Nullable String> removeVerticalWhitespaces() {
        return replaceAll("\\v", EMPTY);
    }

    public static UnaryOperator<@Nullable String> removeLeadingHorizontalWhitespaces() {
        return replaceFirst("^\\h+", EMPTY);
    }

    public static UnaryOperator<@Nullable String> removeLeadingWhitespaces() {
        return replaceFirst("^\\s+", EMPTY);
    }

    public static UnaryOperator<@Nullable String> removeLeadingVerticalWhitespaces() {
        return replaceFirst("^\\v+", EMPTY);
    }

    public static UnaryOperator<@Nullable String> removeTrailingHorizontalWhitespaces() {
        return replaceFirst("\\h+$", EMPTY);
    }

    public static UnaryOperator<@Nullable String> removeTrailingWhitespaces() {
        return replaceFirst("\\s+$", EMPTY);
    }

    public static UnaryOperator<@Nullable String> removeTrailingVerticalWhitespaces() {
        return replaceFirst("\\v+$", EMPTY);
    }

    public static UnaryOperator<@Nullable String> tabToSpace() {
        return replaceAll(REGEX_TAB, " ");
    }

    public static UnaryOperator<@Nullable String> tabToSpaces2() {
        return replaceAll(REGEX_TAB, "  ");
    }

    public static UnaryOperator<@Nullable String> tabToSpaces4() {
        return replaceAll(REGEX_TAB, "    ");
    }

    public static UnaryOperator<@Nullable String> tabToSpaces8() {
        return replaceAll(REGEX_TAB, "        ");
    }

    public static UnaryOperator<@Nullable String> normalizeNFD() {
        return s -> nullOrEmpty(s) ? s : Normalizer.normalize(s, Normalizer.Form.NFD);
    }

    public static UnaryOperator<@Nullable String> normalizeNFC() {
        return s -> nullOrEmpty(s) ? s : Normalizer.normalize(s, Normalizer.Form.NFC);
    }

    public static UnaryOperator<@Nullable String> normalizeNFKD() {
        return s -> nullOrEmpty(s) ? s : Normalizer.normalize(s, Normalizer.Form.NFKD);
    }

    public static UnaryOperator<@Nullable String> normalizeNFKC() {
        return s -> nullOrEmpty(s) ? s : Normalizer.normalize(s, Normalizer.Form.NFKC);
    }

    public static UnaryOperator<@Nullable String> lengthAsString() {
        return s -> nullOrEmpty(s) ? "0" : String.valueOf(s.length());
    }

    public static UnaryOperator<@Nullable String> lowerCase(Locale locale) {
        Objects.requireNonNull(locale);
        return s -> nullOrEmpty(s) ? s : s.toLowerCase(locale);
    }

    public static UnaryOperator<@Nullable String> upperCase(Locale locale) {
        Objects.requireNonNull(locale);
        return s -> nullOrEmpty(s) ? s : s.toUpperCase(locale);
    }

    public static UnaryOperator<@Nullable String> repeat(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count is negative: " + count);
        }
        return s -> nullOrEmpty(s) ? s : s.repeat(count);
    }

    public static UnaryOperator<@Nullable String> replaceAll(String regex, String replacement) {
        Objects.requireNonNull(regex);
        Objects.requireNonNull(replacement);
        return s -> nullOrEmpty(s) ? s : s.replaceAll(regex, replacement);
    }

    public static UnaryOperator<@Nullable String> replaceFirst(String regex, String replacement) {
        Objects.requireNonNull(regex);
        Objects.requireNonNull(replacement);
        return s -> nullOrEmpty(s) ? s : s.replaceFirst(regex, replacement);
    }

    public static UnaryOperator<@Nullable String> removeAll(String regex) {
        Objects.requireNonNull(regex);
        return replaceAll(regex, EMPTY);
    }

    public static UnaryOperator<@Nullable String> removeFirst(String regex) {
        Objects.requireNonNull(regex);
        return replaceFirst(regex, EMPTY);
    }

    public static UnaryOperator<@Nullable String> indent(int n) {
        return s -> nullOrEmpty(s) ? s : s.indent(n);
    }

    public static UnaryOperator<@Nullable String> prefix(String prefix) {
        Objects.requireNonNull(prefix);
        return s -> nullOrEmpty(s) ? prefix : prefix.concat(s);
    }

    public static UnaryOperator<@Nullable String> suffix(String suffix) {
        Objects.requireNonNull(suffix);
        return s -> nullOrEmpty(s) ? suffix : s.concat(suffix);
    }

    public static UnaryOperator<@Nullable String> surround(String prefix, String suffix) {
        Objects.requireNonNull(prefix);
        Objects.requireNonNull(suffix);
        return s -> nullOrEmpty(s) ? prefix.concat(suffix) : prefix.concat(s).concat(suffix);
    }

    public static UnaryOperator<@Nullable String> supplier(Supplier<@Nullable String> supplier) {
        Objects.requireNonNull(supplier);
        return s -> supplier.get();
    }

    public static UnaryOperator<@Nullable String> codePointAt(int index, @Nullable String alternative) {
        return s -> (nullOrEmpty(s) || (index < 0) || (index >= s.length())) ? alternative : Character.toString(s.codePointAt(index));
    }

    public static UnaryOperator<@Nullable String> codePointAt(int index, Supplier<@Nullable String> alternative) {
        Objects.requireNonNull(alternative);
        return s -> (nullOrEmpty(s) || (index < 0) || (index >= s.length())) ? alternative.get() : Character.toString(s.codePointAt(index));
    }

    public static UnaryOperator<@Nullable String> charAt(int index, @Nullable String alternative) {
        return s -> (nullOrEmpty(s) || (index < 0) || (index >= s.length())) ? alternative : String.valueOf(s.charAt(index));
    }

    public static UnaryOperator<@Nullable String> charAt(int index, Supplier<@Nullable String> alternative) {
        Objects.requireNonNull(alternative);
        return s -> (nullOrEmpty(s) || (index < 0) || (index >= s.length())) ? alternative.get() : String.valueOf(s.charAt(index));
    }

    public static UnaryOperator<@Nullable String> formattedWithArguments(@Nullable Locale locale, @Nullable Object... args) {
        return s -> nullOrEmpty(s) ? s : String.format(locale, s, args);
    }

    public static UnaryOperator<@Nullable String> formatAsArgument(@Nullable Locale locale, String format) {
        Objects.requireNonNull(format);
        return s -> String.format(locale, format, s);
    }

    public static UnaryOperator<@Nullable String> substring(int beginIndex) {
        if (beginIndex < 0) {
            throw new IllegalArgumentException("beginIndex is negative: " + beginIndex);
        }

        return s -> nullOrEmpty(s) ? s : s.substring(Math.min(s.length(), beginIndex));
    }

    public static UnaryOperator<@Nullable String> substring(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            throw new IllegalArgumentException("beginIndex is negative: " + beginIndex);
        }
        if (beginIndex > endIndex) {
            throw new IllegalArgumentException("beginIndex is larger than endIndex: " + beginIndex + " " + endIndex);
        }

        return s -> nullOrEmpty(s) ? s : s.substring(Math.min(s.length(), beginIndex), Math.min(s.length(), endIndex));
    }

    public static UnaryOperator<@Nullable String> removeFromStart(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length is negative: " + length);
        }

        return substring(length);
    }

    public static UnaryOperator<@Nullable String> removeFromEnd(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length is negative: " + length);
        }

        return s -> nullOrEmpty(s) ? s : s.substring(0, Math.max(0, s.length() - length));
    }

    public static UnaryOperator<@Nullable String> removeStringFromStart(String string) {
        Objects.requireNonNull(string);
        return s -> (nullOrEmpty(s) || !s.startsWith(string)) ? s : removeFromStart(string.length()).apply(s);
    }

    public static UnaryOperator<@Nullable String> removeStringFromEnd(String string) {
        Objects.requireNonNull(string);
        return s -> (nullOrEmpty(s) || !s.endsWith(string)) ? s : removeFromEnd(string.length()).apply(s);
    }

    public static UnaryOperator<@Nullable String> keepFromStart(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length is negative: " + length);
        }

        return substring(0, length);
    }

    public static UnaryOperator<@Nullable String> keepFromEnd(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length is negative: " + length);
        }

        return s -> nullOrEmpty(s) ? s : s.substring(Math.max(0, s.length() - length));
    }

    public static UnaryOperator<@Nullable String> padStart(String character, int length) {
        if (nullOrEmpty(character)) {
            throw new IllegalArgumentException("character is null or empty:" + character);
        }
        if (length < 0) {
            throw new IllegalArgumentException("length is negative: " + length);
        }

        return s -> {
            String result;
            if (nullOrEmpty(s)) {
                if ((length % character.length()) == 0) {
                    result = character.repeat(length / character.length());
                } else {
                    result = character.repeat((length / character.length()) + 1).substring(character.length() - (length % character.length()));
                }
            } else if (s.length() < length) {
                int padLength = length - s.length();
                if ((padLength % character.length()) == 0) {
                    result = character.repeat(padLength / character.length()).concat(s);
                } else {
                    result = character.repeat((padLength / character.length()) + 1).substring(character.length() - (padLength % character.length())).concat(s);
                }
            } else {
                result = s;
            }
            return result;
        };
    }

    public static UnaryOperator<@Nullable String> padEnd(String character, int length) {
        if (nullOrEmpty(character)) {
            throw new IllegalArgumentException("character is null or empty:" + character);
        }
        if (length < 0) {
            throw new IllegalArgumentException("length is negative: " + length);
        }
        return s -> {
            String result;
            if (nullOrEmpty(s)) {
                if ((length % character.length()) == 0) {
                    result = character.repeat(length / character.length());
                } else {
                    result = character.repeat((length / character.length()) + 1).substring(0, length);
                }
            } else if (s.length() < length) {
                int padLength = length - s.length();
                if ((padLength % character.length()) == 0) {
                    result = s.concat(character.repeat(padLength / character.length()));
                } else {
                    result = s.concat(character.repeat((padLength / character.length()) + 1).substring(0, padLength));
                }
            } else {
                result = s;
            }
            return result;
        };
    }

    public static UnaryOperator<@Nullable String> encodeBase64(Base64.Encoder encoder, Charset charset) {
        Objects.requireNonNull(encoder);
        Objects.requireNonNull(charset);
        return s -> nullOrEmpty(s) ? s : encoder.encodeToString(s.getBytes(charset));
    }

    public static UnaryOperator<@Nullable String> decodeBase64(Base64.Decoder decoder, Charset charset) {
        Objects.requireNonNull(decoder);
        Objects.requireNonNull(charset);
        return s -> nullOrEmpty(s) ? s : new String(decoder.decode(s), charset);
    }

    public static UnaryOperator<@Nullable String> splitCollect(Function<? super String, Stream<String>> splitStringFunction,
                                                               Collector<? super String, ?, @Nullable String> stringCollector) {
        Objects.requireNonNull(splitStringFunction);
        Objects.requireNonNull(stringCollector);
        return s -> nullOrEmpty(s) ? s :
                splitStringFunction.apply(s)
                                   .collect(stringCollector);
    }

    public static UnaryOperator<@Nullable String> splitFilterCollect(Function<? super String, Stream<String>> splitStringFunction,
                                                                     Predicate<? super String> filterStringPredicate,
                                                                     Collector<? super String, ?, @Nullable String> stringCollector) {
        Objects.requireNonNull(splitStringFunction);
        Objects.requireNonNull(filterStringPredicate);
        Objects.requireNonNull(stringCollector);
        return s -> nullOrEmpty(s) ? s :
                splitStringFunction.apply(s)
                                   .filter(filterStringPredicate)
                                   .collect(stringCollector);
    }

    public static UnaryOperator<@Nullable String> splitMapCollect(Function<? super String, Stream<String>> splitStringFunction,
                                                                  UnaryOperator<String> stringUnaryOperator,
                                                                  Collector<? super String, ?, @Nullable String> stringCollector) {
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
    public static UnaryOperator<@Nullable String> convertUsingByteArray(Function<@Nullable String, byte @Nullable []> stringToBytesFunction,
                                                                        Function<byte @Nullable [], @Nullable String> bytesToStringFunction) {
        Objects.requireNonNull(stringToBytesFunction);
        Objects.requireNonNull(bytesToStringFunction);
        return s -> bytesToStringFunction.apply(stringToBytesFunction.apply(s));
    }

}
