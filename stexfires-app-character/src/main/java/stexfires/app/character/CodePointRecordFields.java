package stexfires.app.character;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.util.Alignment;
import stexfires.util.CodePoint;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public enum CodePointRecordFields {

    PRINTABLE_STRING("Char", 5, Alignment.START, (codePoint, alternativeValue) -> codePoint.printableString().orElse(alternativeValue)),
    CODE_POINT("Decimal", 7, Alignment.END, (codePoint, alternativeValue) -> String.valueOf(codePoint.value())),
    HEX_STRING("Hex", 5, Alignment.END, (codePoint, alternativeValue) -> codePoint.hexString()),
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
    IS_DIGIT("Digit", 5, Alignment.START, (codePoint, alternativeValue) -> String.valueOf(codePoint.isDigit()));

    private final String fieldName;
    private final int minWidth;
    private final Alignment alignment;
    private final BiFunction<CodePoint, String, String> convertFunction;

    CodePointRecordFields(String fieldName,
                          int minWidth,
                          Alignment alignment,
                          BiFunction<CodePoint, String, String> convertFunction) {
        this.fieldName = fieldName;
        this.minWidth = minWidth;
        this.alignment = alignment;
        this.convertFunction = convertFunction;
    }

    public static TextRecord generateCodePointRecord(@NotNull CodePoint codePoint,
                                                     @Nullable String alternativeValue) {
        Objects.requireNonNull(codePoint);
        return new ManyFieldsRecord(
                // category
                codePoint.type().name(),
                // recordId
                (long) codePoint.value(),
                // text fields
                Arrays.stream(values()).map(field -> field.convert(codePoint, alternativeValue)).collect(Collectors.toList())
        );
    }

    public static Stream<TextRecord> generateCodePointRecordStream(int lowestCodePoint,
                                                                   int highestCodePoint,
                                                                   @Nullable String alternativeValue) {
        return IntStream.rangeClosed(lowestCodePoint, highestCodePoint)
                        .filter(Character::isValidCodePoint)
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

    public String convert(@NotNull CodePoint codePoint, @Nullable String alternativeValue) {
        Objects.requireNonNull(codePoint);
        return convertFunction.apply(codePoint, alternativeValue);
    }

}
