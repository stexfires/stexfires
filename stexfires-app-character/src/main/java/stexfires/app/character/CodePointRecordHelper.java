package stexfires.app.character;

import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;

import java.lang.Character.UnicodeBlock;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
@SuppressWarnings("SpellCheckingInspection")
public final class CodePointRecordHelper {

    public static final int INDEX_CODE_POINT = 0;
    public static final int INDEX_HEX_STRING = 1;
    public static final int INDEX_PRINTABLE_STRING = 2;
    public static final int INDEX_CHARACTER_COUNT = 3;
    public static final int INDEX_CHARACTER_NAME = 4;
    public static final int INDEX_IS_DEFINED = 5;
    public static final int INDEX_IS_VALID_CODE_POINT = 6;
    public static final int INDEX_IS_MIRRORED = 7;
    public static final int INDEX_IS_ISO_CONTROL = 8;
    public static final int INDEX_IS_ALPHABETIC = 9;
    public static final int INDEX_IS_LETTER = 10;
    public static final int INDEX_IS_SPACE_CHAR = 11;
    public static final int INDEX_IS_DIGIT = 12;
    public static final int INDEX_DIGIT_VALUE = 13;
    public static final int INDEX_NUMERIC_VALUE = 14;
    public static final int INDEX_TYPE = 15;
    public static final int INDEX_BLOCK = 16;
    public static final int INDEX_DIRECTIONALITY = 17;

    private CodePointRecordHelper() {
    }

    public static String codePointCharacterTypeAsString(int codePoint,
                                                        String missing) {
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
            default -> missing;
        };
    }

    public static String codePointDirectionalityAsString(int codePoint,
                                                         String missing) {
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
            default -> missing;
        };
    }

    public static String codePointUnicodeBlockAsString(int codePoint,
                                                       String missing) {
        UnicodeBlock unicodeBlock = UnicodeBlock.of(codePoint);
        return (unicodeBlock == null) ? missing : unicodeBlock.toString();
    }

    private static String codePointAsPrintableString(int codePoint,
                                                     String notPrintable) {
        int characterType = Character.getType(codePoint);
        return (characterType == Character.CONTROL || characterType == Character.SURROGATE
                || characterType == Character.UNASSIGNED || characterType == Character.PRIVATE_USE)
                ? notPrintable : Character.toString(codePoint);
    }

    public static TextRecord generateCodePointRecord(int codePoint,
                                                     String notPrintable,
                                                     String missing) {
        return new ManyFieldsRecord(
                // category
                String.valueOf(Character.getType(codePoint)),
                // recordId
                (long) codePoint,
                // text fields
                String.valueOf(codePoint),
                Integer.toHexString(codePoint),
                codePointAsPrintableString(codePoint, notPrintable),
                String.valueOf(Character.charCount(codePoint)),
                Character.getName(codePoint),
                String.valueOf(Character.isDefined(codePoint)),
                String.valueOf(Character.isValidCodePoint(codePoint)),
                String.valueOf(Character.isMirrored(codePoint)),
                String.valueOf(Character.isISOControl(codePoint)),
                String.valueOf(Character.isAlphabetic(codePoint)),
                String.valueOf(Character.isLetter(codePoint)),
                String.valueOf(Character.isSpaceChar(codePoint)),
                String.valueOf(Character.isDigit(codePoint)),
                String.valueOf(Character.digit(codePoint, 10)),
                String.valueOf(Character.getNumericValue(codePoint)),
                codePointCharacterTypeAsString(codePoint, missing),
                codePointUnicodeBlockAsString(codePoint, missing),
                codePointDirectionalityAsString(Character.getDirectionality(codePoint), missing)
        );
    }

    public static Stream<TextRecord> generateCodePointRecordStream(int lowestCodePoint,
                                                                   int highestCodePoint,
                                                                   String notPrintable,
                                                                   String missing) {
        return IntStream.rangeClosed(lowestCodePoint, highestCodePoint)
                        .mapToObj(codePoint -> CodePointRecordHelper.generateCodePointRecord(codePoint,
                                notPrintable, missing));
    }

}
