package stexfires.app.character;

import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.util.CodePoints;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public final class CodePointRecordHelper {

    public static final int INDEX_CODE_POINT = 0;
    public static final int INDEX_HEX_STRING = 1;
    public static final int INDEX_PRINTABLE_STRING = 2;
    public static final int INDEX_CHARACTER_COUNT = 3;
    public static final int INDEX_CHARACTER_NAME = 4;
    public static final int INDEX_TYPE = 5;
    public static final int INDEX_BLOCK = 6;
    public static final int INDEX_DIRECTIONALITY = 7;
    public static final int INDEX_IS_DEFINED = 8;
    public static final int INDEX_IS_VALID_CODE_POINT = 9;
    public static final int INDEX_IS_MIRRORED = 10;
    public static final int INDEX_IS_ISO_CONTROL = 11;
    public static final int INDEX_IS_ALPHABETIC = 12;
    public static final int INDEX_IS_LETTER = 13;
    public static final int INDEX_IS_SPACE_CHAR = 14;
    public static final int INDEX_IS_DIGIT = 15;
    public static final int INDEX_DIGIT_VALUE = 16;
    public static final int INDEX_NUMERIC_VALUE = 17;

    private CodePointRecordHelper() {
    }

    public static TextRecord generateCodePointRecord(int codePoint,
                                                     String notPrintableValue,
                                                     String unknownValue) {
        return new ManyFieldsRecord(
                // category
                CodePoints.typeAsString(codePoint, unknownValue),
                // recordId
                (long) codePoint,
                // text fields
                String.valueOf(codePoint),
                Integer.toHexString(codePoint),
                CodePoints.toPrintableString(codePoint, notPrintableValue),
                String.valueOf(Character.charCount(codePoint)),
                Character.isValidCodePoint(codePoint) ? Character.getName(codePoint) : unknownValue,
                CodePoints.typeAsString(codePoint, unknownValue),
                CodePoints.unicodeBlockAsString(codePoint, unknownValue),
                CodePoints.directionalityAsString(codePoint, unknownValue),
                String.valueOf(Character.isDefined(codePoint)),
                String.valueOf(Character.isValidCodePoint(codePoint)),
                String.valueOf(Character.isMirrored(codePoint)),
                String.valueOf(Character.isISOControl(codePoint)),
                String.valueOf(Character.isAlphabetic(codePoint)),
                String.valueOf(Character.isLetter(codePoint)),
                String.valueOf(Character.isSpaceChar(codePoint)),
                String.valueOf(Character.isDigit(codePoint)),
                Character.digit(codePoint, 10) == -1 ? unknownValue :
                        String.valueOf(Character.digit(codePoint, 10)),
                Character.getNumericValue(codePoint) == -2 ? notPrintableValue :
                        Character.getNumericValue(codePoint) == -1 ? unknownValue :
                                String.valueOf(Character.getNumericValue(codePoint))
        );
    }

    public static Stream<TextRecord> generateCodePointRecordStream(int lowestCodePoint,
                                                                   int highestCodePoint,
                                                                   String notPrintableValue,
                                                                   String unknownValue) {
        return IntStream.rangeClosed(lowestCodePoint, highestCodePoint)
                        .mapToObj(codePoint ->
                                CodePointRecordHelper.generateCodePointRecord(
                                        codePoint,
                                        notPrintableValue,
                                        unknownValue));
    }

}
