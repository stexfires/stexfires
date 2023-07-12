package stexfires.util;

import org.jetbrains.annotations.Nullable;

import java.lang.Character.UnicodeBlock;

/**
 * The record represents a Unicode code point.
 * It consists of an {@code int} value between {@link #MIN_VALUE} and {@link #MAX_VALUE}.
 *
 * @see java.lang.Character
 * @since 0.1
 */
public record CodePoint(int value) {

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
     * Returns the type of the code point as an {@code int}.
     *
     * @return the type of the code point as an {@code int}.
     * @see java.lang.Character#getType(int)
     */
    public int typeAsInt() {
        return Character.getType(value);
    }

    /**
     * Returns the type of the code point as a {@code String}.
     *
     * @return the type of the code point as a {@code String}.
     * @see java.lang.Character#getType(int)
     * @see stexfires.util.CodePoint.Type#name()
     */
    public String typeAsString() {
        return type().name();
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
     * Returns the directionality of the code point as a {@code byte}.
     *
     * @return the directionality of the code point as a {@code byte}.
     * @see java.lang.Character#getDirectionality(int)
     */
    public byte directionalityAsByte() {
        return Character.getDirectionality(value);
    }

    /**
     * Returns the directionality of the code point as a {@code String}.
     *
     * @return the directionality of the code point as a {@code String}.
     * @see java.lang.Character#getDirectionality(int)
     * @see stexfires.util.CodePoint.Directionality#name()
     */
    public String directionalityAsString() {
        return directionality().name();
    }

    /**
     * Returns the Unicode block containing the given code point,
     * or {@code null} if the code point is not a member of a defined block.
     *
     * @return the Unicode block containing the given code point, or {@code null} if the code point is not a member of a defined block.
     * @see java.lang.Character.UnicodeBlock#of(int)
     */
    public @Nullable UnicodeBlock unicodeBlock() {
        return UnicodeBlock.of(value);
    }

    /**
     * Returns the Unicode block containing the given code point as a {@code String}.
     *
     * @param unknownValue an alternative value for code points that are not a member of a defined block. Can be {@code null}.
     * @return the Unicode block containing the given code point as a {@code String}. Can be {@code null}.
     * @see java.lang.Character.UnicodeBlock#of(int)
     */
    public @Nullable String unicodeBlockAsString(@Nullable String unknownValue) {
        try {
            UnicodeBlock unicodeBlock = UnicodeBlock.of(value);
            return (unicodeBlock == null) ? unknownValue : unicodeBlock.toString();
        } catch (IllegalArgumentException e) {
            // Should not happen
            return unknownValue;
        }
    }

    /**
     * Returns the String representation of the given code point if it is printable.
     * <p>
     * A code point is printable if its type is not {@link Character#UNASSIGNED}, {@link Character#CONTROL},
     * {@link Character#SURROGATE} or {@link Character#PRIVATE_USE}.
     *
     * @param notPrintableValue an alternative value for non-printable code points. Can be {@code null}.
     * @return the String representation of the given code point if it is printable. Can be {@code null}.
     * @see java.lang.Character#getType(int)
     * @see java.lang.Character#toString(int)
     */
    public @Nullable String toPrintableString(@Nullable String notPrintableValue) {
        try {
            int characterType = Character.getType(value);
            return characterType == Character.UNASSIGNED
                    || characterType == Character.CONTROL
                    || characterType == Character.SURROGATE
                    || characterType == Character.PRIVATE_USE
                    ? notPrintableValue
                    : Character.toString(value);
        } catch (IllegalArgumentException e) {
            // Should not happen
            return notPrintableValue;
        }
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
