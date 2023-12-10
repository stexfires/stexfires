package stexfires.app.character;

import org.junit.jupiter.api.Test;
import stexfires.record.TextRecords;
import stexfires.util.CodePoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests for {@link CodePointRecordFields}.
 */
@SuppressWarnings({"MagicNumber"})
class CodePointRecordFieldsTest {

    /**
     * Test method for {@link CodePointRecordFields#generateCodePointRecord(stexfires.util.CodePoint, String)}.
     */
    @Test
    void generateCodePointRecord_0() {
        int codePointValue = 0;
        CodePoint codePoint = new CodePoint(codePointValue);
        assertEquals("CONTROL", CodePointRecordFields.generateCodePointRecord(codePoint, "").category());
        assertEquals(codePointValue, CodePointRecordFields.generateCodePointRecord(codePoint, "").recordId());
        assertEquals(21, CodePointRecordFields.generateCodePointRecord(codePoint, "").size());
        assertEquals("0", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.CODE_POINT.ordinal()));
        assertEquals("0", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.HEX_STRING.ordinal()));
        assertEquals("\\u0000", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_ESCAPES.ordinal()));
        assertEquals("", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.PRINTABLE_STRING.ordinal()));
        assertEquals("1", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.CHAR_COUNT.ordinal()));
        assertEquals("NULL", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.NAME.ordinal()));
        assertEquals("CONTROL", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.TYPE.ordinal()));
        assertEquals("BASIC_LATIN", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_BLOCK.ordinal()));
        assertEquals("COMMON", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_SCRIPT.ordinal()));
        assertEquals("DIRECTIONALITY_BOUNDARY_NEUTRAL", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.DIRECTIONALITY.ordinal()));
        assertEquals("true", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_DEFINED.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_MIRRORED.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_IDEOGRAPHIC.ordinal()));
        assertEquals("true", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_ISO_CONTROL.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_ALPHABETIC.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_LETTER.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_SPACE_CHAR.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_DIGIT.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_EMOJI.ordinal()));
        assertEquals("", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.DECIMAL_DIGIT.ordinal()));
        assertEquals("", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.NUMERIC_VALUE.ordinal()));

        assertNull(CodePointRecordFields.generateCodePointRecord(codePoint, null).textAt(CodePointRecordFields.PRINTABLE_STRING.ordinal()));
    }

    /**
     * Test method for {@link CodePointRecordFields#generateCodePointRecord(stexfires.util.CodePoint, String)} with codePoint 32.
     */
    @Test
    void generateCodePointRecord_32() {
        int codePointValue = 32;
        CodePoint codePoint = new CodePoint(codePointValue);
        assertEquals("SPACE_SEPARATOR", CodePointRecordFields.generateCodePointRecord(codePoint, "").category());
        assertEquals(codePointValue, CodePointRecordFields.generateCodePointRecord(codePoint, "").recordId());
        assertEquals(21, CodePointRecordFields.generateCodePointRecord(codePoint, "").size());
        assertEquals("32", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.CODE_POINT.ordinal()));
        assertEquals("20", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.HEX_STRING.ordinal()));
        assertEquals("\\u0020", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_ESCAPES.ordinal()));
        assertEquals(" ", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.PRINTABLE_STRING.ordinal()));
        assertEquals("1", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.CHAR_COUNT.ordinal()));
        assertEquals("SPACE", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.NAME.ordinal()));
        assertEquals("SPACE_SEPARATOR", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.TYPE.ordinal()));
        assertEquals("BASIC_LATIN", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_BLOCK.ordinal()));
        assertEquals("COMMON", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_SCRIPT.ordinal()));
        assertEquals("DIRECTIONALITY_WHITESPACE", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.DIRECTIONALITY.ordinal()));
        assertEquals("true", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_DEFINED.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_MIRRORED.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_IDEOGRAPHIC.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_ISO_CONTROL.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_ALPHABETIC.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_LETTER.ordinal()));
        assertEquals("true", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_SPACE_CHAR.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_DIGIT.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_EMOJI.ordinal()));
        assertEquals("", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.DECIMAL_DIGIT.ordinal()));
        assertEquals("", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.NUMERIC_VALUE.ordinal()));
    }

    /**
     * Test method for {@link CodePointRecordFields#generateCodePointRecord(stexfires.util.CodePoint, String)} with codePoint 55.
     */
    @Test
    void generateCodePointRecord_55() {
        int codePointValue = 55;
        CodePoint codePoint = new CodePoint(codePointValue);
        assertEquals("DECIMAL_DIGIT_NUMBER", CodePointRecordFields.generateCodePointRecord(codePoint, "").category());
        assertEquals(codePointValue, CodePointRecordFields.generateCodePointRecord(codePoint, "").recordId());
        assertEquals(21, CodePointRecordFields.generateCodePointRecord(codePoint, "").size());
        assertEquals("55", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.CODE_POINT.ordinal()));
        assertEquals("37", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.HEX_STRING.ordinal()));
        assertEquals("\\u0037", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_ESCAPES.ordinal()));
        assertEquals("7", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.PRINTABLE_STRING.ordinal()));
        assertEquals("1", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.CHAR_COUNT.ordinal()));
        assertEquals("DIGIT SEVEN", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.NAME.ordinal()));
        assertEquals("DECIMAL_DIGIT_NUMBER", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.TYPE.ordinal()));
        assertEquals("BASIC_LATIN", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_BLOCK.ordinal()));
        assertEquals("COMMON", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_SCRIPT.ordinal()));
        assertEquals("DIRECTIONALITY_EUROPEAN_NUMBER", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.DIRECTIONALITY.ordinal()));
        assertEquals("true", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_DEFINED.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_MIRRORED.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_IDEOGRAPHIC.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_ISO_CONTROL.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_ALPHABETIC.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_LETTER.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_SPACE_CHAR.ordinal()));
        assertEquals("true", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_DIGIT.ordinal()));
        assertEquals("true", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_EMOJI.ordinal()));
        assertEquals("7", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.DECIMAL_DIGIT.ordinal()));
        assertEquals("7", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.NUMERIC_VALUE.ordinal()));
    }

    /**
     * Test method for {@link CodePointRecordFields#generateCodePointRecord(stexfires.util.CodePoint, String)} with codePoint 65.
     */
    @Test
    void generateCodePointRecord_65() {
        int codePointValue = 65;
        CodePoint codePoint = new CodePoint(codePointValue);
        assertEquals("UPPERCASE_LETTER", CodePointRecordFields.generateCodePointRecord(codePoint, "").category());
        assertEquals(codePointValue, CodePointRecordFields.generateCodePointRecord(codePoint, "").recordId());
        assertEquals(21, CodePointRecordFields.generateCodePointRecord(codePoint, "").size());
        assertEquals("65", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.CODE_POINT.ordinal()));
        assertEquals("41", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.HEX_STRING.ordinal()));
        assertEquals("\\u0041", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_ESCAPES.ordinal()));
        assertEquals("A", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.PRINTABLE_STRING.ordinal()));
        assertEquals("1", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.CHAR_COUNT.ordinal()));
        assertEquals("LATIN CAPITAL LETTER A", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.NAME.ordinal()));
        assertEquals("UPPERCASE_LETTER", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.TYPE.ordinal()));
        assertEquals("BASIC_LATIN", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_BLOCK.ordinal()));
        assertEquals("LATIN", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_SCRIPT.ordinal()));
        assertEquals("DIRECTIONALITY_LEFT_TO_RIGHT", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.DIRECTIONALITY.ordinal()));
        assertEquals("true", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_DEFINED.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_MIRRORED.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_IDEOGRAPHIC.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_ISO_CONTROL.ordinal()));
        assertEquals("true", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_ALPHABETIC.ordinal()));
        assertEquals("true", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_LETTER.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_SPACE_CHAR.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_DIGIT.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_EMOJI.ordinal()));
        assertEquals("", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.DECIMAL_DIGIT.ordinal()));
        assertEquals("10", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.NUMERIC_VALUE.ordinal()));
    }

    /**
     * Test method for {@link CodePointRecordFields#generateCodePointRecord(stexfires.util.CodePoint, String)} with codePoint 127794.
     */
    @Test
    void generateCodePointRecord_127794() {
        int codePointValue = 127794;
        CodePoint codePoint = new CodePoint(codePointValue);
        assertEquals("OTHER_SYMBOL", CodePointRecordFields.generateCodePointRecord(codePoint, "").category());
        assertEquals(codePointValue, CodePointRecordFields.generateCodePointRecord(codePoint, "").recordId());
        assertEquals(21, CodePointRecordFields.generateCodePointRecord(codePoint, "").size());
        assertEquals("127794", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.CODE_POINT.ordinal()));
        assertEquals("1f332", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.HEX_STRING.ordinal()));
        assertEquals("\\ud83c\\udf32", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_ESCAPES.ordinal()));
        assertEquals("\uD83C\uDF32", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.PRINTABLE_STRING.ordinal()));
        assertEquals("2", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.CHAR_COUNT.ordinal()));
        assertEquals("EVERGREEN TREE", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.NAME.ordinal()));
        assertEquals("OTHER_SYMBOL", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.TYPE.ordinal()));
        assertEquals("MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_BLOCK.ordinal()));
        assertEquals("COMMON", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.UNICODE_SCRIPT.ordinal()));
        assertEquals("DIRECTIONALITY_OTHER_NEUTRALS", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.DIRECTIONALITY.ordinal()));
        assertEquals("true", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_DEFINED.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_MIRRORED.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_IDEOGRAPHIC.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_ISO_CONTROL.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_ALPHABETIC.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_LETTER.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_SPACE_CHAR.ordinal()));
        assertEquals("false", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_DIGIT.ordinal()));
        assertEquals("true", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.IS_EMOJI.ordinal()));
        assertEquals("", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.DECIMAL_DIGIT.ordinal()));
        assertEquals("", CodePointRecordFields.generateCodePointRecord(codePoint, "").textAt(CodePointRecordFields.NUMERIC_VALUE.ordinal()));
    }

    /**
     * Test method for {@link CodePointRecordFields#generateCodePointRecordStream(int, int, String)}.
     */
    @Test
    void generateCodePointRecordStream_count() {
        assertEquals(1, CodePointRecordFields.generateCodePointRecordStream(0, 0, "").count());
        assertEquals(2, CodePointRecordFields.generateCodePointRecordStream(0, 1, "").count());
        assertEquals(3, CodePointRecordFields.generateCodePointRecordStream(0, 2, "").count());
        assertEquals(0, CodePointRecordFields.generateCodePointRecordStream(1, 0, "").count());
        assertEquals(1, CodePointRecordFields.generateCodePointRecordStream(2, 2, "").count());
        assertEquals(128, CodePointRecordFields.generateCodePointRecordStream(0, 127, "").count());
    }

    /**
     * Test method for {@link CodePointRecordFields#generateCodePointRecordStream(int, int, String)}.
     */
    @Test
    void generateCodePointRecordStream_findFirst() {
        for (int codePointValue = 0; codePointValue < 256; codePointValue++) {
            assertEquals(codePointValue, CodePointRecordFields.generateCodePointRecordStream(codePointValue, codePointValue, "").findFirst().orElseGet(TextRecords::empty).recordId());
            assertEquals(codePointValue, CodePointRecordFields.generateCodePointRecordStream(codePointValue, codePointValue + 2, "").findFirst().orElseGet(TextRecords::empty).recordId());
            assertEquals(codePointValue, CodePointRecordFields.generateCodePointRecordStream(codePointValue, codePointValue + 1000, "").findFirst().orElseGet(TextRecords::empty).recordId());

            assertEquals(codePointValue + 2, CodePointRecordFields.generateCodePointRecordStream(codePointValue, codePointValue + 2, "").skip(2).findFirst().orElseGet(TextRecords::empty).recordId());
            assertEquals(codePointValue + 1000, CodePointRecordFields.generateCodePointRecordStream(codePointValue, codePointValue + 1000, "").skip(1000).findFirst().orElseGet(TextRecords::empty).recordId());
        }
    }

}