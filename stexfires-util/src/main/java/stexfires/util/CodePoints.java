package stexfires.util;

import org.jetbrains.annotations.Nullable;

import java.lang.Character.UnicodeBlock;

/**
 * This class consists of {@code static} utility methods and constants
 * for operating on CodePoints.
 *
 * @see java.lang.Character
 * @see java.lang.Character.UnicodeBlock
 * @since 0.1
 */
@SuppressWarnings("SpellCheckingInspection")
public final class CodePoints {

    /**
     * The minimum ASCII code point (0).
     */
    public static final int MIN_ASCII_CODE_POINT = 0;
    /**
     * The maximum ASCII code point (127).
     */
    public static final int MAX_ASCII_CODE_POINT = 127;

    private CodePoints() {
    }

    /**
     * Returns the category or type of the code point as a {@code String}.
     *
     * @param codePoint    character (Unicode code point)
     * @param unknownValue an alternative value for unknown code points. Can be {@code null}.
     * @return the category or type of the code point as a {@code String}.
     * @see java.lang.Character#getType(int)
     */
    public static String typeAsString(int codePoint,
                                      @Nullable String unknownValue) {
        return switch (Character.getType(codePoint)) {
            case Character.COMBINING_SPACING_MARK -> "COMBINING_SPACING_MARK";
            case Character.CONNECTOR_PUNCTUATION -> "CONNECTOR_PUNCTUATION";
            case Character.CONTROL -> "CONTROL";
            case Character.CURRENCY_SYMBOL -> "CURRENCY_SYMBOL";
            case Character.DASH_PUNCTUATION -> "DASH_PUNCTUATION";
            case Character.DECIMAL_DIGIT_NUMBER -> "DECIMAL_DIGIT_NUMBER";
            case Character.ENCLOSING_MARK -> "ENCLOSING_MARK";
            case Character.END_PUNCTUATION -> "END_PUNCTUATION";
            case Character.FINAL_QUOTE_PUNCTUATION -> "FINAL_QUOTE_PUNCTUATION";
            case Character.FORMAT -> "FORMAT";
            case Character.INITIAL_QUOTE_PUNCTUATION -> "INITIAL_QUOTE_PUNCTUATION";
            case Character.LETTER_NUMBER -> "LETTER_NUMBER";
            case Character.LINE_SEPARATOR -> "LINE_SEPARATOR";
            case Character.LOWERCASE_LETTER -> "LOWERCASE_LETTER";
            case Character.MATH_SYMBOL -> "MATH_SYMBOL";
            case Character.MODIFIER_LETTER -> "MODIFIER_LETTER";
            case Character.MODIFIER_SYMBOL -> "MODIFIER_SYMBOL";
            case Character.NON_SPACING_MARK -> "NON_SPACING_MARK";
            case Character.OTHER_LETTER -> "OTHER_LETTER";
            case Character.OTHER_NUMBER -> "OTHER_NUMBER";
            case Character.OTHER_PUNCTUATION -> "OTHER_PUNCTUATION";
            case Character.OTHER_SYMBOL -> "OTHER_SYMBOL";
            case Character.PARAGRAPH_SEPARATOR -> "PARAGRAPH_SEPARATOR";
            case Character.PRIVATE_USE -> "PRIVATE_USE";
            case Character.SPACE_SEPARATOR -> "SPACE_SEPARATOR";
            case Character.START_PUNCTUATION -> "START_PUNCTUATION";
            case Character.SURROGATE -> "SURROGATE";
            case Character.TITLECASE_LETTER -> "TITLECASE_LETTER";
            case Character.UNASSIGNED -> "UNASSIGNED";
            case Character.UPPERCASE_LETTER -> "UPPERCASE_LETTER";
            default -> unknownValue;
        };
    }

