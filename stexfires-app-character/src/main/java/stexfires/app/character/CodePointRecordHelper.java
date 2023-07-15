package stexfires.app.character;

import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.util.CodePoint;

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
    public static final int INDEX_DECIMAL_DIGIT = 16;
    public static final int INDEX_NUMERIC_VALUE = 17;

    private CodePointRecordHelper() {
    }

    public static TextRecord generateCodePointRecord(int codePoint,
                                                     String notPrintableValue,
                                                     String unknownValue) {
        CodePoint cp = new CodePoint(codePoint);
        return new ManyFieldsRecord(
                // category
                cp.typeAsString(),
                // recordId
                (long) cp.value(),
                // text fields
                String.valueOf(cp.value()),
                cp.hexString(),
                cp.toPrintableString(notPrintableValue),
                String.valueOf(cp.charCount()),
                cp.name(),
                cp.typeAsString(),
                cp.unicodeBlockAsString(unknownValue),
                cp.directionalityAsString(),
                String.valueOf(Character.isDefined(cp.value())),
                String.valueOf(Character.isValidCodePoint(cp.value())),
                String.valueOf(Character.isMirrored(cp.value())),
                String.valueOf(Character.isISOControl(cp.value())),
                String.valueOf(Character.isAlphabetic(cp.value())),
                String.valueOf(Character.isLetter(cp.value())),
                String.valueOf(Character.isSpaceChar(cp.value())),
                String.valueOf(Character.isDigit(cp.value())),
                cp.decimalDigit().map(String::valueOf).orElse(unknownValue),
                cp.numericValue().map(String::valueOf).orElse(unknownValue)
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
