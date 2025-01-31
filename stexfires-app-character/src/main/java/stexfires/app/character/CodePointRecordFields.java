package stexfires.app.character;

import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.util.Alignment;
import stexfires.util.CodePoint;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * @since 0.1
 */
public enum CodePointRecordFields {

    PRINTABLE_STRING("Char", 5, Alignment.START, (codePoint, alternativeValue) -> codePoint.printableString().orElse(alternativeValue)),
    CODE_POINT("Decimal", 7, Alignment.END, (codePoint, alternativeValue) -> String.valueOf(codePoint.value())),
    HEX_STRING("Hex", 5, Alignment.END, (codePoint, alternativeValue) -> codePoint.hexString()),
    UNICODE_ESCAPES("Unicode Escapes", 15, Alignment.START, (codePoint, alternativeValue) -> codePoint.unicodeEscapes()),
    CHAR_COUNT("Count", 5, Alignment.END, (codePoint, alternativeValue) -> String.valueOf(codePoint.charCount())),
    NUMERIC_VALUE("NumVal", 10, Alignment.END, (codePoint, alternativeValue) -> codePoint.numericValue().map(String::valueOf).orElse(alternativeValue)),
    DECIMAL_DIGIT("Digit", 7, Alignment.END, (codePoint, alternativeValue) -> codePoint.decimalDigit().map(String::valueOf).orElse(alternativeValue)),
    NAME("Name", 75, Alignment.START, (codePoint, alternativeValue) -> codePoint.name().orElse(alternativeValue)),
    UNICODE_BLOCK("Unicode Block", 45, Alignment.START, (codePoint, alternativeValue) -> codePoint.unicodeBlock().map(Character.UnicodeBlock::toString).orElse(alternativeValue)),
    UNICODE_SCRIPT("Unicode Script", 24, Alignment.START, (codePoint, alternativeValue) -> codePoint.unicodeScript().name()),
    TYPE("Type", 30, Alignment.START, (codePoint, alternativeValue) -> codePoint.type().name()),
    DIRECTIONALITY("Directionality", 45, Alignment.START, (codePoint, alternativeValue) -> codePoint.directionality().name()),
    IS_DEFINED("Defined", 7, Alignment.START, (codePoint, alternativeValue) -> String.valueOf(codePoint.isDefined())),
    IS_ISO_CONTROL("ISOControl", 10, Alignment.START, (codePoint, alternativeValue) -> String.valueOf(codePoint.isISOControl())),
    IS_MIRRORED("Mirrored", 8, Alignment.START, (codePoint, alternativeValue) -> String.valueOf(codePoint.isMirrored())),
    IS_IDEOGRAPHIC("Ideographic", 11, Alignment.START, (codePoint, alternativeValue) -> String.valueOf(codePoint.isIdeographic())),
    IS_LETTER("Letter", 6, Alignment.START, (codePoint, alternativeValue) -> String.valueOf(codePoint.isLetter())),
    IS_ALPHABETIC("Alphabetic", 10, Alignment.START, (codePoint, alternativeValue) -> String.valueOf(codePoint.isAlphabetic())),
    IS_SPACE_CHAR("SpaceChar", 9, Alignment.START, (codePoint, alternativeValue) -> String.valueOf(codePoint.isSpaceChar())),
    IS_DIGIT("Digit", 5, Alignment.START, (codePoint, alternativeValue) -> String.valueOf(codePoint.isDigit())),
    IS_EMOJI("Emoji", 5, Alignment.START, (codePoint, alternativeValue) -> String.valueOf(codePoint.isEmoji()));

    private final String fieldName;
    private final int minWidth;
    private final Alignment alignment;
    private final BiFunction<CodePoint, String, String> convertFunction;

    CodePointRecordFields(String fieldName,
                          int minWidth,
                          Alignment alignment,
                          BiFunction<CodePoint, String, String> convertFunction) {
        Objects.requireNonNull(fieldName);
        if (fieldName.isBlank()) {
            throw new IllegalArgumentException("fieldName must not be blank! fieldName=" + fieldName);
        }
        if (minWidth < 5) {
            throw new IllegalArgumentException("minWidth must be >= 5! minWidth=" + minWidth);
        }
        Objects.requireNonNull(alignment);
        Objects.requireNonNull(convertFunction);
        this.fieldName = fieldName;
        this.minWidth = minWidth;
        this.alignment = alignment;
        this.convertFunction = convertFunction;
    }

    public static TextRecord generateCodePointRecord(CodePoint codePoint,
                                                     String alternativeValue) {
        Objects.requireNonNull(codePoint);
        Objects.requireNonNull(alternativeValue);
        return new ManyFieldsRecord(
                // category
                codePoint.type().name(),
                // recordId
                Long.valueOf(codePoint.value()),
                // text fields
                Arrays.stream(values()).map(field -> field.convert(codePoint, alternativeValue)));
    }

    public static Stream<TextRecord> generateCodePointRecordStream(int lowestCodePoint,
                                                                   int highestCodePoint,
                                                                   String alternativeValue) {
        if (lowestCodePoint > highestCodePoint) {
            throw new IllegalArgumentException("lowestCodePoint must be <= highestCodePoint! lowestCodePoint=" + lowestCodePoint + " highestCodePoint=" + highestCodePoint);
        }
        if (!Character.isValidCodePoint(lowestCodePoint)) {
            throw new IllegalArgumentException("lowestCodePoint must be a valid code point! lowestCodePoint=" + lowestCodePoint);
        }
        if (!Character.isValidCodePoint(highestCodePoint)) {
            throw new IllegalArgumentException("highestCodePoint must be a valid code point! highestCodePoint=" + highestCodePoint);
        }
        Objects.requireNonNull(alternativeValue);
        return IntStream.rangeClosed(lowestCodePoint, highestCodePoint)
                        .mapToObj(codePoint ->
                                generateCodePointRecord(new CodePoint(codePoint), alternativeValue));
    }

    public String fieldName() {
        return fieldName;
    }

    public int minWidth() {
        return minWidth;
    }

    public Alignment alignment() {
        return alignment;
    }

    public String convert(CodePoint codePoint,
                          String alternativeValue) {
        Objects.requireNonNull(codePoint);
        Objects.requireNonNull(alternativeValue);
        return convertFunction.apply(codePoint, alternativeValue);
    }

}
