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
    public static final int INDEX_DECIMAL_DIGIT = 4;
    public static final int INDEX_NUMERIC_VALUE = 5;
    public static final int INDEX_CHARACTER_NAME = 6;
    public static final int INDEX_TYPE = 7;
    public static final int INDEX_BLOCK = 8;
    public static final int INDEX_SCRIPT = 9;
    public static final int INDEX_DIRECTIONALITY = 10;
    public static final int INDEX_IS_DEFINED = 11;
    public static final int INDEX_IS_MIRRORED = 12;
    public static final int INDEX_IS_ISO_CONTROL = 13;
    public static final int INDEX_IS_ALPHABETIC = 14;
    public static final int INDEX_IS_LETTER = 15;
    public static final int INDEX_IS_SPACE_CHAR = 16;
    public static final int INDEX_IS_DIGIT = 17;

    private CodePointRecordHelper() {
    }

    public static TextRecord generateCodePointRecord(int codePoint,
                                                     String notPrintableValue,
                                                     String unknownValue) {
        CodePoint cp = new CodePoint(codePoint);
        return new ManyFieldsRecord(
                // category
                cp.type().name(),
                // recordId
                (long) cp.value(),
                // text fields
                String.valueOf(cp.value()),
                cp.hexString(),
                cp.printableString().orElse(notPrintableValue),
                String.valueOf(cp.charCount()),
                cp.decimalDigit().map(String::valueOf).orElse(unknownValue),
                cp.numericValue().map(String::valueOf).orElse(unknownValue),
                cp.name().orElse(unknownValue),
                cp.type().name(),
                cp.unicodeBlock().map(Character.UnicodeBlock::toString).orElse(unknownValue),
                cp.unicodeScript().name(),
                cp.directionality().name(),
                String.valueOf(Character.isDefined(cp.value())),
                String.valueOf(Character.isMirrored(cp.value())),
                String.valueOf(Character.isISOControl(cp.value())),
                String.valueOf(Character.isAlphabetic(cp.value())),
                String.valueOf(Character.isLetter(cp.value())),
                String.valueOf(Character.isSpaceChar(cp.value())),
                String.valueOf(Character.isDigit(cp.value()))
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
