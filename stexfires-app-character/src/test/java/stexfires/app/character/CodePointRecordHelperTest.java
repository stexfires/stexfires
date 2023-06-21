package stexfires.app.character;

import org.junit.jupiter.api.Test;
import stexfires.record.TextRecords;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SuppressWarnings({"MagicNumber", "SpellCheckingInspection"})
class CodePointRecordHelperTest {

    @Test
    void generateCodePointRecord_negative() {
        int codePoint = -1;
        assertEquals("UNASSIGNED", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").category());
        assertEquals(codePoint, CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").recordId());
        assertEquals(18, CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").size());
        assertEquals("-1", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CODE_POINT));
        assertEquals("ffffffff", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_HEX_STRING));
        assertEquals("----", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_PRINTABLE_STRING));
        assertEquals("1", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CHARACTER_COUNT));
        assertEquals("", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CHARACTER_NAME));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_DEFINED));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_VALID_CODE_POINT));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_MIRRORED));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_ISO_CONTROL));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_ALPHABETIC));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_LETTER));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_SPACE_CHAR));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_DIGIT));
        assertEquals("-1", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_DIGIT_VALUE));
        assertEquals("-1", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_NUMERIC_VALUE));
        assertEquals("UNASSIGNED", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_TYPE));
        assertEquals("", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_BLOCK));
        assertEquals("DIRECTIONALITY_UNDEFINED", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_DIRECTIONALITY));

        assertNull(CodePointRecordHelper.generateCodePointRecord(codePoint, "----", null).textAt(CodePointRecordHelper.INDEX_CHARACTER_NAME));
        assertNull(CodePointRecordHelper.generateCodePointRecord(codePoint, "----", null).textAt(CodePointRecordHelper.INDEX_BLOCK));
    }

    @Test
    void generateCodePointRecord_0() {
        int codePoint = 0;
        assertEquals("CONTROL", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").category());
        assertEquals(codePoint, CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").recordId());
        assertEquals(18, CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").size());
        assertEquals("0", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CODE_POINT));
        assertEquals("0", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_HEX_STRING));
        assertEquals("----", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_PRINTABLE_STRING));
        assertEquals("1", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CHARACTER_COUNT));
        assertEquals("NULL", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CHARACTER_NAME));
        assertEquals("true", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_DEFINED));
        assertEquals("true", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_VALID_CODE_POINT));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_MIRRORED));
        assertEquals("true", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_ISO_CONTROL));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_ALPHABETIC));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_LETTER));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_SPACE_CHAR));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_DIGIT));
        assertEquals("-1", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_DIGIT_VALUE));
        assertEquals("-1", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_NUMERIC_VALUE));
        assertEquals("CONTROL", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_TYPE));
        assertEquals("BASIC_LATIN", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_BLOCK));
        assertEquals("DIRECTIONALITY_BOUNDARY_NEUTRAL", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_DIRECTIONALITY));

        assertNull(CodePointRecordHelper.generateCodePointRecord(codePoint, null, "").textAt(CodePointRecordHelper.INDEX_PRINTABLE_STRING));
    }

    @Test
    void generateCodePointRecord_32() {
        int codePoint = 32;
        assertEquals("SPACE_SEPARATOR", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").category());
        assertEquals(codePoint, CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").recordId());
        assertEquals(18, CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").size());
        assertEquals("32", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CODE_POINT));
        assertEquals("20", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_HEX_STRING));
        assertEquals(" ", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_PRINTABLE_STRING));
        assertEquals("1", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CHARACTER_COUNT));
        assertEquals("SPACE", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CHARACTER_NAME));
        assertEquals("true", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_DEFINED));
        assertEquals("true", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_VALID_CODE_POINT));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_MIRRORED));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_ISO_CONTROL));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_ALPHABETIC));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_LETTER));
        assertEquals("true", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_SPACE_CHAR));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_DIGIT));
        assertEquals("-1", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_DIGIT_VALUE));
        assertEquals("-1", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_NUMERIC_VALUE));
        assertEquals("SPACE_SEPARATOR", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_TYPE));
        assertEquals("BASIC_LATIN", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_BLOCK));
        assertEquals("DIRECTIONALITY_WHITESPACE", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_DIRECTIONALITY));
    }

    @Test
    void generateCodePointRecord_55() {
        int codePoint = 55;
        assertEquals("DECIMAL_DIGIT_NUMBER", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").category());
        assertEquals(codePoint, CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").recordId());
        assertEquals(18, CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").size());
        assertEquals("55", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CODE_POINT));
        assertEquals("37", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_HEX_STRING));
        assertEquals("7", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_PRINTABLE_STRING));
        assertEquals("1", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CHARACTER_COUNT));
        assertEquals("DIGIT SEVEN", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CHARACTER_NAME));
        assertEquals("true", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_DEFINED));
        assertEquals("true", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_VALID_CODE_POINT));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_MIRRORED));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_ISO_CONTROL));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_ALPHABETIC));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_LETTER));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_SPACE_CHAR));
        assertEquals("true", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_DIGIT));
        assertEquals("7", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_DIGIT_VALUE));
        assertEquals("7", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_NUMERIC_VALUE));
        assertEquals("DECIMAL_DIGIT_NUMBER", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_TYPE));
        assertEquals("BASIC_LATIN", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_BLOCK));
        assertEquals("DIRECTIONALITY_EUROPEAN_NUMBER", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_DIRECTIONALITY));
    }

    @Test
    void generateCodePointRecord_65() {
        int codePoint = 65;
        assertEquals("UPPERCASE_LETTER", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").category());
        assertEquals(codePoint, CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").recordId());
        assertEquals(18, CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").size());
        assertEquals("65", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CODE_POINT));
        assertEquals("41", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_HEX_STRING));
        assertEquals("A", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_PRINTABLE_STRING));
        assertEquals("1", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CHARACTER_COUNT));
        assertEquals("LATIN CAPITAL LETTER A", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_CHARACTER_NAME));
        assertEquals("true", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_DEFINED));
        assertEquals("true", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_VALID_CODE_POINT));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_MIRRORED));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_ISO_CONTROL));
        assertEquals("true", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_ALPHABETIC));
        assertEquals("true", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_LETTER));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_SPACE_CHAR));
        assertEquals("false", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_IS_DIGIT));
        assertEquals("-1", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_DIGIT_VALUE));
        assertEquals("10", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_NUMERIC_VALUE));
        assertEquals("UPPERCASE_LETTER", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_TYPE));
        assertEquals("BASIC_LATIN", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_BLOCK));
        assertEquals("DIRECTIONALITY_LEFT_TO_RIGHT", CodePointRecordHelper.generateCodePointRecord(codePoint, "----", "").textAt(CodePointRecordHelper.INDEX_DIRECTIONALITY));
    }

    @Test
    void generateCodePointRecordStream_count() {
        assertEquals(1, CodePointRecordHelper.generateCodePointRecordStream(0, 0, "", "").count());
        assertEquals(2, CodePointRecordHelper.generateCodePointRecordStream(0, 1, "", "").count());
        assertEquals(3, CodePointRecordHelper.generateCodePointRecordStream(0, 2, "", "").count());
        assertEquals(0, CodePointRecordHelper.generateCodePointRecordStream(1, 0, "", "").count());
        assertEquals(1, CodePointRecordHelper.generateCodePointRecordStream(2, 2, "", "").count());
        assertEquals(128, CodePointRecordHelper.generateCodePointRecordStream(0, 127, "", "").count());
    }

    @Test
    void generateCodePointRecordStream_findFirst() {
        for (int codePoint = 0; codePoint < 256; codePoint++) {
            assertEquals(codePoint, CodePointRecordHelper.generateCodePointRecordStream(codePoint, codePoint, "", "").findFirst().orElseGet(TextRecords::empty).recordId());
            assertEquals(codePoint, CodePointRecordHelper.generateCodePointRecordStream(codePoint, codePoint + 2, "", "").findFirst().orElseGet(TextRecords::empty).recordId());
            assertEquals(codePoint, CodePointRecordHelper.generateCodePointRecordStream(codePoint, codePoint + 1000, "", "").findFirst().orElseGet(TextRecords::empty).recordId());

            assertEquals(codePoint + 2, CodePointRecordHelper.generateCodePointRecordStream(codePoint, codePoint + 2, "", "").skip(2).findFirst().orElseGet(TextRecords::empty).recordId());
            assertEquals(codePoint + 1000, CodePointRecordHelper.generateCodePointRecordStream(codePoint, codePoint + 1000, "", "").skip(1000).findFirst().orElseGet(TextRecords::empty).recordId());
        }
    }

}