    /**
     * Returns the directionality of the code point as a {@code String}.
     *
     * @param codePoint    character (Unicode code point)
     * @param unknownValue an alternative value for unknown code points. Can be {@code null}.
     * @return the directionality of the code point as a {@code String}.
     * @see java.lang.Character#getDirectionality(int)
     */
    public static String directionalityAsString(int codePoint,
                                                @Nullable String unknownValue) {
        return switch (Character.getDirectionality(codePoint)) {
            case Character.DIRECTIONALITY_UNDEFINED -> "DIRECTIONALITY_UNDEFINED";
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT -> "DIRECTIONALITY_LEFT_TO_RIGHT";
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT -> "DIRECTIONALITY_RIGHT_TO_LEFT";
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC -> "DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC";
            case Character.DIRECTIONALITY_EUROPEAN_NUMBER -> "DIRECTIONALITY_EUROPEAN_NUMBER";
            case Character.DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR -> "DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR";
            case Character.DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR -> "DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR";
            case Character.DIRECTIONALITY_ARABIC_NUMBER -> "DIRECTIONALITY_ARABIC_NUMBER";
            case Character.DIRECTIONALITY_COMMON_NUMBER_SEPARATOR -> "DIRECTIONALITY_COMMON_NUMBER_SEPARATOR";
            case Character.DIRECTIONALITY_NONSPACING_MARK -> "DIRECTIONALITY_NONSPACING_MARK";
            case Character.DIRECTIONALITY_BOUNDARY_NEUTRAL -> "DIRECTIONALITY_BOUNDARY_NEUTRAL";
            case Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR -> "DIRECTIONALITY_PARAGRAPH_SEPARATOR";
            case Character.DIRECTIONALITY_SEGMENT_SEPARATOR -> "DIRECTIONALITY_SEGMENT_SEPARATOR";
            case Character.DIRECTIONALITY_WHITESPACE -> "DIRECTIONALITY_WHITESPACE";
            case Character.DIRECTIONALITY_OTHER_NEUTRALS -> "DIRECTIONALITY_OTHER_NEUTRALS";
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING -> "DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING";
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE -> "DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE";
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING -> "DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING";
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE -> "DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE";
            case Character.DIRECTIONALITY_POP_DIRECTIONAL_FORMAT -> "DIRECTIONALITY_POP_DIRECTIONAL_FORMAT";
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT_ISOLATE -> "DIRECTIONALITY_LEFT_TO_RIGHT_ISOLATE";
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_ISOLATE -> "DIRECTIONALITY_RIGHT_TO_LEFT_ISOLATE";
            case Character.DIRECTIONALITY_FIRST_STRONG_ISOLATE -> "DIRECTIONALITY_FIRST_STRONG_ISOLATE";
            case Character.DIRECTIONALITY_POP_DIRECTIONAL_ISOLATE -> "DIRECTIONALITY_POP_DIRECTIONAL_ISOLATE";
            default -> unknownValue;
        };
    }

    /**
     * Returns the Unicode block containing the given code point as a {@code String}.
     *
     * @param codePoint    character (Unicode code point)
     * @param unknownValue an alternative value for unknown or invalid code points. Can be {@code null}.
     * @return the Unicode block containing the given code point as a {@code String}.
     * @see java.lang.Character.UnicodeBlock#of(int)
     */
    public static String unicodeBlockAsString(int codePoint,
                                              @Nullable String unknownValue) {
        try {
            UnicodeBlock unicodeBlock = UnicodeBlock.of(codePoint);
            return (unicodeBlock == null) ? unknownValue : unicodeBlock.toString();
        } catch (IllegalArgumentException e) {
            return unknownValue;
        }
    }

    /**
     * Returns the String representation of the given code point if it is printable.
     * <p>
     * A code point is printable if it is valid and defined and its type is not
     * {@link Character#UNASSIGNED}, {@link Character#CONTROL}, {@link Character#SURROGATE} or
     * {@link Character#PRIVATE_USE}.
     *
     * @param codePoint         character (Unicode code point)
     * @param notPrintableValue an alternative value for non-printable code points. Can be {@code null}.
     * @return the String representation of the given code point if it is printable.
     * @see java.lang.Character#getType(int)
     * @see java.lang.Character#isValidCodePoint(int)
     * @see java.lang.Character#isDefined(int)
     * @see java.lang.Character#toString(int)
     */
    public static String toPrintableString(int codePoint,
                                           @Nullable String notPrintableValue) {
        try {
            int characterType = Character.getType(codePoint);
            return characterType == Character.UNASSIGNED
                    || characterType == Character.CONTROL
                    || characterType == Character.SURROGATE
                    || characterType == Character.PRIVATE_USE
                    || !Character.isValidCodePoint(codePoint)
                    || !Character.isDefined(codePoint)
                    ? notPrintableValue
                    : Character.toString(codePoint);
        } catch (IllegalArgumentException e) {
            return notPrintableValue;
        }
    }

}
