package stexfires.util;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.lang.Character.UnicodeBlock;
import java.lang.Character.UnicodeScript;
import java.util.HexFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * The record represents a Unicode code point.
 * It consists of an {@code int} value between {@link #MIN_VALUE} and {@link #MAX_VALUE}.
 *
 * @see java.lang.Character
 * @since 0.1
 */
public record CodePoint(int value) implements Serializable, Comparable<CodePoint> {

    /**
     * The minimum value of an ASCII code point (0 = 0x00).
     */
    public static final int MIN_ASCII_VALUE = 0;
    /**
     * The maximum value of an ASCII code point (127 = 0x7F).
     */
    public static final int MAX_ASCII_VALUE = 0x7F;

    /**
     * The minimum value of a Unicode code point (0 = 0x00).
     *
     * @see java.lang.Character#MIN_CODE_POINT
     */
    public static final int MIN_VALUE = Character.MIN_CODE_POINT;
    /**
     * The maximum value of a Unicode code point (1114111 = 0x10FFFF).
     *
     * @see java.lang.Character#MAX_CODE_POINT
     */
    public static final int MAX_VALUE = Character.MAX_CODE_POINT;

    /**
     * The minimum value of a Unicode high-surrogate code unit (55296 = 0xD800).
     *
     * @see java.lang.Character#MIN_HIGH_SURROGATE
     */
    public static final int MIN_HIGH_SURROGATE = Character.MIN_HIGH_SURROGATE;
    /**
     * The maximum value of a Unicode high-surrogate code unit (56319 = 0xDBFF).
     *
     * @see java.lang.Character#MAX_HIGH_SURROGATE
     */
    public static final int MAX_HIGH_SURROGATE = Character.MAX_HIGH_SURROGATE;
    /**
     * The minimum value of a Unicode low-surrogate code unit (56320 = 0xDC00).
     *
     * @see java.lang.Character#MIN_LOW_SURROGATE
     */
    public static final int MIN_LOW_SURROGATE = Character.MIN_LOW_SURROGATE;
    /**
     * The maximum value of a Unicode low-surrogate code unit (57343 = 0xDFFF).
     *
     * @see java.lang.Character#MAX_LOW_SURROGATE
     */
    public static final int MAX_LOW_SURROGATE = Character.MAX_LOW_SURROGATE;

    /**
     * The minimum value of a Unicode Basic Multilingual Plane (BMP) code point (0 = 0x0000).
     * It is the same as {@link #MIN_VALUE}.
     *
     * @see java.lang.Character#MIN_VALUE
     */
    public static final int MIN_BMP_CODE_POINT = Character.MIN_VALUE;
    /**
     * The maximum value of a Unicode Basic Multilingual Plane (BMP) code point (65535 = 0xFFFF).
     * It is one less than {@link #MIN_SUPPLEMENTARY_CODE_POINT} and the largest value of type char.
     *
     * @see java.lang.Character#MAX_VALUE
     */
    public static final int MAX_BMP_CODE_POINT = Character.MAX_VALUE;
    /**
     * The minimum value of a Unicode supplementary code point (65536 = 0x010000).
     * It is one greater than {@link #MAX_BMP_CODE_POINT}.
     *
     * @see java.lang.Character#MIN_SUPPLEMENTARY_CODE_POINT
     */
    public static final int MIN_SUPPLEMENTARY_CODE_POINT = Character.MIN_SUPPLEMENTARY_CODE_POINT;
    /**
     * The maximum value of a Unicode supplementary code point (1114111 = 0x10FFFF).
     * It is the same as {@link #MAX_VALUE}.
     *
     * @see java.lang.Character#MAX_CODE_POINT
     */
    public static final int MAX_SUPPLEMENTARY_CODE_POINT = Character.MAX_CODE_POINT;

    /**
     * Constructs a {@code CodePoint} with the given value.
     *
     * @param value the value of the code point
     * @throws IllegalArgumentException if the value is not a valid code point
     * @see java.lang.Character#isValidCodePoint(int)
     */
    public CodePoint {
        if (!Character.isValidCodePoint(value)) {
            throw new IllegalArgumentException("Invalid code point: " + value);
        }
    }

    /**
     * Returns the code point specified by the given character name.
     *
     * @param name the character name
     * @return the code point specified by the given character name.
     * @throws java.lang.IllegalArgumentException if the specified name is not a valid character name
     * @see java.lang.Character#codePointOf(java.lang.String)
     */
    public static CodePoint ofName(@NotNull String name) {
        Objects.requireNonNull(name);
        return new CodePoint(Character.codePointOf(name));
    }

    /**
     * Returns the code point for the given {@code char}.
     *
     * @param character the {@code char}
     * @return the code point for the given {@code char}.
     */
    public static CodePoint ofChar(char character) {
        return new CodePoint(character);
    }

    /**
     * Finds the first code point that matches the given {@code Predicate}.
     * The search starts at {@code startCodePoint} and ends at {@code endCodePoint}.
     * The {@code startCodePoint} can be less than, equal to or greater than {@code endCodePoint}.
     *
     * @param startCodePoint     the code point to start the search (inclusive)
     * @param endCodePoint       the code point to end the search (inclusive)
     * @param codePointPredicate the predicate to apply to each code point to determine if it should be returned
     * @return the first code point that matches the given {@code Predicate}.
     */
    public static Optional<CodePoint> findFirst(@NotNull CodePoint startCodePoint, @NotNull CodePoint endCodePoint,
                                                @NotNull Predicate<CodePoint> codePointPredicate) {
        Objects.requireNonNull(startCodePoint);
        Objects.requireNonNull(endCodePoint);
        Objects.requireNonNull(codePointPredicate);
        if (startCodePoint.value < endCodePoint.value) {
            return IntStream.rangeClosed(startCodePoint.value, endCodePoint.value)
                            .mapToObj(CodePoint::new)
                            .filter(codePointPredicate)
                            .findFirst();
        } else if (startCodePoint.value > endCodePoint.value) {
            return IntStream.rangeClosed(endCodePoint.value, startCodePoint.value)
                            .map(i -> endCodePoint.value + startCodePoint.value - i)
                            .mapToObj(CodePoint::new)
                            .filter(codePointPredicate)
                            .findFirst();
        } else {
            return codePointPredicate.test(startCodePoint) ? Optional.of(startCodePoint) : Optional.empty();
        }
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     */
    @Override
    public int compareTo(@NotNull CodePoint o) {
        Objects.requireNonNull(o);
        return Integer.compare(value, o.value);
    }

    /**
     * Returns the value of the code point as a {@code char} if it is a BmpCodePoint.
     *
     * @return the value of the code point as a {@code char} if it is a BmpCodePoint.
     * @throws ArithmeticException if the value is not a BmpCodePoint
     * @see CodePoint#MAX_BMP_CODE_POINT
     */
    public char valueAsChar() throws ArithmeticException {
        if (value > CodePoint.MAX_BMP_CODE_POINT || value < CodePoint.MIN_BMP_CODE_POINT) {
            throw new ArithmeticException("Not a BmpCodePoint: " + value);
        }
        return (char) value;
    }

    /**
     * Returns the value of the code point as an {@code Optional<Character>}.
     * If the code point is not a BmpCodePoint the {@code Optional} is empty.
     *
     * @return the value of the code point as an {@code Optional<Character>}.
     * @see CodePoint#MAX_BMP_CODE_POINT
     */
    public Optional<Character> valueAsOptionalCharacter() {
        if (value > CodePoint.MAX_BMP_CODE_POINT || value < CodePoint.MIN_BMP_CODE_POINT) {
            return Optional.empty();
        }
        return Optional.of((char) value);
    }

    /**
     * Returns the next code point as an {@code Optional<CodePoint>}.
     * If the code point is the maximum value the {@code Optional} is empty.
     *
     * @return the next code point as an {@code Optional<CodePoint>}.
     */
    public Optional<CodePoint> next() {
        return hasNext() ? Optional.of(new CodePoint(value + 1)) : Optional.empty();
    }

    /**
     * Returns the previous code point as an {@code Optional<CodePoint>}.
     * If the code point is the minimum value the {@code Optional} is empty.
     *
     * @return the previous code point as an {@code Optional<CodePoint>}.
     */
    public Optional<CodePoint> previous() {
        return hasPrevious() ? Optional.of(new CodePoint(value - 1)) : Optional.empty();
    }

    /**
     * Returns {@code true} if the code point has a next code point.
     * If the code point is the maximum value {@code false} is returned.
     *
     * @return {@code true} if the code point has a next code point.
     */
    public boolean hasNext() {
        return value < MAX_VALUE;
    }

    /**
     * Returns {@code true} if the code point has a previous code point.
     * If the code point is the minimum value {@code false} is returned.
     *
     * @return {@code true} if the code point has a previous code point.
     */
    public boolean hasPrevious() {
        return value > MIN_VALUE;
    }

    /**
     * Returns the name of the code point as an {@code Optional<String>}.
     * If the code point is unassigned the {@code Optional} is empty.
     *
     * @return the name of the code point as an {@code Optional<String>}.
     * @see java.lang.Character#getName(int)
     */
    public Optional<String> name() {
        return Optional.ofNullable(Character.getName(value));
    }

    /**
     * Returns a String object representing this code point.
     *
     * @return a String object representing this code point.
     * @see java.lang.Character#toString(int)
     */
    public String string() {
        return Character.toString(value);
    }

    /**
     * Returns whether the code point is printable.
     * <p>
     * A code point is printable if its type is not {@link Character#UNASSIGNED}, {@link Character#CONTROL},
     * {@link Character#SURROGATE} or {@link Character#PRIVATE_USE}.
     *
     * @return whether the code point is printable.
     * @see java.lang.Character#getType(int)
     */
    public boolean isPrintable() {
        int characterType = Character.getType(value);
        return characterType != Character.UNASSIGNED
                && characterType != Character.CONTROL
                && characterType != Character.SURROGATE
                && characterType != Character.PRIVATE_USE;
    }

    /**
     * Returns the String representation of the given code point as an {@code Optional<String>}.
     * If the code point is not printable the {@code Optional} is empty.
     *
     * @return the String representation of the given code point as an {@code Optional<String>}.
     * @see CodePoint#isPrintable()
     * @see Character#toString(int)
     * @see CodePoint#string()
     */
    public Optional<String> printableString() {
        return isPrintable() ? Optional.of(Character.toString(value)) : Optional.empty();
    }

    /**
     * Returns the value of the code point as a hex {@code String}.
     *
     * @return the value of the code point as a hex {@code String}.
     * @see java.lang.Integer#toHexString(int)
     */
    public String hexString() {
        return Integer.toHexString(value);
    }

    /**
     * Returns the value of the code point as a {@code String} with Unicode escapes.
     *
     * @return the value of the code point as a {@code String} with Unicode escapes.
     * @see java.util.HexFormat#toHexDigits(char)
     */
    public String unicodeEscapes() {
        var hexFormat = HexFormat.of();
        if (value >= CodePoint.MIN_BMP_CODE_POINT && value <= CodePoint.MAX_BMP_CODE_POINT) {
            return "\\u" + hexFormat.toHexDigits((char) value);
        } else if (value >= CodePoint.MIN_SUPPLEMENTARY_CODE_POINT && value <= CodePoint.MAX_SUPPLEMENTARY_CODE_POINT) {
            return "\\u" + hexFormat.toHexDigits(Character.highSurrogate(value)) + "\\u" + hexFormat.toHexDigits(Character.lowSurrogate(value));
        } else {
            // Should never happen
            throw new IllegalStateException("Not a valid code point: " + value);
        }
    }

    /**
     * Determines whether the code point is an alphabetic character.
     *
     * @return {@code true} if the code point is an alphabetic character.
     * @see java.lang.Character#isAlphabetic(int)
     */
    public boolean isAlphabetic() {
        return Character.isAlphabetic(value);
    }

    /**
     * Determines whether the code point is in the ASCII range.
     *
     * @return {@code true} if the code point is in the ASCII range.
     * @see CodePoint#MIN_ASCII_VALUE
     * @see CodePoint#MAX_ASCII_VALUE
     */
    public boolean isASCII() {
        // compare with MAX_ASCII_VALUE first because the comparison with MIN_ASCII_VALUE should always be true
        return value <= MAX_ASCII_VALUE && value >= MIN_ASCII_VALUE;
    }

    /**
     * Determines whether the code point is between the given code points.
     *
     * @param minCodePoint the minimum code point
     * @param maxCodePoint the maximum code point
     * @return {@code true} if the code point is between the given code points.
     */
    public boolean isBetween(int minCodePoint, int maxCodePoint) {
        // Compare with the maximum first, as this is probably more often the decisive comparison.
        return value <= maxCodePoint && value >= minCodePoint;
    }

    /**
     * Determines whether the code point is in the Basic Multilingual Plane (BMP).
     *
     * @return {@code true} if the code point is in the Basic Multilingual Plane (BMP).
     * @see java.lang.Character#isBmpCodePoint(int)
     */
    public boolean isBmpCodePoint() {
        return Character.isBmpCodePoint(value);
    }

    /**
     * Determines whether the code point is defined in Unicode.
     *
     * @return {@code true} if the code point is defined in Unicode.
     * @see java.lang.Character#isDefined(int)
     */
    public boolean isDefined() {
        return Character.isDefined(value);
    }

    /**
     * Determines whether the code point is a digit.
     *
     * @return {@code true} if the code point is a digit.
     * @see java.lang.Character#isDigit(int)
     */
    public boolean isDigit() {
        return Character.isDigit(value);
    }

    /**
     * Determines whether the code point is an Emoji.
     *
     * @return {@code true} if the code point is an Emoji.
     * @see java.lang.Character#isEmoji(int)
     */
    public boolean isEmoji() {
        return Character.isEmoji(value);
    }

    /**
     * Determines whether the code point is an Emoji Component.
     *
     * @return {@code true} if the code point is an Emoji Component.
     * @see java.lang.Character#isEmojiComponent(int)
     */
    public boolean isEmojiComponent() {
        return Character.isEmojiComponent(value);
    }

    /**
     * Determines whether the code point is an Emoji Modifier.
     *
     * @return {@code true} if the code point is an Emoji Modifier.
     * @see java.lang.Character#isEmojiModifier(int)
     */
    public boolean isEmojiModifier() {
        return Character.isEmojiModifier(value);
    }

    /**
     * Determines whether the code point is an Emoji Modifier Base.
     *
     * @return {@code true} if the code point is an Emoji Modifier Base.
     * @see java.lang.Character#isEmojiModifierBase(int)
     */
    public boolean isEmojiModifierBase() {
        return Character.isEmojiModifierBase(value);
    }

    /**
     * Determines whether the code point has the Emoji Presentation property.
     *
     * @return {@code true} if the code point has the Emoji Presentation property.
     * @see java.lang.Character#isEmojiPresentation(int)
     */
    public boolean isEmojiPresentation() {
        return Character.isEmojiPresentation(value);
    }

    /**
     * Determines whether the code point is an Extended Pictographic.
     *
     * @return {@code true} if the code point is an Extended Pictographic.
     * @see java.lang.Character#isExtendedPictographic(int)
     */
    public boolean isExtendedPictographic() {
        return Character.isExtendedPictographic(value);
    }

    /**
     * Determines whether the code point should be regarded as an ignorable character in a Java identifier or a Unicode identifier.
     *
     * @return {@code true} if the code point should be regarded as an ignorable character in a Java identifier or a Unicode identifier.
     * @see java.lang.Character#isIdentifierIgnorable(int)
     */
    public boolean isIdentifierIgnorable() {
        return Character.isIdentifierIgnorable(value);
    }

    /**
     * Determines whether the code point is CJKV (Chinese, Japanese, Korean and Vietnamese) ideograph.
     *
     * @return {@code true} if the code point is CJKV (Chinese, Japanese, Korean and Vietnamese) ideograph.
     * @see java.lang.Character#isIdeographic(int)
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean isIdeographic() {
        return Character.isIdeographic(value);
    }

    /**
     * Determines whether the code point is an ISO control character.
     *
     * @return {@code true} if the code point is an ISO control character.
     * @see java.lang.Character#isISOControl(int)
     */
    public boolean isISOControl() {
        return Character.isISOControl(value);
    }

    /**
     * Determines whether the code point may be part of a Java identifier as other than the first character.
     *
     * @return {@code true} if the code point may be part of a Java identifier as other than the first character.
     * @see java.lang.Character#isJavaIdentifierPart(int)
     */
    public boolean isJavaIdentifierPart() {
        return Character.isJavaIdentifierPart(value);
    }

    /**
     * Determines whether the code point is a letter.
     *
     * @return {@code true} if the code point is a letter.
     * @see java.lang.Character#isLetter(int)
     */
    public boolean isLetter() {
        return Character.isLetter(value);
    }

    /**
     * Determines whether the code point is a letter or digit.
     *
     * @return {@code true} if the code point is a letter or digit.
     * @see java.lang.Character#isLetterOrDigit(int)
     */
    public boolean isLetterOrDigit() {
        return Character.isLetterOrDigit(value);
    }

    /**
     * Determines whether the code point is a lowercase character.
     *
     * @return {@code true} if the code point is a lowercase character.
     * @see java.lang.Character#isLowerCase(int)
     */
    public boolean isLowerCase() {
        return Character.isLowerCase(value);
    }

    /**
     * Determines whether the code point is mirrored according to the Unicode specification.
     *
     * @return {@code true} if the code point is mirrored according to the Unicode specification.
     * @see java.lang.Character#isMirrored(int)
     */
    public boolean isMirrored() {
        return Character.isMirrored(value);
    }

    /**
     * Determines whether the code point is a space character.
     *
     * @return {@code true} if the code point is a space character.
     * @see java.lang.Character#isSpaceChar(int)
     */
    public boolean isSpaceChar() {
        return Character.isSpaceChar(value);
    }

    /**
     * Determines whether the code point is in the supplementary character range.
     *
     * @return {@code true} if the code point is in the supplementary character range.
     * @see java.lang.Character#isSupplementaryCodePoint(int)
     */
    public boolean isSupplementaryCodePoint() {
        return Character.isSupplementaryCodePoint(value);
    }

    /**
     * Determines whether the code point is a titlecase character.
     *
     * @return {@code true} if the code point is a titlecase character.
     * @see java.lang.Character#isTitleCase(int)
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean isTitleCase() {
        return Character.isTitleCase(value);
    }

    /**
     * Determines whether the code point may be part of a Unicode identifier as other than the first character.
     *
     * @return {@code true} if the code point may be part of a Unicode identifier as other than the first character.
     * @see java.lang.Character#isUnicodeIdentifierPart(int)
     */
    public boolean isUnicodeIdentifierPart() {
        return Character.isUnicodeIdentifierPart(value);
    }

    /**
     * Determines whether the code point is permissible as the first character in a Unicode identifier.
     *
     * @return {@code true} if the code point is permissible as the first character in a Unicode identifier.
     * @see java.lang.Character#isUnicodeIdentifierStart(int)
     */
    public boolean isUnicodeIdentifierStart() {
        return Character.isUnicodeIdentifierStart(value);
    }

    /**
     * Determines whether the code point is an uppercase character.
     *
     * @return {@code true} if the code point is an uppercase character.
     * @see java.lang.Character#isUpperCase(int)
     */
    public boolean isUpperCase() {
        return Character.isUpperCase(value);
    }

    /**
     * Determines whether the code point is white space according to Java.
     *
     * @return {@code true} if the code point is white space according to Java.
     * @see java.lang.Character#isWhitespace(int)
     */
    public boolean isWhitespace() {
        return Character.isWhitespace(value);
    }

    /**
     * Determines the number of char values needed to represent the code point.
     *
     * @return the number of char values needed to represent the code point.
     * @see java.lang.Character#charCount(int)
     */
    public int charCount() {
        return Character.charCount(value);
    }

    /**
     * Returns the numeric value of the code point as an {@code Optional<Integer>},
     * if it is a decimal digit character.
     *
     * @return the numeric value of the code point as an {@code Optional<Integer>}, if it is a decimal digit character.
     * @see java.lang.Character#digit(int, int)
     */
    public Optional<Integer> decimalDigit() {
        int digit = Character.digit(value, 10);
        return digit < 0 ? Optional.empty() : Optional.of(digit);
    }

    /**
     * Returns the numeric value of the code point as an {@code Optional<Integer>},
     * if it has a non-negative numeric value.
     *
     * @return the numeric value of the code point as an {@code Optional<Integer>}, if it has a non-negative numeric value.
     * @see java.lang.Character#getNumericValue(int)
     */
    public Optional<Integer> numericValue() {
        int numericValue = Character.getNumericValue(value);
        return numericValue < 0 ? Optional.empty() : Optional.of(numericValue);
    }

    /**
     * Returns the trailing surrogate as an {@code Optional<Character>}.
     * If the code point is not a supplementary character the {@code Optional} is empty.
     *
     * @return the trailing surrogate as an {@code Optional<Character>}.
     * @see java.lang.Character#lowSurrogate(int)
     * @see java.lang.Character#isSupplementaryCodePoint(int)
     */
    public Optional<Character> lowSurrogate() {
        return Character.isSupplementaryCodePoint(value) ? Optional.of(Character.lowSurrogate(value)) : Optional.empty();
    }

    /**
     * Returns the leading surrogate as an {@code Optional<Character>}.
     * If the code point is not a supplementary character the {@code Optional} is empty.
     *
     * @return the leading surrogate as an {@code Optional<Character>}.
     * @see java.lang.Character#highSurrogate(int)
     * @see java.lang.Character#isSupplementaryCodePoint(int)
     */
    public Optional<Character> highSurrogate() {
        return Character.isSupplementaryCodePoint(value) ? Optional.of(Character.highSurrogate(value)) : Optional.empty();
    }

    /**
     * Returns the type of the code point as the enum {@link Type}.
     *
     * @return the type of the code point as the enum {@link Type}.
     * @see java.lang.Character#getType(int)
     */
    public Type type() {
        return switch (Character.getType(value)) {
            case Character.UNASSIGNED -> Type.UNASSIGNED;
            case Character.UPPERCASE_LETTER -> Type.UPPERCASE_LETTER;
            case Character.LOWERCASE_LETTER -> Type.LOWERCASE_LETTER;
            case Character.TITLECASE_LETTER -> Type.TITLECASE_LETTER;
            case Character.MODIFIER_LETTER -> Type.MODIFIER_LETTER;
            case Character.OTHER_LETTER -> Type.OTHER_LETTER;
            case Character.NON_SPACING_MARK -> Type.NON_SPACING_MARK;
            case Character.ENCLOSING_MARK -> Type.ENCLOSING_MARK;
            case Character.COMBINING_SPACING_MARK -> Type.COMBINING_SPACING_MARK;
            case Character.DECIMAL_DIGIT_NUMBER -> Type.DECIMAL_DIGIT_NUMBER;
            case Character.LETTER_NUMBER -> Type.LETTER_NUMBER;
            case Character.OTHER_NUMBER -> Type.OTHER_NUMBER;
            case Character.SPACE_SEPARATOR -> Type.SPACE_SEPARATOR;
            case Character.LINE_SEPARATOR -> Type.LINE_SEPARATOR;
            case Character.PARAGRAPH_SEPARATOR -> Type.PARAGRAPH_SEPARATOR;
            case Character.CONTROL -> Type.CONTROL;
            case Character.FORMAT -> Type.FORMAT;
            case Character.PRIVATE_USE -> Type.PRIVATE_USE;
            case Character.SURROGATE -> Type.SURROGATE;
            case Character.DASH_PUNCTUATION -> Type.DASH_PUNCTUATION;
            case Character.START_PUNCTUATION -> Type.START_PUNCTUATION;
            case Character.END_PUNCTUATION -> Type.END_PUNCTUATION;
            case Character.CONNECTOR_PUNCTUATION -> Type.CONNECTOR_PUNCTUATION;
            case Character.OTHER_PUNCTUATION -> Type.OTHER_PUNCTUATION;
            case Character.MATH_SYMBOL -> Type.MATH_SYMBOL;
            case Character.CURRENCY_SYMBOL -> Type.CURRENCY_SYMBOL;
            case Character.MODIFIER_SYMBOL -> Type.MODIFIER_SYMBOL;
            case Character.OTHER_SYMBOL -> Type.OTHER_SYMBOL;
            case Character.INITIAL_QUOTE_PUNCTUATION -> Type.INITIAL_QUOTE_PUNCTUATION;
            case Character.FINAL_QUOTE_PUNCTUATION -> Type.FINAL_QUOTE_PUNCTUATION;
            default ->
                    throw new IllegalStateException("Unknown type of Unicode code point: " + value + " (" + Character.getType(value) + ")");
        };
    }

    /**
     * Returns the directionality of the code point as the enum {@link Directionality}.
     *
     * @return the directionality of the code point as the enum {@link Directionality}.
     * @see java.lang.Character#getDirectionality(int)
     */
    public Directionality directionality() {
        return switch (Character.getDirectionality(value)) {
            case Character.DIRECTIONALITY_UNDEFINED -> Directionality.DIRECTIONALITY_UNDEFINED;
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT -> Directionality.DIRECTIONALITY_LEFT_TO_RIGHT;
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT -> Directionality.DIRECTIONALITY_RIGHT_TO_LEFT;
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC -> Directionality.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
            case Character.DIRECTIONALITY_EUROPEAN_NUMBER -> Directionality.DIRECTIONALITY_EUROPEAN_NUMBER;
            case Character.DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR ->
                    Directionality.DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR;
            case Character.DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR ->
                    Directionality.DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR;
            case Character.DIRECTIONALITY_ARABIC_NUMBER -> Directionality.DIRECTIONALITY_ARABIC_NUMBER;
            case Character.DIRECTIONALITY_COMMON_NUMBER_SEPARATOR ->
                    Directionality.DIRECTIONALITY_COMMON_NUMBER_SEPARATOR;
            case Character.DIRECTIONALITY_NONSPACING_MARK -> Directionality.DIRECTIONALITY_NONSPACING_MARK;
            case Character.DIRECTIONALITY_BOUNDARY_NEUTRAL -> Directionality.DIRECTIONALITY_BOUNDARY_NEUTRAL;
            case Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR -> Directionality.DIRECTIONALITY_PARAGRAPH_SEPARATOR;
            case Character.DIRECTIONALITY_SEGMENT_SEPARATOR -> Directionality.DIRECTIONALITY_SEGMENT_SEPARATOR;
            case Character.DIRECTIONALITY_WHITESPACE -> Directionality.DIRECTIONALITY_WHITESPACE;
            case Character.DIRECTIONALITY_OTHER_NEUTRALS -> Directionality.DIRECTIONALITY_OTHER_NEUTRALS;
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING ->
                    Directionality.DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING;
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE ->
                    Directionality.DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE;
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING ->
                    Directionality.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING;
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE ->
                    Directionality.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE;
            case Character.DIRECTIONALITY_POP_DIRECTIONAL_FORMAT ->
                    Directionality.DIRECTIONALITY_POP_DIRECTIONAL_FORMAT;
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT_ISOLATE -> Directionality.DIRECTIONALITY_LEFT_TO_RIGHT_ISOLATE;
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_ISOLATE -> Directionality.DIRECTIONALITY_RIGHT_TO_LEFT_ISOLATE;
            case Character.DIRECTIONALITY_FIRST_STRONG_ISOLATE -> Directionality.DIRECTIONALITY_FIRST_STRONG_ISOLATE;
            case Character.DIRECTIONALITY_POP_DIRECTIONAL_ISOLATE ->
                    Directionality.DIRECTIONALITY_POP_DIRECTIONAL_ISOLATE;
            default ->
                    throw new IllegalStateException("Unknown directionality of Unicode code point: " + value + " (" + Character.getDirectionality(value) + ")");
        };
    }

    /**
     * Returns the Unicode block containing the given code point as an {@code Optional<UnicodeBlock>}.
     *
     * @return the Unicode block containing the given code point as an {@code Optional<UnicodeBlock>}.
     * @see java.lang.Character.UnicodeBlock#of(int)
     */
    public Optional<UnicodeBlock> unicodeBlock() {
        return Optional.ofNullable(UnicodeBlock.of(value));
    }

    /**
     * Returns the Unicode script containing the given code point.
     *
     * @return the Unicode script containing the given code point.
     * @see java.lang.Character.UnicodeScript#of(int)
     */
    public UnicodeScript unicodeScript() {
        return UnicodeScript.of(value);
    }

    /**
     * Directionality of Unicode code points.
     *
     * @see java.lang.Character#getDirectionality(int)
     */
    @SuppressWarnings("SpellCheckingInspection")
    public enum Directionality {

        /**
         * Undefined bidirectional character type. Undefined {@code char}
         * values have undefined directionality in the Unicode specification.
         */
        DIRECTIONALITY_UNDEFINED(Character.DIRECTIONALITY_UNDEFINED),

        /**
         * Strong bidirectional character type "L" in the Unicode specification.
         */
        DIRECTIONALITY_LEFT_TO_RIGHT(Character.DIRECTIONALITY_LEFT_TO_RIGHT),

        /**
         * Strong bidirectional character type "R" in the Unicode specification.
         */
        DIRECTIONALITY_RIGHT_TO_LEFT(Character.DIRECTIONALITY_RIGHT_TO_LEFT),

        /**
         * Strong bidirectional character type "AL" in the Unicode specification.
         */
        DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC(Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC),

        /**
         * Weak bidirectional character type "EN" in the Unicode specification.
         */
        DIRECTIONALITY_EUROPEAN_NUMBER(Character.DIRECTIONALITY_EUROPEAN_NUMBER),

        /**
         * Weak bidirectional character type "ES" in the Unicode specification.
         */
        DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR(Character.DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR),

        /**
         * Weak bidirectional character type "ET" in the Unicode specification.
         */
        DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR(Character.DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR),

        /**
         * Weak bidirectional character type "AN" in the Unicode specification.
         */
        DIRECTIONALITY_ARABIC_NUMBER(Character.DIRECTIONALITY_ARABIC_NUMBER),

        /**
         * Weak bidirectional character type "CS" in the Unicode specification.
         */
        DIRECTIONALITY_COMMON_NUMBER_SEPARATOR(Character.DIRECTIONALITY_COMMON_NUMBER_SEPARATOR),

        /**
         * Weak bidirectional character type "NSM" in the Unicode specification.
         */
        DIRECTIONALITY_NONSPACING_MARK(Character.DIRECTIONALITY_NONSPACING_MARK),

        /**
         * Weak bidirectional character type "BN" in the Unicode specification.
         */
        DIRECTIONALITY_BOUNDARY_NEUTRAL(Character.DIRECTIONALITY_BOUNDARY_NEUTRAL),

        /**
         * Neutral bidirectional character type "B" in the Unicode specification.
         */
        DIRECTIONALITY_PARAGRAPH_SEPARATOR(Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR),

        /**
         * Neutral bidirectional character type "S" in the Unicode specification.
         */
        DIRECTIONALITY_SEGMENT_SEPARATOR(Character.DIRECTIONALITY_SEGMENT_SEPARATOR),

        /**
         * Neutral bidirectional character type "WS" in the Unicode specification.
         */
        DIRECTIONALITY_WHITESPACE(Character.DIRECTIONALITY_WHITESPACE),

        /**
         * Neutral bidirectional character type "ON" in the Unicode specification.
         */
        DIRECTIONALITY_OTHER_NEUTRALS(Character.DIRECTIONALITY_OTHER_NEUTRALS),

        /**
         * Strong bidirectional character type "LRE" in the Unicode specification.
         */
        DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING(Character.DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING),

        /**
         * Strong bidirectional character type "LRO" in the Unicode specification.
         */
        DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE(Character.DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE),

        /**
         * Strong bidirectional character type "RLE" in the Unicode specification.
         */
        DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING(Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING),

        /**
         * Strong bidirectional character type "RLO" in the Unicode specification.
         */
        DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE(Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE),

        /**
         * Weak bidirectional character type "PDF" in the Unicode specification.
         */
        DIRECTIONALITY_POP_DIRECTIONAL_FORMAT(Character.DIRECTIONALITY_POP_DIRECTIONAL_FORMAT),

        /**
         * Weak bidirectional character type "LRI" in the Unicode specification.
         */
        DIRECTIONALITY_LEFT_TO_RIGHT_ISOLATE(Character.DIRECTIONALITY_LEFT_TO_RIGHT_ISOLATE),

        /**
         * Weak bidirectional character type "RLI" in the Unicode specification.
         */
        DIRECTIONALITY_RIGHT_TO_LEFT_ISOLATE(Character.DIRECTIONALITY_RIGHT_TO_LEFT_ISOLATE),

        /**
         * Weak bidirectional character type "FSI" in the Unicode specification.
         */
        DIRECTIONALITY_FIRST_STRONG_ISOLATE(Character.DIRECTIONALITY_FIRST_STRONG_ISOLATE),

        /**
         * Weak bidirectional character type "PDI" in the Unicode specification.
         */
        DIRECTIONALITY_POP_DIRECTIONAL_ISOLATE(Character.DIRECTIONALITY_POP_DIRECTIONAL_ISOLATE);

        private final byte byteConstant;

        Directionality(byte byteConstant) {
            this.byteConstant = byteConstant;
        }

        public final byte byteConstant() {
            return byteConstant;
        }

    }

    /**
     * Type of Unicode code points.
     *
     * @see Character#getType(int)
     */
    @SuppressWarnings("SpellCheckingInspection")
    public enum Type {

        /**
         * General category "Cn" in the Unicode specification.
         */
        UNASSIGNED(Character.UNASSIGNED),

        /**
         * General category "Lu" in the Unicode specification.
         */
        UPPERCASE_LETTER(Character.UPPERCASE_LETTER),

        /**
         * General category "Ll" in the Unicode specification.
         */
        LOWERCASE_LETTER(Character.LOWERCASE_LETTER),

        /**
         * General category "Lt" in the Unicode specification.
         */
        TITLECASE_LETTER(Character.TITLECASE_LETTER),

        /**
         * General category "Lm" in the Unicode specification.
         */
        MODIFIER_LETTER(Character.MODIFIER_LETTER),

        /**
         * General category "Lo" in the Unicode specification.
         */
        OTHER_LETTER(Character.OTHER_LETTER),

        /**
         * General category "Mn" in the Unicode specification.
         */
        NON_SPACING_MARK(Character.NON_SPACING_MARK),

        /**
         * General category "Me" in the Unicode specification.
         */
        ENCLOSING_MARK(Character.ENCLOSING_MARK),

        /**
         * General category "Mc" in the Unicode specification.
         */
        COMBINING_SPACING_MARK(Character.COMBINING_SPACING_MARK),

        /**
         * General category "Nd" in the Unicode specification.
         */
        DECIMAL_DIGIT_NUMBER(Character.DECIMAL_DIGIT_NUMBER),

        /**
         * General category "Nl" in the Unicode specification.
         */
        LETTER_NUMBER(Character.LETTER_NUMBER),

        /**
         * General category "No" in the Unicode specification.
         */
        OTHER_NUMBER(Character.OTHER_NUMBER),

        /**
         * General category "Zs" in the Unicode specification.
         */
        SPACE_SEPARATOR(Character.SPACE_SEPARATOR),

        /**
         * General category "Zl" in the Unicode specification.
         */
        LINE_SEPARATOR(Character.LINE_SEPARATOR),

        /**
         * General category "Zp" in the Unicode specification.
         */
        PARAGRAPH_SEPARATOR(Character.PARAGRAPH_SEPARATOR),

        /**
         * General category "Cc" in the Unicode specification.
         */
        CONTROL(Character.CONTROL),

        /**
         * General category "Cf" in the Unicode specification.
         */
        FORMAT(Character.FORMAT),

        /**
         * General category "Co" in the Unicode specification.
         */
        PRIVATE_USE(Character.PRIVATE_USE),

        /**
         * General category "Cs" in the Unicode specification.
         */
        SURROGATE(Character.SURROGATE),

        /**
         * General category "Pd" in the Unicode specification.
         */
        DASH_PUNCTUATION(Character.DASH_PUNCTUATION),

        /**
         * General category "Ps" in the Unicode specification.
         */
        START_PUNCTUATION(Character.START_PUNCTUATION),

        /**
         * General category "Pe" in the Unicode specification.
         */
        END_PUNCTUATION(Character.END_PUNCTUATION),

        /**
         * General category "Pc" in the Unicode specification.
         */
        CONNECTOR_PUNCTUATION(Character.CONNECTOR_PUNCTUATION),

        /**
         * General category "Po" in the Unicode specification.
         */
        OTHER_PUNCTUATION(Character.OTHER_PUNCTUATION),

        /**
         * General category "Sm" in the Unicode specification.
         */
        MATH_SYMBOL(Character.MATH_SYMBOL),

        /**
         * General category "Sc" in the Unicode specification.
         */
        CURRENCY_SYMBOL(Character.CURRENCY_SYMBOL),

        /**
         * General category "Sk" in the Unicode specification.
         */
        MODIFIER_SYMBOL(Character.MODIFIER_SYMBOL),

        /**
         * General category "So" in the Unicode specification.
         */
        OTHER_SYMBOL(Character.OTHER_SYMBOL),

        /**
         * General category "Pi" in the Unicode specification.
         */
        INITIAL_QUOTE_PUNCTUATION(Character.INITIAL_QUOTE_PUNCTUATION),

        /**
         * General category "Pf" in the Unicode specification.
         */
        FINAL_QUOTE_PUNCTUATION(Character.FINAL_QUOTE_PUNCTUATION);

        private final int intConstant;

        Type(int intConstant) {
            this.intConstant = intConstant;
        }

        public final int intConstant() {
            return intConstant;
        }

    }

}
